from flask import Flask, render_template
from datetime import datetime

app = Flask(__name__, static_folder='mystatic')


class User:
    def __init__(self, username, email):
        self.username = username
        self.email = email


@app.route('/')
def index():
    return render_template("index.html")


# 通过基本类型渲染模板
@app.route('/blog/<int:blog_id>')
def blog_detail(blog_id):
    return render_template("blog_detail.html", blog_id=blog_id, user_name='Jack Li')


# 通过对象渲染模板
@app.route('/profile')
def profile():
    user = User(username='Jack Li', email='123@qq.com')
    return render_template("profile.html", user=user)


# 通过字典渲染模板
@app.route('/user_info')
def user_info():
    person = {
        'username': "Jack Li",
        'email': '123@qq.com'
    }

    return render_template("user_info.html", person=person)


# 过滤器(管道操作)
@app.route('/filter')
def filter():
    user = User(username='Jack Li', email='123@qq.com')
    return render_template("filter.html", user=user)


# 自定义过滤器
def date_format(value, format='%Y年%d月%m日 %H:%M'):
    return value.strftime(format)


app.add_template_filter(date_format, 'dformat')


# 自定义过滤器(管道操作)
@app.route('/custom_filter')
def custom_filter():
    user = User(username='Jack Li', email='123@qq.com')
    df = datetime.now()

    return render_template("custom_filter.html", user=user, datetime=df)


# 控制语句
@app.route('/control')
def control():
    age = 19

    books = [{
        'name': '三国演义',
        'author': '罗贯中'
    },
        {
            'name': '水浒传',
            'author': '施耐庵'
        }
    ]

    return render_template("control.html", age=age, books=books)


# 模板继承
@app.route("/child1")
def child1():
    return render_template("child1.html")


@app.route("/child2")
def child2():
    return render_template("child2.html")

# 加载静态资源文件
@app.route('/static')
def load_static():
    return render_template('static.html')


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=80)
