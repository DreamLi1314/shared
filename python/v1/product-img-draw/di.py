from injector import Binder, singleton
from service.draw_service import DrawService
from service.impl.product_img_draw_service import ProductImgDrawService
from service.draw_service_factory import DrawServiceFactory
from service.impl.line_chart_draw_service import LineChartDrawService

# 配置函数，绑定多实现
def configure(binder: Binder):
    # 使用 multibind 来绑定多个实现类
    binder.bind(DrawService, to=ProductImgDrawService, scope=singleton)
    binder.bind(DrawService, to=LineChartDrawService, scope=singleton)
    # 也可以使用 multibind 来注册一个集合
    services = [ProductImgDrawService, LineChartDrawService]
    binder.bind(DrawService, to=services, scope=singleton)

    binder.bind(DrawServiceFactory, to=DrawServiceFactory(services), scope=singleton)
