package main

import (
    "context"
    "log"
    "os"

    "google.golang.org/genai"

    "google.golang.org/adk/agent"
    "google.golang.org/adk/agent/llmagent"
    "google.golang.org/adk/cmd/launcher"
    "google.golang.org/adk/cmd/launcher/full"
    "google.golang.org/adk/model/gemini"
    "google.golang.org/adk/tool"
    "google.golang.org/adk/tool/geminitool"
)

func main() {
    ctx := context.Background()

    // モデルの作成
    model, err := gemini.NewModel(ctx, "gemini-2.5-flash", &genai.ClientConfig{
        APIKey: os.Getenv("GOOGLE_API_KEY"),
    })
    if err != nil {
        log.Fatalf("Failed to create model: %v", err)
    }

    // エージェントの作成
    a, err := llmagent.New(llmagent.Config{
        Name:        "my_agent",
        Model:       model,
        Description: "シンプルな会話エージェント",
        Instruction: "あなたは親切なアシスタントです。日本語で答えてください。",
        Tools: []tool.Tool{
            geminitool.GoogleSearch{}, // ツールは不要なら省略可
        },
    })
    if err != nil {
        log.Fatalf("Failed to create agent: %v", err)
    }

    // ランチャーの設定と起動
    config := &launcher.Config{
        AgentLoader: agent.NewSingleLoader(a),
    }
    l := full.NewLauncher()
    if err = l.Execute(ctx, config, os.Args[1:]); err != nil {
        log.Fatalf("Run failed: %v\n\n%s", err, l.CommandLineSyntax())
    }
}
