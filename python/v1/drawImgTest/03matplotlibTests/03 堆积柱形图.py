#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(15, 6))

# 数据
x = np.arange(4, 10)
y_max = np.array([i for i in np.random.randint(20, 40, len(x))])
y_min = np.array([i for i in np.random.randint(0, 20, len(x))])

print("==== x ======\n", x)
print("==== y_max ======\n", y_max)
print("==== y_min ======\n", y_min)

# 绘图

barWidth = 0.3
# width: 柱子宽度, 默认 0.8
# tick_label: x 轴刻度, 默认取 x 的值
plt.bar(x, y_min, width=barWidth, tick_label = list("abcdef"))
plt.bar(x, y_max, width=barWidth, bottom=y_min)

# 显示出来
plt.show()

