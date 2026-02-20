#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

def show_values(axes, bar_rects):
    for rect in bar_rects:
        width = rect.get_width()
        height = rect.get_height()
        axes.text(rect.get_x() + width / 2, height + 1, s='{}'.format(height), ha='center')

# 数据
y = np.random.randint(25, 40, 12)
y1 = np.random.randint(25, 40, 12)
y2 = np.random.randint(10, 25, 12)
x = np.arange(1, 13)
labels = [ '{}月'.format(label) for label in x ]

def draw_pie(axes, y, title):
    axes.pie(y, labels=labels, autopct="%.1f%%", radius=1.2, textprops={'fontsize': 12})
    axes.set_title(title, fontsize = 14, y=1.2)

axes = plt.subplot2grid((3, 2), (0, 0), rowspan=2, colspan=2)

bar_rects = axes.bar(x, y, width = 0.6, tick_label = labels, color='#13A4AC')

axes.set_title('抖音各月人数增长情况', fontsize = 14)
axes.set_ylabel('增长人数')
axes.set_ylim(0, y.max() + 10)
axes.axhline(y.mean(), lw = 1, ls = '--', color='gray')

show_values(axes, bar_rects)

pie1 = plt.subplot2grid((3, 2), (2, 0))
draw_pie(pie1, y1, '2017年各月销量')

pie2 = plt.subplot2grid((3, 2), (2, 1))
draw_pie(pie2, y2, '2018年各月销量')

# 设置紧凑布局
plt.tight_layout()

# 显示出来
plt.show()

