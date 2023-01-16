from flask import Flask
import config
from exts import db

app = Flask(__name__)

# 绑定配置文件
app.config.from_object(config)

db.init_app()

@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(port=80, debug=True)
