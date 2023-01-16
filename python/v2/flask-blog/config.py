# 配置文件
# 1. 数据库配置

host = '106.75.101.61'
port = 3306
user = 'root'
pwd = 'Yiji0713!'
db = 'db_test'
SQLALCHEMY_DATABASE_URI = f"mysql+pymysql://{user}:{pwd}@{host}:{port}/{db}?charset=utf8mb4"


# 2. 邮箱配置
MAIL_SERVER = "smtp.qq.com"
MAIL_USE_SSL = True
MAIL_PORT = 465
MAIL_USERNAME = "243853974@qq.com"
MAIL_PASSWORD = "svefkkqyfxmtbhba"
MAIL_DEFAULT_SENDER = "243853974@qq.com"