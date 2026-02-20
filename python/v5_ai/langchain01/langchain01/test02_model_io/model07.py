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

prompt = template.invoke({
    "name": "小智",
    "history": [] if prompt is None else prompt.to_messages(),
    "que": "1+2*3=?",
})

response = llm.invoke(prompt)
print(response)

prompt = template.invoke({
    "name": "小智",
    "history": [] if prompt is None else prompt.to_messages().append(response),
    "que": "我刚才问的问题是什么?",
})

response = llm.invoke(prompt)
print(response)

print()