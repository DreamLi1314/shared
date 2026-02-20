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
# 等差数列 [-pi, pi]
x = np.linspace(-np.pi, np.pi, 1000, endpoint=True)
y1, y2 = np.sin(x), np.cos(x)

# 绘图
plt.plot(x,y1, x, y2)

#  设置坐标轴的标签

plt.xlabel('x 轴', labelpad=10)
plt.ylabel('y 轴')

# 设置坐标轴的刻度范围
plt.xlim(x.min() * 1.5, x.max() * 1.5)
plt.ylim(-1.5, 1.5)

# 设置刻度线及刻度标签
plt.xticks([-np.pi, -np.pi/2, 0, np.pi/2, np.pi],
           [r'$-\pi$', r'$-\pi/2$', 0, r'$-\pi/2$', r'$-\pi$'],
           rotation = 45)

# 显示出来
plt.show()

