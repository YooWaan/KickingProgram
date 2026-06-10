use async_trait::async_trait;
use dotenvy::dotenv;
use graph_flow::{
    Context, FlowRunner, GraphBuilder, InMemorySessionStorage,
    NextAction, Session, Task, TaskResult, SessionStorage,
};
use graph_flow::context::MessageRole;
use rig_core::{
    client::{CompletionClient, ProviderClient},
    completion::{Chat, Message},
    providers::gemini,
};
use std::io::{self, Write};
use std::sync::Arc;

// ============================================================
// Task 1: システムプロンプトを設定する初期化タスク
// ============================================================
struct InitTask;

#[async_trait]
impl Task for InitTask {
    async fn run(&self, ctx: Context) -> graph_flow::Result<TaskResult> {
        // システムプロンプトをコンテキストに保存
        ctx.set(
            "system_prompt",
            "あなたは親切で知識豊富なアシスタントです。日本語で丁寧に答えてください。".to_string(),
        )
        .await;

        println!("=== Gemini チャットへようこそ ===");
        println!("'exit' または 'quit' と入力すると終了します。\n");

        // WaitForInput: ユーザー入力待ちで一時停止
        Ok(TaskResult::new(
            Some("チャットを開始します。何でも聞いてください！".to_string()),
            NextAction::WaitForInput,
        ))
    }
}

// ============================================================
// Task 2: Gemini API を呼び出してチャット応答を生成するタスク
// ============================================================
struct GeminiChatTask;

#[async_trait]
impl Task for GeminiChatTask {
    async fn run(&self, ctx: Context) -> graph_flow::Result<TaskResult> {
        // ユーザー入力を取得
        let user_input: String = ctx
            .get_sync("user_input")
            .unwrap_or_default();

        if user_input.trim().is_empty() {
            return Ok(TaskResult::new(
                Some("入力が空です。もう一度お試しください。".to_string()),
                NextAction::WaitForInput,
            ));
        }

        // 終了コマンドの確認
        if matches!(user_input.trim(), "exit" | "quit" | "終了") {
            ctx.set("should_exit", true).await;
            return Ok(TaskResult::new(
                Some("チャットを終了します。またね！".to_string()),
                NextAction::End,
            ));
        }

        // Gemini クライアントを初期化
        //let api_key = std::env::var("GEMINI_API_KEY")
        //    .map_err(|_| GraphError::Other(anyhow::anyhow!("GEMINI_API_KEY が設定されていません")))?;

        let client = gemini::Client::from_env().map_err(|e| anyhow::anyhow!("client build error: {}", e))?;
        //let client = gemini::Client::new(&api_key).map_err(|e| anyhow::anyhow!("client build error: {}", e))?;

        // システムプロンプトを取得
        let system_prompt: String = ctx
            .get_sync("system_prompt")
            .unwrap_or_else(|| "You are a helpful assistant.".to_string());

        // Rig で Gemini エージェントを構築
        let agent = client
        //.get("gemini-2.0-flash")   // モデル名はここで変更可
            .agent(gemini::completion::GEMINI_2_0_FLASH)
            .preamble(&system_prompt)//.map_err(|e| anyhow::anyhow!("can't get gemini model: {}", e))?
            .build();

        // チャット履歴を取得（Rig の Message 形式）
        let mut chat_history = ctx.get_all_messages().await.iter().map(|msg| match msg.role {
            MessageRole::User => Message::user(msg.content.clone()),
            MessageRole::Assistant => Message::assistant(msg.content.clone()),
            _ => Message::user(msg.content.clone()),
        }).collect();

        // Gemini API を呼び出す
        let response = agent
            .chat(&user_input, &mut chat_history)
            .await
            .map_err(|e| anyhow::anyhow!(e.to_string()))?;

        // 会話履歴をコンテキストに保存
        ctx.add_user_message(user_input.clone()).await;
        ctx.add_assistant_message(response.clone()).await;

        // 次もユーザー入力を待つ（会話継続）
        Ok(TaskResult::new(
            Some(response),
            NextAction::WaitForInput,
        ))
    }
}

// ============================================================
// メイン関数
// ============================================================
#[tokio::main]
async fn main() -> anyhow::Result<()> {
    // 環境変数を読み込む
    dotenv().ok();

    // タスクインスタンスを作成
    let init_task = Arc::new(InitTask);
    let chat_task = Arc::new(GeminiChatTask);

    // グラフを構築
    // InitTask → GeminiChatTask (ループ: WaitForInput で繰り返す)
    let graph = Arc::new(
        GraphBuilder::new("gemini_chat")
            .add_task(init_task.clone())
            .add_task(chat_task.clone())
            .add_edge(init_task.id(), chat_task.id())
            // チャットタスクは自分自身にループ（WaitForInput のたびに再開）
            .add_edge(chat_task.id(), chat_task.id())
            .build(),
    );

    // セッションストレージとフローランナーを作成
    let session_storage = Arc::new(InMemorySessionStorage::new());
    let flow_runner = FlowRunner::new(graph.clone(), session_storage.clone());

    // セッションを作成（InitTask から開始）
    let session_id = "chat_session_001".to_string();
    let session = Session::new_from_task(session_id.clone(), init_task.id());
    session_storage.save(session).await?;

    // ============================================================
    // メインループ: フローを実行しながらユーザーと対話
    // ============================================================
    loop {
        // ワークフローを1ステップ実行
        let result = flow_runner.run(&session_id).await?;

        // AI の応答を表示
        if let Some(response) = &result.response {
            println!("\nGemini: {}\n", response);
        }

        // 実行ステータスを確認
        match result.status {
            graph_flow::ExecutionStatus::Completed => {
                println!("セッション終了。");
                break;
            }
            graph_flow::ExecutionStatus::Error(e) => {
                eprintln!("エラーが発生しました: {}", e);
                break;
            }
            graph_flow::ExecutionStatus::WaitingForInput => {
                // ユーザー入力を受け取る
                print!("あなた: ");
                io::stdout().flush()?;

                let mut input = String::new();
                io::stdin().read_line(&mut input)?;
                let input = input.trim().to_string();

                // 入力をセッションコンテキストに保存
                let session = session_storage.get(&session_id).await?.unwrap();
                session.context.set("user_input", input).await;
                session_storage.save(session).await?;
            }
            graph_flow::ExecutionStatus::Paused { .. } => {
                // 次のタスクへ自動継続
                continue;
            }
        }
    }

    Ok(())
}
