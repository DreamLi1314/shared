from flask import Flask, session, g
import config
from exts import db, mail
from models import User
from blueprints.qa import bp as qa_bp
from blueprints.auth import bp as auth_bp
from flask_migrate import Migrate

app = Flask(__name__)

# 绑定配置文件
app.config.from_object(config)

# 初始化
db.init_app(app)
mail.init_app(app)

# 数据库版本管理
migrate = Migrate(app, db)

# 绑定蓝图
app.register_blueprint(qa_bp)
app.register_blueprint(auth_bp)

# hook
@app.before_request
def my_before_request():
    user_id = session.get('user_id')
    print(f"my_before_request: {user_id}")

    user = None

    if user_id:
        user = User.query.get(user_id)

    setattr(g, "user", user)

@app.context_processor
def my_context_processor():
    print(f"my_context_processor: {g.user}")
    return {'user': g.user}

if __name__ == '__main__':
    app.run(port=8080, debug=True)
