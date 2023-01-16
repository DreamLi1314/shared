from flask import Flask, jsonify
from flask_sqlalchemy import SQLAlchemy
from geoalchemy2 import Geometry
from sqlalchemy import Column, Integer, String

app = Flask(__name__)

host = '106.75.70.77'
port = 25432
user = 'postgres'
pwd = 'yiji_0716'
db = 'postgis-test'

app.config['SQLALCHEMY_DATABASE_URI'] = f"postgresql://{user}:{pwd}@{host}:{port}/{db}"

# SQLAlchemy 会自动读取 app.config 中设置好的连接数据库 url 信息
db = SQLAlchemy(app)

class User(db.Model):
    __tablename__ = 't_user'
    id = Column(Integer, primary_key=True)
    name = Column(String(100), nullable=False)
    password = Column(String(100), nullable=False)

    # 双向 1
    # articles = db.relationship("Article", back_populates="author")

class Article(db.Model):
    __tablename__ = 't_article'
    id = Column(Integer, primary_key=True)
    title = Column(String(100), nullable=False)
    content = Column(String(100), nullable=False)

    # 添加作者的外键
    author_id = Column(Integer, db.ForeignKey('t_user.id'))
    # 添加关联模型
    # author = db.relationship("User") # 单向
    # 双向 1:需要再双方模型都声明 n 边的属性
    # author = db.relationship("User", back_populates = "articles")
    # 双向 2: backref 会自动给 User 模型添加一个 articles 的属性用于获取文章列表
    author = db.relationship("User", backref="articles")

# with app.app_context():
#     db.drop_all()
#     db.create_all()
#     user1 = User(name='Jack Li', password = 'pwd123')
#     db.session.add(user1)
#
#     user2 = User(name='Wang wu', password='1pwd123444')
#     db.session.add(user2)
#
#     db.session.commit()
#
#     print("重建表完成..")

@app.route("/article/add/<user_id>")
def add(user_id):
    article = Article(title='Python 教程', content="Python 是一个 xxxx",
                      author=User.query.get(user_id))
    db.session.add(article)

    db.session.commit()

    return "添加成功"

@app.route("/article/query/<user_id>")
def query(user_id):
    user = User.query.get(user_id)

    for article in user.articles:
        print(article.title)

    return '查询成功'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
