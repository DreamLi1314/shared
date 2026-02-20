#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

# 数据
x1 = np.linspace(-np.pi, np.pi, 500)
y1 = np.cos(x1) * 2

x4 = np.arange(0, 10, 0.5)
y4 = np.power(x4, 3)

def draw_plot_axes(axes, x, y):
    axes.plot(x, y)

ax1 = plt.subplot(221)

# ax4 = plt.subplot(224)
# 共享坐标轴
# ax4 = plt.subplot(224, sharex = ax1)
ax4 = plt.subplot(224, sharey = ax1)

draw_plot_axes(ax1, x1, y1)
draw_plot_axes(ax4, x4, y4)

# 显示出来
plt.show()

