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

spinbox1 = tk.Spinbox(root_window, bg='#9BCD9B', cnf={
    'from': 0,
    'to': 20,
    'increment': 2,
    'width': 15
})
spinbox1.pack()


spinbox2 = tk.Spinbox(root_window, values = ('Java', 'Python', 'C++'))
spinbox2.pack()

# 开启主循环，让窗口处于显示状态
root_window.mainloop()
