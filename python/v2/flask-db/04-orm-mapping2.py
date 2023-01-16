from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import create_engine, Column, Integer, String
from geoalchemy2 import Geometry

app = Flask(__name__)

host = '106.75.70.77'
port = 25432
user = 'postgres'
pwd = 'yiji_0716'
db = 'postgis-test'

url = f"postgresql://{user}:{pwd}@{host}:{port}/{db}"

engine = create_engine(url, echo=True, pool_size = 6)

Base = declarative_base()

class User(Base):
    __tablename__ = 't_user'
    id = Column(Integer, primary_key=True)
    name = Column(String(100))
    pos = Column(Geometry('POLYGON'))

with  app.app_context():
    # 删除表
    User.__table__.drop(engine)

    # 自动创建表
    User.__table__.create(engine)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
