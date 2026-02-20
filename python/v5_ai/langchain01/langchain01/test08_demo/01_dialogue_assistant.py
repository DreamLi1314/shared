"""
我们将构建一个可以与多种不同工具进行交互的Agent：一个是本地数据库，另一个是搜索引擎。你能
够向该Agent提问，观察它调用工具，并与它进行对话。
"""

import os

import dotenv
from langchain import hub
from langchain.agents import create_tool_calling_agent, AgentExecutor
from langchain_community.document_loaders import WebBaseLoader
from langchain_community.tools import TavilySearchResults
from langchain_community.vectorstores import FAISS
from langchain_core.chat_history import BaseChatMessageHistory, InMemoryChatMessageHistory
from langchain_core.runnables import RunnableWithMessageHistory
from langchain_core.tools import Tool, create_retriever_tool
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langgraph.prebuilt.chat_agent_executor import create_tool_calling_executor

dotenv.load_dotenv()

# 1 获取工具
# 1.1 获取搜索引擎
search = TavilySearchResults(
    max_results=5,
    include_answer=True,
    include_raw_content=True,
    include_images=True,
    # search_depth="advanced",
    # include_domains = []
    # exclude_domains = []
)

search_tool = Tool(
    name = "Search",
    description="一个搜索引擎优化全面，准确，可信的结果。当你需要回答有关当前事件的问题时非常有用。可以问天气、旅游、美食等",
    func=search.run
)

response = search_tool.invoke("北京市明天天气怎么样?")
# print(response)

# 1.2 构建 Retrieval tool
# 1.2.1 加载资源
loader = WebBaseLoader(web_path = "https://www.12371.cn/special/zggcdzc/zggcdzcqw/#disanzhang")
docs = loader.load()

# 1.2.2 切分资源
splitter = RecursiveCharacterTextSplitter(
    chunk_size= 1000,
    chunk_overlap=100,
    separators = ["\n\n", "\n", "。"]
)
split_docs = splitter.split_documents(docs)

# 1.2.3 向量化及存储
embeddings_model = OpenAIEmbeddings(
    model = os.getenv("OPENAI_EMBEDDING_MODEL"),
    openai_api_base = os.getenv("OPENAI_EMBEDDING_BASE_URL"),
)
vector = FAISS.from_documents(split_docs, embeddings_model)

# 1.2.4 转化为 Retrieval
retriever = vector.as_retriever()

# 1.2.5 工具自测
# print(retriever.invoke("中国共产党在社会主义初级阶段的基本路线是")[0])

# 1.2.6 工具封装
baidu_search_tool = create_retriever_tool(
    retriever=retriever,
    name="baidu_search",
    description="搜索百度"
)

# 2. 构建工具集
tools = [search_tool, baidu_search_tool]

# 3 构建大模型
# 3.1 获取大模型
llm = ChatOpenAI(model = os.getenv("OPENAI_MODEL"))

# 3.2 绑定工具
llm_with_tools =  llm.bind_tools(tools)

# 3.3 模型测试
response_llm = llm_with_tools.invoke("今天北京天气怎么样?")
# print(response_llm) # 没有真实调用工具,只识别要调用工具的意图

# 4 创建智能体,调动工具
# 4.1 构建提示词
prompt = hub.pull("hwchase17/openai-functions-agent")

# 4.2 创建智能体
agent = create_tool_calling_agent(llm_with_tools, tools, prompt)

# 4.3 创建智能体执行器
agent_executor = AgentExecutor(agent=agent, tools = tools, verbose = True)

# 4.4 智能体测试
# response_agent = agent_executor.invoke({"input": "西安明天天气怎么样?"})
# print(response_agent)

# 5 增加记忆: 区分用户
store = {}

def find_memory(user_id: str) -> BaseChatMessageHistory:
    if user_id not in store:
        store[user_id] = InMemoryChatMessageHistory()

    return store[user_id]

agent_with_memory = RunnableWithMessageHistory(
    runnable= agent_executor,
    get_session_history = find_memory,
    input_messages_key="input",
    history_messages_key="chat_history"
)

response_agent_memory = agent_with_memory.invoke(
    {"input": "明天西安的天气怎么样?"},
    config={
        "configurable": {
            "session_id": "1"
        }
    }
)

print(response_agent_memory)

response_agent_memory = agent_with_memory.invoke(
    {"input": "上海呢?"},
    config={
        "configurable": {
            "session_id": "1"
        }
    }
)

print(response_agent_memory)
