from flask import Flask, jsonify
from flask_sqlalchemy import SQLAlchemy
from geoalchemy2 import Geometry
from sqlalchemy import Column, Integer, String
from flask_migrate import Migrate

app = Flask(__name__)

host = '106.75.70.77'
port = 25432
user = 'postgres'
pwd = 'yiji_0716'
db = 'postgis-test'

app.config['SQLALCHEMY_DATABASE_URI'] = f"postgresql://{user}:{pwd}@{host}:{port}/{db}"

# SQLAlchemy 会自动读取 app.config 中设置好的连接数据库 url 信息
db = SQLAlchemy(app)

migrate = Migrate(app, db)

# Flask 映射三部曲
# 1. flask db init: 只需执行一次, 会创建 migrations 文件夹
# dreamli@JackLi flask-db % flask --app 07-orm-migrate  db init
# 2. flask db migrate # flask --app 07-orm-migrate db migrate
# 3. flask db upgrade # flask --app 07-orm-migrate db upgrade

class User(db.Model):
    __tablename__ = 't_user2'
    id = Column(Integer, primary_key=True)
    name = Column(String(100))
    geom = Column(Geometry('POLYGON'))
    pos = Column(Geometry(srid=4326, geometry_type="POINT"))

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
