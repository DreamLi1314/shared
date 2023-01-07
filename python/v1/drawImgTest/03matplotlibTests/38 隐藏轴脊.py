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

# 1. 隐藏轴脊: 上, 右, 左
ax.spines['top'].set_color("none")
ax.spines['right'].set_color("none")
ax.spines['left'].set_color("none")

# 2. 隐藏左边刻度及刻度标签
# 隐藏刻度
ax.yaxis.set_ticks_position('none')
# 隐藏刻度标签
ax.set_yticklabels([])

# 显示出来
plt.show()

