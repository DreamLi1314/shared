import wtforms
from wtforms.validators import Email, Length, EqualTo
from models import User, EmailCaptchaModel
from exts import db


class RegisterForm(wtforms.Form):
    email = wtforms.StringField(validators=[Email(message='邮箱格式错误!')])
    captcha = wtforms.StringField(validators=[Length(min=4, max=4, message="验证码必须是 4 位")])
    username = wtforms.StringField(validators=[Length(min=3, max=20, message="用户名格式错误!")])
    password = wtforms.StringField(validators=[Length(min=6, max=20, message="密码最少 6 位!")])
    password2 = wtforms.StringField(validators=[EqualTo("password", message="两次密码不一致")])

    # 自定义验证:
    # 1. 邮箱是否被注册
    def validate_email(self, filed):
        user = User.query.filter_by(email=filed.data).first()

        if user:
            raise wtforms.ValidationError(message="该邮箱已经被注册!")

    # 2. 验证码是否正确
    def validate_captcha(self, field):
        captcha = field.data
        email = self.email.data

        captcha_model = EmailCaptchaModel.query.filter_by(
            email=email, captcha=captcha).first()

        if not captcha_model:
            raise wtforms.ValidationError(message="邮箱或者验证码错误!")

        else:
            # 验证码应该存 redis 自动移除
            db.session.delete(captcha_model)
            db.session.commit()

class LoginForm(wtforms.Form):
    email = wtforms.StringField(validators=[Email(message='邮箱格式错误!')])
    password = wtforms.StringField(validators=[Length(min=6, max=20, message="密码最少 6 位!")])
