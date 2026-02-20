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

# 数据
x = np.random.randint(0, 100, 50)
y = np.random.randint(0, 100, 50)

# 绘图
plt.scatter(x, y, c = 'red', alpha=0.5)

# 显示出来
plt.show()

