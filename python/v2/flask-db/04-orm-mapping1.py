from flask import Flask
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


with app.app_context():
    db.drop_all()
    db.create_all()
    print("重建表完成..")

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
