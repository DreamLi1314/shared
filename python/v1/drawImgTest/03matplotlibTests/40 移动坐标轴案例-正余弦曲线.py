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

x = np.linspace(-np.pi, np.pi, 1000, endpoint=True)
y1, y2 = np.sin(x), np.cos(x)

ax = plt.axes()

# 绘图
plt.plot(x,y1, label = '正弦曲线')
plt.plot(x, y2, label = '余弦曲线')

#  设置坐标轴的标签
plt.xlabel('x 轴', loc='right')
plt.ylabel('y 轴', loc='top')

plt.legend()

# 设置坐标轴的刻度范围
plt.xlim(x.min() * 1.5, x.max() * 1.5)
plt.ylim(-1.5, 1.5)

# 设置刻度线及刻度标签
plt.xticks([-np.pi, -np.pi/2, 0, np.pi/2, np.pi],
           [r'$-\pi$', r'$-\pi/2$', 0, r'$-\pi/2$', r'$-\pi$'],
           rotation = 45)

# 隐藏上方及右方轴脊
ax.spines['top'].set_color("none")
ax.spines['right'].set_color("none")

# 移动轴
ax.spines['left'].set_position('center')
ax.spines['bottom'].set_position('center')

# 显示出来
plt.show()

