
from flask import Blueprint, request
from geoviswtx_utils.img_utils import ImgUtils
from geoviswtx_utils.log_utils import LogUtils
from injector import inject

from service.draw_service_factory import DrawServiceFactory
from vo.draw_img_request import DrawImgRequest

bp = Blueprint("draw", __name__, url_prefix='/draw')

logger = LogUtils.build_logger()

@bp.route("/img", methods=['POST'])
@inject
def draw(drawServiceFactory: DrawServiceFactory):
    logger.info("开始处理绘图请求！")
    requestVoSchema = DrawImgRequest.Schema()
    requestVo = requestVoSchema.load(request.json)
    service = drawServiceFactory.find_service(requestVo.type)
    imgBuf = service.draw(requestVo)
    logger.info("绘图请求-绘图绘制成功，开始转 base64")
    # 转换为Base64格式
    img_base64 = ImgUtils.img2Base64(imgBuf)
    logger.info("绘图请求处理完成！")

    return img_base64
