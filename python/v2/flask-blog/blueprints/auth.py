from flask import Blueprint, render_template, jsonify
from exts import mail, db
from flask_mail import Message
from flask import request
import string
import random
from models import EmailCaptchaModel

bp = Blueprint("auth", __name__, url_prefix='/auth')


# http://127.0.0.1:5000/auth/login
@bp.route("/login")
def login():
    pass


@bp.route("/captcha/email")
def get_email_captcha():
    email = request.args.get("email")

    print(email)

    # 生成验证码
    source = string.digits * 4
    captcha = random.sample(source, 4)
    captcha = "".join(captcha)

    print(captcha)

    msg = Message(subject="JavaFamily Blog 注册验证码",
                  recipients=["18829346477@163.com"],
                  body=f"欢迎注册 JavaFamily Blog, 您的验证码是: {captcha}")
    mail.send(msg)

    # 保存验证码及邮箱
    model = EmailCaptchaModel(email=email, captcha=captcha)
    db.session.add(model)
    db.session.commit()

    return jsonify({
        'code': 200,
        'msg': None,
        'data': email
    })

@bp.route("/register")
def register():
    # 验证用户提交的邮箱和验证码是对应且正确的

    return render_template("register.html")


@bp.route("/mail/test")
def mail_test():
    msg = Message(subject="测试", recipients=["18829346477@163.com"], body="这是一个测试数据")
    mail.send(msg)

    return "Send Success!"
