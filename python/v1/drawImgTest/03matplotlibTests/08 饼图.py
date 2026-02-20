#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(8, 8))

# 数据
data = np.random.randint(0, 100, 6)
labels = list("ABCDEF")

print("==== data ======\n", data)

# 绘饼图
# plt.pie(data, radius=1, labels=labels, autopct='%.2f%%', startangle=180)

# 绘制圆环图
# wedgeprops: 设置圆环
# pctdistance: 设置label 在圆的位置
plt.pie(data, radius=1.2, labels=labels, autopct='%.2f%%', wedgeprops={'width':0.6}, pctdistance=0.75)

# 显示出来
plt.show()

