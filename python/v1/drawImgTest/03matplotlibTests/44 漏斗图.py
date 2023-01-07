#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

ax = plt.axes()

# 数据
labels = np.flipud(np.array(['访问商品', '加购物车', '生成订单', '支付订单', '完成交易']))
x1 = np.flipud(np.array([1000, 500, 300, 200, 150]))
x2 = (x1.max() - x1) / 2
x3 = x2 + x1
y = np.arange(len(x1))

# 绘制条形金字塔
height =0.6
right_rects = plt.barh(y, x3, color='r', tick_label=labels, height=height)
left_rects = plt.barh(y, x2, color='w', height=height)

# 绘制边框折线
plt.plot(x3, y, color='#ff5544')
plt.plot(x2, y, color = '#ff5544')


# 写 label
notes = [ '%.f%%' % (i / x1.max() * 100) for i in x1 ]

for rect_r, rect_l, note in zip(right_rects, left_rects, notes):
    tx = rect_l.get_width() + (rect_r.get_width() - rect_l.get_width()) / 2
    ty = rect_r.get_y() + rect_r.get_height() / 2
    plt.text(tx, ty, note, ha='center', va = 'center')

# 隐藏坐标轴

# 隐藏轴脊
for dir in ['top', 'bottom', 'left', 'right']:
    ax.spines[dir].set_color('none')

# 隐藏刻度
ax.xaxis.set_ticks_position('none')
ax.set_xticklabels([])
ax.yaxis.set_ticks_position('none')


# 显示出来
plt.show()

