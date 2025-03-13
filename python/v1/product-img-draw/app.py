from flask import Flask
import config
# from exts import db, mail
from di import configure
from blueprints.draw_img import bp as bp_draw_img
from flask_injector import FlaskInjector
import matplotlib.pyplot as plt
import matplotlib as mpl
from geoviswtx_utils.web_utils import WebUtils
from geoviswtx_utils.log_utils import LogUtils

# init
mpl.use('Agg')  # 使用非 GUI 后端
plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签
plt.rcParams['axes.unicode_minus'] = False  # 用来正常显示负号
mpl.rcParams['font.family'] = 'Heiti TC'  # 修改了全局变量
mpl.rcParams['font.size'] = 9  # 字体大小，可以按照喜好设置

app = Flask(__name__)

# 绑定配置文件
app.config.from_object(config)

# 初始化
# db.init_app(app)
# mail.init_app(app)

logger = LogUtils.build_logger()

# 绑定蓝图
app.register_blueprint(bp_draw_img)

# hook
@app.after_request
def controller_advice(response):
    # 如果响应内容是JSON，统一格式
    if response.is_json:
        # 获取响应的 JSON 数据
        response_data = response.get_json()
        # 封装为统一格式
        result = WebUtils.response_success(response_data)
        # 更新响应内容
        response.set_data(result.data)
    else:
        try:
            result = WebUtils.response_success(response.data.decode('utf-8'))
            # 更新响应内容
            response.set_data(result.data)
        except Exception as e:
            logger.exception(f"An unexpected error occurred: {e}")
            pass

    return response

FlaskInjector(app=app, modules=[configure])

if __name__ == '__main__':
    logger.info('系统启动......')
    app.run(port=8080, debug=True)
