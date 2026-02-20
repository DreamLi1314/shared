from langchain_core.messages import SystemMessage, HumanMessage
from langchain_openai import ChatOpenAI
import dotenv
import os

dotenv.load_dotenv()

'''
大模型本身没有记忆能力
'''

llm = ChatOpenAI(model=os.getenv("OPENAI_MODEL"))

sys_message = SystemMessage(
content="我是一个人工智能的助手，我的名字叫小智",
)
human_message = HumanMessage(content="猫王是一只猫吗？")
messages = [sys_message, human_message]

#调用大模型，传入messages
response = llm.invoke(messages)
print(response)

human_message = HumanMessage(content="你叫什么名字？")
response = llm.invoke([human_message])
print(response)

human_message = HumanMessage(content="从现在开始，你叫小明")
response = llm.invoke([human_message])
print(response)

human_message = HumanMessage(content="你叫什么名字？")
response = llm.invoke([human_message])
print(response)

print()