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
    name = Column(String(100))
    geom = Column(Geometry('POLYGON'))
    pos = Column(Geometry(srid=4326, geometry_type="POINT"))

@app.route("/user/add")
def add():
    geom = 'POLYGON((1 0,3 0,3 2,1 2,1 0))'
    pos = 'POINT(111.6437389 21.02016667)'
    user = User(name='JackLi', pos=pos, geom=geom)
    db.session.add(user)
    db.session.commit()

    return "用户创建成功!"

@app.route("/user/find")
def find_by_id():
    user = User.query.get(1)

    return jsonify(user)

@app.route("/user/filter")
def find_by_filter():
    users = User.query.filter_by(name='JackLi')

    for user in users:
        print(user.name)

    return 'Success'

@app.route("/user/list")
def list():
    users = db.session.query(User)

    for user in users:
        print(user.name)

    return 'Success'

@app.route("/user/update")
def update():
    user = User.query.get(1)

    user.name = 'JackLi1314'

    db.session.commit()

    return 'Success'

@app.route("/user/del")
def delete():
    user = User.query.get(2)

    db.session.delete(user)

    db.session.commit()

    return 'Success'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
