import tkinter as tk

root_window = tk.Tk()

root_window.title("文本框控件")

width = 450
height = 300
# 窗口居中，获取屏幕尺寸以计算布局参数，使窗口居屏幕中央
screenwidth = root_window.winfo_screenwidth()
screenheight = root_window.winfo_screenheight()
size_geo = '%dx%d+%d+%d' % (width, height, (screenwidth - width) / 2, (screenheight - height) / 2)
root_window.geometry(size_geo)

text = tk.Text(root_window, width=60, height=3, undo=True)
text.grid()

text.insert(tk.INSERT, 'JavaFamily(www.javafamily.club) 是一个神奇的网站!')


# 定义撤销和恢复方法，调用edit_undo()和 edit_redo()方法
def backout():
    text.edit_undo()


def regain():
    text.edit_redo()


# 定义撤销和恢复按钮
tk.Button(root_window, text='撤销', command=backout).grid(row=3, column=0, sticky="w", padx=10, pady=5)
tk.Button(root_window, text='恢复', command=regain).grid(row=3, column=0, sticky="e", padx=10, pady=5)


# 获取字符，使用get() 方法
def print_select_content():
    # 获取从第 1 行第 3 列开始, 到第 2 行结尾的内容
    print(text.get("1.3", "2.end"))


button = tk.Button(root_window, text='获取数据', command=print_select_content).grid(row=4, column=0, sticky="w", padx=10,
                                                                                pady=5)
# 在Text控件内插入-个按钮
# text.window_create(tk.END, window=button)

# Tag

text.tag_add('name', '1.0', '1.10')
text.tag_config('name', background="yellow", foreground="blue", underline=1)

# Mark

# 设置标记，这里的 1.end 表示 第一行最后一个字符，当然也可以使用数字来表示比如 1.5 表示第一行第五个字符
text.mark_set("mark", "1.end")
# 在标记之后插入相应的文字
text.insert("mark", "网址：javafamily.club")
# 跟着自动移动，往后插入，而不是停留在原位置
text.insert("mark", ",欢迎光临")

root_window.mainloop()
