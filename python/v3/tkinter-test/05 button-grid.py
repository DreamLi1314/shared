# -*- coding:utf-8 -*-
import tkinter as tk
from tkinter import Message, LEFT, messagebox

# 调用Tk()创建主窗口
root_window = tk.Tk()
# 给主窗口起一个名字，也就是窗口的名字
root_window.title('JavaFamily：javafamily.club')

width = 450
height = 300
# 窗口居中，获取屏幕尺寸以计算布局参数，使窗口居屏幕中央
screenwidth = root_window.winfo_screenwidth()
screenheight = root_window.winfo_screenheight()
size_geo = '%dx%d+%d+%d' % (width, height, (screenwidth - width) / 2, (screenheight - height) / 2)
root_window.geometry(size_geo)
# 禁止 resize
root_window.resizable(False, False)

account = tk.StringVar()

# 将俩个标签分别布置在第一行、第二行
tk.Label(root_window, text="账号：").grid(row=0)
tk.Label(root_window, text="密码：").grid(row=1)
# 创建输入框控件

def valid_account():
    if e1.get() == "JackLi":
        messagebox.showinfo(title="Info", message="输入正确")
        return True
    else:
        messagebox.showwarning(title="Warning", message="输入不正确")
        e1.delete(0, tk.END)
        return False

e1 = tk.Entry(root_window, textvariable=account, validate='focusout', validatecommand=valid_account)
# 以 * 的形式显示密码
e2 = tk.Entry(root_window, show='*')
e1.grid(row=0, column=1, padx=10, pady=5)
e2.grid(row=1, column=1, padx=10, pady=5)


# 编写一个简单的回调函数
def login():
    messagebox.showinfo(title='JavaFamily', message="欢迎 %s 到来 JavaFamily" % account.get())


# 使用 grid()的函数来布局，并控制按钮的显示位置
tk.Button(root_window, text="登录", width=10, command=login).grid(row=3, column=0, sticky="w", padx=10, pady=5)
tk.Button(root_window, text="退出", width=10, command=root_window.quit).grid(row=3, column=1, sticky="e", padx=10, pady=5)

# 开启主循环，让窗口处于显示状态
root_window.mainloop()
