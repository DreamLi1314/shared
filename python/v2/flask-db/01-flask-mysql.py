from flask import Flask
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)

host = '106.75.101.61'
port = 3306
user = 'root'
pwd = 'Yiji0713!'
db = 'db_test'

app.config['SQLALCHEMY_DATABASE_URI'] = f"mysql+pymysql://{user}:{pwd}@{host}:{port}/{db}?charset=utf8mb4"

# SQLAlchemy 会自动读取 app.config 中设置好的连接数据库 url 信息
db = SQLAlchemy(app)

with app.app_context():
    with db.engine.connect() as conn:
        rs = conn.execute('select 1')
        print(rs.fetchone())  # (1,)

@app.route('/')
def hello_world():
    return 'Hello World!'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
