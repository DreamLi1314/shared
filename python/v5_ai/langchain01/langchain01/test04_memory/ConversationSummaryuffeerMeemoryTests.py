from langchain.memory import ConversationSummaryBufferMemory
from langchain_openai import ChatOpenAI
import dotenv
import os

from test04_memory.child_chat_openAI import ChildChatOpenAI

dotenv.load_dotenv()

'''
大模型本身没有记忆能力
'''

# llm = ChatOpenAI(model=os.getenv("OPENAI_MODEL"))
# 使用自定义的子类来初始化 LLM,
# 解决  raise NotImplementedError(NotImplementedError: get_num_tokens_from_messages() is not presently implemented for model cl100k_base
llm = ChildChatOpenAI(
    model=os.getenv("OPENAI_MODEL")
)

memory = ConversationSummaryBufferMemory(
    llm = llm,
    max_token_limit = 60,
    return_messages = True
)

memory.save_context(inputs={"input": "您好,我的名字叫小明"}, outputs={"output": "很高兴见到你"})
memory.save_context(inputs={"input": "Python 是什么"}, outputs={"output": "Python 是变成语言"})
memory.save_context(inputs={"input": "Java 是什么"}, outputs={"output": "Java 是一个一次编写 ,到处运行的变成语言"})

print(memory.load_memory_variables({}))
print("=====")
print(memory.chat_memory.messages)
