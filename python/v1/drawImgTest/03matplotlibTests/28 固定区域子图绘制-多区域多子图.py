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
y1 = np.random.randint(25, 40, 12)
y2 = np.random.randint(10, 25, 12)
x = np.arange(1, 13)
labels = [ '{}月'.format(label) for label in x ]

fig, axes_arr = plt.subplots(1, 2)

def draw_pic(axes, y, color, title):
    barh = axes.barh(x, y, height=0.5, tick_label = labels, color = color)
    axes.set_xlabel('销量')
    axes.set_ylabel('月份')
    axes.set_title(title)
    axes.set_xlim(0, y.max() + 10)

    for b in barh:
        width = b.get_width()
        axes.text(width + 2, b.get_y() + 0.2, s = "%s" %width, ha="center", va="center")

draw_pic(axes_arr[0], y1, '#BF5900', '各月故事书销量')
draw_pic(axes_arr[1], y2, '#FF8500', '各月童话书销量')

# 显示出来
plt.show()

