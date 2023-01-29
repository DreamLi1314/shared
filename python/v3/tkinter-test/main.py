# -*- coding:utf-8 -*-
import tkinter as tk
from tkinter import messagebox

# 调用Tk()创建主窗口
root_window = tk.Tk()
# 给主窗口起一个名字，也就是窗口的名字
root_window.title('JavaFamily：javafamily.club')

# 设置窗口大小:宽x高,注,此处不能为 "*",必须使用 "x"
# root_window.geometry('450x300')
# 设置主窗口的宽度为 450，高度为 400，同时窗口距离左边屏幕的距离为 300（以像素为单位），距离屏幕顶部的距离为 200
# root_window.geometry('450x400+300+200')
width = 450
height = 300
# 窗口居中，获取屏幕尺寸以计算布局参数，使窗口居屏幕中央
screenwidth = root_window.winfo_screenwidth()
screenheight = root_window.winfo_screenheight()
size_geo = '%dx%d+%d+%d' % (width, height, (screenwidth - width) / 2, (screenheight - height) / 2)
root_window.geometry(size_geo)

# 更改左上角窗口的的icon图标,加载 logo
root_window.iconbitmap('./assets/jfoa.ico')

# 获取电脑屏幕的大小
print("电脑的分辨率是%dx%d" % (root_window.winfo_screenwidth(), root_window.winfo_screenheight()))
# 要获取窗口的大小，必须先刷新一下屏幕, 否则获取到数据为: 1x1
root_window.update()
print("窗口的分辨率是%dx%d" % (root_window.winfo_width(), root_window.winfo_height()))

# 设置主窗口的背景颜色,颜色值可以是英文单词，或者颜色值的16进制数,除此之外还可以使用Tk内置的颜色常量
root_window["background"] = "#C9C9C9"

# 设置窗口处于顶层
root_window.attributes('-topmost', True)

# 添加文本内,设置字体的前景色和背景色，和字体类型、大小
text = tk.Label(root_window, text="JavaFamily，欢迎您", bg="yellow", fg="red", font=('Times', 20, 'bold italic'),
                # 设置标签内容区大小
                width=30, height=5,
                # 设置填充区距离、边框宽度和其样式（凹陷式）
                padx=10, pady=15, borderwidth=10, relief="sunken")
# 将文本内容放置在主窗口内
text.pack()

def button_callback():
    if messagebox.showwarning("关闭", "窗口即将关闭!"):
        # 这里必须使用 quit()关闭窗口
        root_window.quit()


# 添加按钮，以及按钮的文本，并通过command 参数设置关闭窗口的功能
button = tk.Button(root_window, text="关闭", command=button_callback)
# 将按钮放置在主窗口内
button.pack(side="bottom")


# 定义回调函数，当用户点击窗口x退出时，执行用户自定义的函数
def close_callback():
    # 显示一个警告信息，点击确后，销毁窗口
    if messagebox.showwarning("警告", "窗口即将关闭!"):
        # 这里必须使用 destory()关闭窗口
        root_window.destroy()


# 使用协议机制与窗口交互，并回调用户自定义的函数
root_window.protocol('WM_DELETE_WINDOW', close_callback)


# 开启主循环，让窗口处于显示状态
root_window.mainloop()
