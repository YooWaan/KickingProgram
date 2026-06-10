import asyncio
import numpy as np
from lightrag import LightRAG, QueryParam
from lightrag.llm import openai
from lightrag.utils import EmbeddingFunc


LLAMA_SERVER_URL = "http://172.16.80.111:8080/v1"
GEMMA_MODEL = "ggml-org/gemma-3-1b-it-GGUF:Q4_K_M"

async def llm_func(prompt, system_prompt=None, history_messages=[], **kwargs):
    return await openai_complete_if_cache(
        GEMMA_MODEL,
        prompt,
        system_prompt=system_prompt,
        history_messages=history_messages,
        api_key="not-needed",       # ローカルなので不要
        base_url=LLAMA_SERVER_URL,
        **kwargs,
    )


async def embed_func(texts: list[str]) -> np.ndarray:
    return await openai_embedding(
        texts,
        model=GEMMA_MODEL,
        api_key="not-needed",
        base_url=LLAMA_SERVER_URL,
    )


rag = LightRAG(
    working_dir="./graph_db",
    llm_model_func=llm_func,
    embedding_func=EmbeddingFunc(
        embedding_dim=1152,   # Gemma 4 の埋め込み次元
        max_token_size=8192,
        func=embed_func,
    ),
    llm_model_max_async=2,    # ローカルLLMは並列数を抑える
)

def main():
    print("Hello from graph-rag!")


if __name__ == "__main__":
    main()
