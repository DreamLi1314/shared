from langchain_core.messages import SystemMessage, HumanMessage
from langchain_core.prompts import PromptTemplate
from langchain_openai import ChatOpenAI
import dotenv
import os

dotenv.load_dotenv()

'''
提示词模板
'''

llm = ChatOpenAI(model=os.getenv("OPENAI_MODEL"),
                 streaming=True)

template = PromptTemplate.from_template(
    template = "请评价{product}的优缺点，包含{aspect1}和{aspect2}"
)

prompt = template.invoke({
    "product": "智能手机",
    "aspect1": "电池续航",
    "aspect2": "拍照质量"
})

print(type(prompt))

response = llm.invoke(prompt)
print(response)
print(type(response))

print()