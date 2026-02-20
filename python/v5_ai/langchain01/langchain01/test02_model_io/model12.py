import dotenv
from langchain_core.prompts import load_prompt

dotenv.load_dotenv()

prompt = load_prompt("prompt.yaml", encoding="utf-8")
# print(prompt)
print(type(prompt))
print(prompt.format(name="年轻人", what="滑稽"))

prompt = load_prompt("prompt.json",encoding="utf-8")
# print(prompt)
print(type(prompt))
print(prompt.format(name="张三",what="搞笑的"))

print()

