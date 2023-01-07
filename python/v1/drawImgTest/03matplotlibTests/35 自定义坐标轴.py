#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

gs = gridspec.GridSpec(3, 3)

ax1 = plt.subplot(gs[0, :])
ax1.plot([0,1], [1, 2])

ax2 = plt.subplot(gs[1, :2])
ax2.plot([0,1], [1, 2])

ax3 = plt.subplot(gs[1:, -1])
ax3.plot([0,1], [1, 2])

# 显示出来
plt.show()

