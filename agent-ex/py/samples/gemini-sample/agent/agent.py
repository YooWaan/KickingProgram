from google.adk.agents import Agent

root_agent = Agent(
    model="gemini-2.5-flash",
    name="my_agent",
    description="シンプルな会話エージェント",
    instruction="あなたは親切なアシスタントです。日本語で答えてください。",
)
