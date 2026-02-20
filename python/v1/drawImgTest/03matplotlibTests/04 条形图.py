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
x1 = np.arange(4, 10)
y_max = np.array([i for i in np.random.randint(20, 40, len(x1))])
y_min = np.array([i for i in np.random.randint(0, 20, len(x1))])

print("==== x ======\n", x1)
print("==== y_max ======\n", y_max)
print("==== y_min ======\n", y_min)

# 绘图

barHeight = 0.3
plt.barh(x1,y_min, height=barHeight, tick_label = list("abcdef"))
plt.barh(x1+ barHeight, y_max, height=barHeight)

# 显示出来
plt.show()

