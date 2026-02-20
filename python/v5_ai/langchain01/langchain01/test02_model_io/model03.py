from langchain_core.messages import SystemMessage, HumanMessage
from langchain_openai import ChatOpenAI
import dotenv
import os

dotenv.load_dotenv()

'''
流式响应
'''

llm = ChatOpenAI(model=os.getenv("OPENAI_MODEL"),
                 streaming=True)

sys_message = SystemMessage(
content="我是一个人工智能的助手，我的名字叫小智",
)
human_message = HumanMessage(content="中美 AI 发展现状？")
messages = [sys_message, human_message]

#调用大模型，传入messages
print("开始流式输出：")
for chunk in llm.stream(messages):
    print(chunk.content, end="", flush=True)
print("\n流式输出结束")

print()