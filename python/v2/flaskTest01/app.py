from flask import Flask, request, render_template

# __name__ 代表当前 app.py 这个模块
# 1. 以后出现 bug, 可以帮助我们快速定位
# 2. 对于寻找模板文件, 有一个相对路径
app = Flask(__name__)

# 创建一个路由和视图函数的映射
@app.route("/")
def hello():
    return "Hello 中国!!!"

# path 参数
@app.route("/params/<int:id>")
def params(id):
    return "带 Path 参数的请求: %s" % id

# 请求参数
# /book/list 返回第一页数据
# /book/list?page=2 返回第二页的数据
@app.route("/book/list")
def book_list():
    page = request.args.get('page', default=1, type=int)
    return f"查询第 {page} 页数据"

@app.route("/index")
def index():
    return render_template("index.html")

if __name__ == '__main__':
    # 1. debug 模式
    # 1) 自带热部署
    # 2) 展示出错信息

    # 2. 修改 host
    # 让其他局域网可以访问到

    # 3. 修改端口号

    app.run(debug=True, host='0.0.0.0', port=80)