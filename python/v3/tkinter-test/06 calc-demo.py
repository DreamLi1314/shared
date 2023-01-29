# -*- coding:utf-8 -*-
import tkinter as tk
from tkinter import Message, LEFT, messagebox, Frame

# 调用Tk()创建主窗口
root_window = tk.Tk()
# 给主窗口起一个名字，也就是窗口的名字
root_window.title('计算器：javafamily.club')

width = 450
height = 300
# 窗口居中，获取屏幕尺寸以计算布局参数，使窗口居屏幕中央
screenwidth = root_window.winfo_screenwidth()
screenheight = root_window.winfo_screenheight()
size_geo = '%dx%d+%d+%d' % (width, height, (screenwidth - width) / 2, (screenheight - height) / 2)
root_window.geometry(size_geo)
# 禁止 resize
root_window.resizable(False, False)


def calc():
    result_label = "=" + str(eval(exp.get()))
    result.config(text=result_label)


# 创建一个容器来包括其他控件
# 如果直接放置其他控件在 root window, 那么所有控件将比较松散, 占满整个 window
frame = Frame(root_window)
frame.pack()

exp = tk.StringVar()

exp_input = tk.Entry(frame, textvariable=exp)

exp_input.pack()

result = tk.Label(frame, text='=')
result.pack(side=tk.LEFT)

calc_btn = tk.Button(frame, text='等于', command=calc)
calc_btn.pack(side=tk.RIGHT)

# 开启主循环，让窗口处于显示状态
root_window.mainloop()
