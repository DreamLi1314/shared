from io import BytesIO

from service.draw_service import DrawService
from vo.draw_img_request import DrawImgRequest


class LineChartDrawService(DrawService):

    def is_accept(draw_type: str):
        return "LineChart".__eq__(draw_type)

    def draw(requestVo: DrawImgRequest) -> BytesIO:
        print("draw LineChart")

        return None