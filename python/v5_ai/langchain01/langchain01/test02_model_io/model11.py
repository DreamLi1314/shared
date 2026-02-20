from langchain_community.vectorstores import FAISS, Chroma
from langchain_core.example_selectors import SemanticSimilarityExampleSelector
from langchain_core.prompts import PromptTemplate, FewShotPromptTemplate
from langchain_openai import OpenAIEmbeddings
import dotenv
import os

dotenv.load_dotenv()

'''
提示词模板
'''
os.environ['OPENAI_BASE_URL'] = os.getenv("OPENAI_EMBEDDING_BASE_URL")
embeddings_model = OpenAIEmbeddings(model=os.getenv("OPENAI_EMBEDDING_MODEL"))

# 2.定义示例提示词模版
example_prompt = PromptTemplate.from_template(
template="{input}的反义词是: {output}",
)

# 3.创建一个示例提示词模版
examples = [
    {"input": "高兴", "output": "悲伤"},
    {"input": "高", "output": "矮"},
    {"input": "长", "output": "短"},
    {"input": "精力充沛", "output": "无精打采"},
    {"input": "阳光", "output": "阴暗"},
    {"input": "粗糙", "output": "光滑"},
    {"input": "干燥", "output": "潮湿"},
    {"input": "富裕", "output": "贫穷"},
]

example_selector = SemanticSimilarityExampleSelector.from_examples(
examples,
embeddings_model,
FAISS,
k=2,
)

# 6.定义小样本提示词模版
similar_prompt = FewShotPromptTemplate(
example_selector=example_selector,
example_prompt=example_prompt,
prefix="给出{word}相关的词组:",
suffix="",
input_variables=["word"],
)
response = similar_prompt.invoke({"word":"忧郁"})
print(response.text)

print()