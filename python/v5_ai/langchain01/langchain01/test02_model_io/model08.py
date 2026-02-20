from langchain_core.messages import SystemMessage, HumanMessage
from langchain_core.prompts import PromptTemplate, ChatPromptTemplate, MessagesPlaceholder
from langchain_openai import ChatOpenAI
import dotenv
import os

dotenv.load_dotenv()

'''
提示词模板
'''

llm = ChatOpenAI(model=os.getenv("OPENAI_MODEL"),
                 streaming=True)

template = ChatPromptTemplate.from_messages(
    [
        ("system", "你是一个数学老师，只能回答数学问题, 你的名字叫{name}"),
        MessagesPlaceholder("history"),
        ("human", "我的问题是{que}")
    ]
)

prompt = None

while True:
    que = input("请输入你的问题: ")

    if que.__eq__("exit"):
        break

    prompt = template.invoke({
        "name": "小智",
        "history": [] if prompt is None else prompt.to_messages(),
        "que": que
    })

    response = llm.invoke(prompt)
    print("AI 回复: " + response.content)

print()