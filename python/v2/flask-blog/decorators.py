from functools import wraps

from flask import g, redirect, url_for


def login_required(func):
    # 保留 func 信息
    @wraps(func)
    # func(a,b,c)
    # func(1, 2, c = 3):
    #   args: 1,2; kwargs: c=3
    def inner(*args, **kwargs):
        if g.user:
            return func(*args, **kwargs)
        else:
            return redirect(url_for("auth.login"))

    return inner