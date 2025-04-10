#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

fig, axes = plt.subplots(2, 2)

ax1 = axes[0][0]
ax1.set_title('Title 1')

ax2 = axes[0][1]
ax2.set_title('Title 2')

ax3 = axes[1][0]
ax3.set_title('Title 3')

ax4 = axes[1][1]
ax4.set_title('Title 4')

# 紧凑布局
# pad: 画布边缘与子图边缘的间距
# w_pad: (width) 水平方向相邻子图的间距
# h_pad: (height) 垂直方向相邻子图的间距
plt.tight_layout(pad=5, w_pad=20, h_pad=15)

# 显示出来
plt.show()

