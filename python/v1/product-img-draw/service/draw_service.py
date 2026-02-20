
from abc import ABC, abstractmethod
from io import BytesIO

from vo.draw_img_request import DrawImgRequest

'''
绘图接口
'''
class DrawService(ABC):

    def __init__(self):
        pass

    @abstractmethod
    def is_accept(draw_type: str) -> bool:
        pass

    @abstractmethod
    def draw(requestVo: DrawImgRequest) -> BytesIO:
        pass