from flask import Blueprint, render_template, request

bp = Blueprint("qa", __name__, url_prefix='/')

# http://127.0.0.1:5000
@bp.route("/")
def index():
    return render_template("index.html")

@bp.route("/qa/publish", methods = ["GET", "POST"])
def publish():
    if request.method == "GET":
        return render_template("publish_question.html")

