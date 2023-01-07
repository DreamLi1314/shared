#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt
import matplotlib.patches as patches

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

def show_values(x, y):
    for xt, yt in zip(x, y):
        plt.text(xt, yt + 5, s='{}'.format(yt), ha = 'center')

x = np.arange(1, 13)
y = x**2 + x
labels = [ '{}月'.format(index) for index in x ]

ax = plt.axes()

marker, lines, base_line = plt.stem(x, y, linefmt='--', markerfmt='d', use_line_collection=True)

plt.setp(marker, color='r', ms = 10)
plt.setp(lines, color='g', lw = 1)
plt.setp(base_line, color='w')

plt.ylim(0, y.max() + 5)

plt.xticks(x, labels)

# 隐藏上方及右方轴脊
ax.spines['top'].set_color("none")
ax.spines['right'].set_color("none")

show_values(x, y)

# 显示出来
plt.show()

