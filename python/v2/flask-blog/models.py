from exts import db
from datetime import datetime

class User(db.Model):
    __tablename__ = "t_blog_user"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    username = db.Column(db.String(100), nullable=False)
    password = db.Column(db.String(200), nullable=False)
    email = db.Column(db.String(100), nullable=False, unique= True)
    join_time = db.Column(db.DateTime, default=datetime.now)

class EmailCaptchaModel(db.Model):
    __tablename__ = "t_email_captcha"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    email = db.Column(db.String(100), nullable=False)
    captcha = db.Column(db.String(100), nullable=False)

class QuestionModel(db.Model):
    __tablename__ = "t_question"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    title = db.Column(db.String(100), nullable=False)
    content = db.Column(db.String(100), nullable=False)
    create_time = db.Column(db.DateTime, default=datetime.now)

    author_id = db.Column(db.Integer, db.ForeignKey("t_blog_user.id"))
    author = db.relationship(User, backref="questions")

class AnswerModel(db.Model):
    __tablename__ = "t_answer"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    content = db.Column(db.String(100), nullable=False)
    create_time = db.Column(db.DateTime, default=datetime.now)

    author_id = db.Column(db.Integer, db.ForeignKey("t_blog_user.id"))
    author = db.relationship(User, backref="answers")

    question_id = db.Column(db.Integer, db.ForeignKey("t_question.id"))
    question = db.relationship(QuestionModel, backref=db.backref("answers", order_by=create_time.desc))