from langchain_core.messages import SystemMessage, HumanMessage
from langchain_core.prompts import PromptTemplate, ChatPromptTemplate
from langchain_openai import ChatOpenAI
import dotenv
import os

dotenv.load_dotenv()

'''
提示词模板
'''

llm = ChatOpenAI(model=os.getenv("OPENAI_MODEL"),
                 streaming=True)

# template = ChatPromptTemplate(
#     [
#         ("system", "你是一个数学老师，只能回答数学问题, 你的名字叫{name}"),
#         ("human", "我的问题是{que}")
#     ]
# )

template = ChatPromptTemplate.from_messages(
    [
        ("system", "你是一个数学老师，只能回答数学问题, 你的名字叫{name}"),
        ("human", "我的问题是{que}")
    ]
)

prompt = template.invoke({
    "name": "小智",
    "que": "1+2*3=?",
})

print(type(prompt))

response = llm.invoke(prompt)
print(response)
print(type(response))

prompt = template.invoke({
    "name": "小智",
    "que": "讲一下王者荣耀的操作技巧",
})

response = llm.invoke(prompt)
print(response)

print()