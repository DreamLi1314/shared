# from exts import db
# from datetime import datetime
#
# class User(db.Model):
#     __tablename__ = "t_blog_user"
#     id = db.Column(db.Integer, primary_key=True, autoincrement=True)
#     username = db.Column(db.String(100), nullable=False)
#     password = db.Column(db.String(200), nullable=False)
#     email = db.Column(db.String(100), nullable=False, unique= True)
#     join_time = db.Column(db.DateTime, default=datetime.now)
