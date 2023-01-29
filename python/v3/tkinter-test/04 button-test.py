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
root_window.geometry('400x200+100+100')

# 设置回调函数
def callback():
    print ("click me!")

# 使用按钮控件调用函数
b = tk.Button(root_window, text="点击执行回调函数", command=callback).pack()

# 当按钮被点击的时候执行click_button()函数
def click_button():
    # 使用消息对话框控件，showinfo()表示温馨提示
    messagebox.showinfo(title='温馨提示', message='欢迎使用 JavaFamily')

# 点击按钮时执行的函数
button = tk.Button(root_window,text='点击前往',bg='#7CCD7C',width=20, height=5,command=click_button).pack()

# 创建图片对象
im = tk.PhotoImage(file='./assets/gif.gif')
# 通过image参数传递图片对象
button = tk.Button(root_window,image=im,command=click_button).pack()

# 开启主循环，让窗口处于显示状态
root_window.mainloop()
