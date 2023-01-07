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

# 绘制多变形
# polygon = patches.RegularPolygon((0.5, 0.5), 6, 0.3, color='g') # 6边型
polygon = patches.Circle((0.5, 0.5), 0.3, color='y')

ax = plt.axes()
ax.add_patch(polygon)

ax.spines['top'].set_color("none")
ax.spines['right'].set_color("none")

# 移动坐标轴, 支持多种方式, data: 指定根据数据移动
# ax.spines['left'].set_position(('data', 0.5))
# ax.spines['bottom'].set_position(('data', 0.5))
# 等价于以下写法
ax.spines['left'].set_position('center')
ax.spines['bottom'].set_position('center')

# 显示出来
plt.show()

