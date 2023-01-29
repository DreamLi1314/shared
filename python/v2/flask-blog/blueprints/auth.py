from flask import Blueprint, render_template, jsonify, redirect, url_for
from exts import mail, db
from flask_mail import Message
from flask import request, session
import string
import json
import random
from models import EmailCaptchaModel, User
from .forms import RegisterForm, LoginForm
from werkzeug.security import generate_password_hash, check_password_hash

bp = Blueprint("auth", __name__, url_prefix='/auth')


# http://127.0.0.1:5000/auth/login
@bp.route("/login", methods=['GET', 'POST'])
def login():
    if request.method == "GET":
        return render_template("login.html")
    else:
        form = LoginForm(request.form)

        if form.validate():
            email = form.email.data
            password = form.password.data
            user = User.query.filter_by(email=email).first()

            if not user:
                print("邮箱不存在")
                return redirect(url_for("auth.login"))

            if check_password_hash(user.password, password):
                # cookie, session
                # flask 中的 session, 是经过加密后存储在 cookie 中的
                session['user_id'] = user.id
                print("登录成功")
                return redirect("/")
            else:
                print("密码错误!")
                return redirect(url_for("auth.login"))

        else:
            print(form.errors)
            return redirect(url_for("auth.login"))

@bp.route("/logout")
def logout():
    session.clear()

    return redirect("/")

@bp.route("/captcha/email", methods=['GET'])
def get_email_captcha():
    email = request.args.get("email")

    print(email)

    # 生成验证码
    source = string.digits * 4
    captcha = random.sample(source, 4)
    captcha = "".join(captcha)

    print(captcha)

    msg = Message(subject="JavaFamily Blog 注册验证码",
                  recipients=[email],
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


@bp.route("/register", methods=["GET", "POST"])
def register():
    # 渲染页面
    if request.method == "GET":
        return render_template("register.html")
    else:
        # 验证用户提交的邮箱和验证码是对应且正确的
        # 表单验证: flask-wtf: wtforms
        form = RegisterForm(request.form)

        if form.validate():
            email = form.email.data
            username = form.username.data
            password = form.password.data

            user = User(email=email,
                        username=username,
                        password=generate_password_hash(password))
            db.session.add(user)
            db.session.commit()

            # return redirect("/auth/login")
            return redirect(url_for("auth.login"))
        else:
            print(form.errors)
            return redirect(url_for("auth.register"))


@bp.route("/mail/test")
def mail_test():
    msg = Message(subject="测试", recipients=["18829346477@163.com"], body="这是一个测试数据")
    mail.send(msg)

    return "Send Success!"

@bp.route("/user-list")
def user_list():
    user = User.query.get(1)

    return json.dumps(user.__dict__)
