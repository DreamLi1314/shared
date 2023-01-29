# -*- coding:utf-8 -*-
import tkinter as tk
from tkinter import Message, LEFT

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

# 更改左上角窗口的的icon图标,加载 logo
root_window.iconbitmap('./assets/jfoa.ico')

msg = Message(root_window, text="JavaFamily 欢迎您", width=60, font=('微软雅黑', 10, 'bold'))
msg.pack(side=LEFT)

# 开启主循环，让窗口处于显示状态
root_window.mainloop()
