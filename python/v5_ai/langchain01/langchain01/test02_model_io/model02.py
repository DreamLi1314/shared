from langchain_core.messages import SystemMessage, HumanMessage
from langchain_openai import ChatOpenAI
import dotenv
import os

dotenv.load_dotenv()

'''
大模型实现类似记忆能力
'''

llm = ChatOpenAI(model=os.getenv("OPENAI_MODEL"))

sys_message = SystemMessage(
content="我是一个人工智能的助手，我的名字叫小智",
)
human_message = HumanMessage(content="你叫什么名字？")
messages = [sys_message, human_message]

#调用大模型，传入messages
response = llm.invoke(messages)
print(response)

response = llm.invoke([human_message])
print(response)

human_message2 = HumanMessage(content="从现在开始，你叫小明")
response = llm.invoke([human_message])
print(response)

human_message3 = HumanMessage(content="你叫什么名字？")
response = llm.invoke([sys_message, human_message, human_message2, human_message3])
print(response)

response = llm.invoke([human_message])
print(response)

#
# human_message = HumanMessage(content="你叫什么名字？")
# response = llm.invoke([human_message])
# print(response)

print()