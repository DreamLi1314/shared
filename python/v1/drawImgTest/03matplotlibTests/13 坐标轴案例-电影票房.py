#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(12, 4), dpi=80)

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

labels = [ '哪吒', '大鹏', '复联四', '蜘蛛侠', '银河补习班' ]

bar_widths = [48.9, 8, 48.2, 33, 16]

y = range(len(labels))

print("==== y ====\n", y)

plt.barh(y, bar_widths, height=0.6)

plt.yticks(y, labels)

plt.xlabel('总票房(亿元)')

plt.show()