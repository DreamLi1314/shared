from typing import List
from langchain_core.messages import BaseMessage
from langchain_openai import ChatOpenAI
from langchain_openai.chat_models.base import BaseChatOpenAI

# 定义子类
class ChildChatOpenAI(ChatOpenAI):
    def get_num_tokens_from_messages(self, messages: List[BaseMessage]) -> int:
        model, encoding = self._get_encoding_model()
        # 针对 cl100k_base 模型进行处理
        if model.startswith("cl100k_base"):
            # 调用祖父类（BaseChatOpenAI）的方法
            return super(BaseChatOpenAI, self).get_num_tokens_from_messages(messages)
        else:
            return super().get_num_tokens_from_messages(messages)
