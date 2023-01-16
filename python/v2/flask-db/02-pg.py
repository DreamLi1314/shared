from flask import Flask
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)

host = '106.75.70.77'
port = 25432
user = 'postgres'
pwd = 'yiji_0716'
db = 'postgis-test'

app.config['SQLALCHEMY_DATABASE_URI'] = f"postgresql://{user}:{pwd}@{host}:{port}/{db}"

# SQLAlchemy 会自动读取 app.config 中设置好的连接数据库 url 信息
db = SQLAlchemy(app)

with app.app_context():
    with db.engine.connect() as conn:
        rs = conn.execute('select 1')
        print(rs.fetchone())  # (1,)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
