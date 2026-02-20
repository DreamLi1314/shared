from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import create_engine

app = Flask(__name__)

host = '106.75.70.77'
port = 25432
user = 'postgres'
pwd = 'yiji_0716'
db = 'postgis-test'

url = f"postgresql://{user}:{pwd}@{host}:{port}/{db}"

# app.config['SQLALCHEMY_DATABASE_URI'] = url
# db = SQLAlchemy(app)

engine = create_engine(url, echo=True, pool_size = 6)

with app.app_context():
    rs = engine.execute('select 1')
    print(rs.fetchone())  # (1,)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
