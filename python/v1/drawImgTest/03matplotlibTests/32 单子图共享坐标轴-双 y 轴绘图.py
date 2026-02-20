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
y1 = np.random.randint(10, 50, 12)
y2 = np.random.randint(10, 50, 12)
tem = np.random.normal(loc = 15, scale=5, size = 12)

x = np.arange(1, 13)
labels = [ '{}月'.format(label) for label in x ]

# 1. 绘制堆叠柱形图
fig, ax = plt.subplots()

barA = ax.bar(x, y1, label='产品A', color='orange')
barB = ax.bar(x, y2, label='产品B', bottom = y1, color = 'g')

ax.set_ylim(10, 50)
ax.set_xlabel('月份')
ax.set_ylabel('销售额(亿元)')
ax.set_title('产品A与产品B销售额', fontsize = 16)
ax.set_xticks(x, labels)
ax.set_ylim(0, (y1+y2).max() + 10)

# ax.legend(loc='upper right')

# 2. 绘制折线图
ax_right = ax.twinx()
plot = ax_right.plot(x, tem, 'm--o', lw = 2, ms = 5, label='气温')

ax_right.set_ylabel('气温($^\circ$C)')

plt.legend([barA, barB, plot[0]], ['产品A', '产品B', '平均气温'])

# 显示出来
plt.show()

