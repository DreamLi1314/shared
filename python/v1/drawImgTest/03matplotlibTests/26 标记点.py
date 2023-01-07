#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(12, 8), dpi=80)

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

# 数据
y1 = np.random.randint(7, 9, 30)
y2 = np.random.randint(5, 7, 30)
y3 = np.random.randint(2, 6, 30)
labels = np.arange(1, 31)

# plt.plot(labels, y1, label = '产品A', color = 'g', ls = '--', marker = 'D')
# plt.plot(labels, y2, label = '产品B', color = 'r', ls = ':', marker = '*')
# plt.plot(labels, y3, label = '产品C', color = 'y', ls = '-', marker = '^')

plt.plot(labels, y1, 'gD--', labels, y2, 'r*:', labels, y3, 'y^-')

plt.grid(alpha = 0.3)

plt.xlabel("日期")
plt.ylabel("销量")

plt.legend([ '产品A', '产品B', '产品C' ])

# 显示出来
plt.show()

