from geoviswtx_utils.log_utils import LogUtils
from geoviswtx_utils.tool import charset

from service.draw_service import DrawService
import geopandas as gpd
import pandas as pd
import matplotlib.pyplot as plt
import io

from vo.draw_img_request import DrawImgRequest

logger = LogUtils.build_logger()

class ProductImgDrawService(DrawService):

    def is_accept(draw_type: str) -> bool:
        return "ProductImg".__eq__(draw_type)

    def draw(requestVo: DrawImgRequest) -> io.BytesIO:
        logger.info("获取 shp 边界数据")
        pltArea = requestVo.plt_area
        # 加载数据-矢量数据的位置
        regions = gpd.GeoDataFrame.from_file(requestVo.shp_path, encoding=charset)

        # 加载数据
        logger.info("加载数据")
        data = pd.read_excel('/Users/dreamli/Workspace/geovis/projects/components/python/product-img-draw/data/henan_gdp.xlsx')

        # 处理数据格式（Excel中的name字段中有空格，消除空格）
        data['area_name'] = data['name'].str.replace(' ', '')

        # 连接矢量数据属性表与Excel表格
        reg = pd.merge(regions, data, left_on='name', right_on='area_name')
        # 复制整张表
        reg02 = reg.copy()
        # # 专题地图制图
        reg02['coords'] = reg02['geometry'].apply(lambda x: x.representative_point().coords[0])

        logger.info("开始绘图")
        reg02.plot(figsize=(8, 6),  # 图像大小
                   column='avg_gdp',  # 分级设色字段
                   scheme='quantiles',  # MapClassify-分级类型
                   legend=True,  # 图例
                   legend_kwds={"loc": "lower left"},
                   cmap='Reds',  # 渐变色带的名称
                   edgecolor='k')  # 边框颜色

        # 地图标注
        logger.info("添加地图标注")
        for n, i in enumerate(reg02['coords']):
            plt.text(i[0] - 0.13, i[1], reg02['area_name'][n])  # 标注位置X，Y，标注内容

        plt.title(pltArea.title)
        plt.grid(True, alpha=0.5)  # 显示网格，透明度为50%
        img_buffer = io.BytesIO()
        plt.savefig(img_buffer, format='png', bbox_inches='tight')
        # plt.show()
        plt.close()
        img_buffer.seek(0)

        return img_buffer