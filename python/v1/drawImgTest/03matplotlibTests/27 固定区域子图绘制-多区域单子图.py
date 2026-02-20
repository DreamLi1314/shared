#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(15, 8), dpi=80)

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

# 数据
y1 = np.random.randint(25, 40, 12)
y2 = np.random.randint(10, 25, 12)
x = np.arange(1, 13)
labels = [ '{}月'.format(label) for label in x ]

# 1. 绘制折线图

ax1 = plt.subplot(2, 1, 1)

ax1.plot(x, y1, 'm--o', lw = 2, ms = 5, label='产品A')
ax1.plot(x, y2, 'g--o', lw = 2, ms = 5, label='产品B')

ax1.set_ylim(10, 50)
ax1.set_xlabel('月份')
ax1.set_ylabel('销售额(亿元)')
ax1.set_title('产品A与产品B销售额', fontsize = 16)
ax1.set_xticks(x, labels)

ax1.legend(loc='upper right')

# 显示数据
for xy1 in zip(x, y1):
    ax1.annotate("%s"% xy1[1], xy1, textcoords='offset points', xytext=(-5, 5))

for xy2 in zip(x, y2):
    ax1.annotate("%s"% xy2[1], xy2, textcoords='offset points', xytext=(-5, 5))

# 2. 绘制饼图
def draw_pie(index, y_data, title):
    ax2 = plt.subplot(2, 2, index)
    ax2.pie(y_data, labels=labels, autopct="%.1f%%", radius=1, wedgeprops={'width':0.5}, pctdistance=0.75)
    ax2.set_title(title, fontsize=16)

draw_pie(3, y1, '产品A销量')
draw_pie(4, y2, '产品B销量')

# 显示出来
plt.show()

