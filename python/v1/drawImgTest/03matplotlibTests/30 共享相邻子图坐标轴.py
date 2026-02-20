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
x1 = np.linspace(0, np.pi, 500)
y1 = np.cos(x1)**2
x2 = np.linspace(0, 10, 500)
y2 = np.sin(x2)
x3 = np.arange(0, 5, 0.5)
y3 = np.random.randint(0, 5, 10)
x4 = np.arange(0, 5, 0.5)
y4 = np.power(x4, x4)

def draw_plot_axes(axes, x, y):
    axes.plot(x, y)

def draw_scatter_axes(axes, x, y):
    axes.scatter(x, y)

# 共享坐标轴
# fig, axes_arr = plt.subplots(2, 2)
# fig, axes_arr = plt.subplots(2, 2, sharex='row')
# fig, axes_arr = plt.subplots(2, 2, sharex='all', sharey='all')
fig, axes_arr = plt.subplots(2, 2, sharex='row', sharey='col')

draw_plot_axes(axes_arr[0][0], x1, y1)
draw_plot_axes(axes_arr[0][1], x2, y2)
draw_scatter_axes(axes_arr[1][0], x3, y3)
draw_scatter_axes(axes_arr[1][1], x4, y4)

# 显示出来
plt.show()

