import dotenv
from langchain_openai import ChatOpenAI
import os

dotenv.load_dotenv() #加载当前目录下的 .env 文件
os.environ['OPENAI_API_KEY'] = os.getenv("OPENAI_API_KEY1")
os.environ['OPENAI_BASE_URL'] = os.getenv("OPENAI_BASE_URL")
base_url = os.getenv("OPENAI_BASE_URL")
model = os.getenv("OPENAI_MODEL")

# 创建大模型实例
llm = ChatOpenAI(model=model, base_url=base_url,  temperature=0.7)

# 直接提供问题，并调用llm
response = llm.invoke(input = "什么是大模型？")
print(response)