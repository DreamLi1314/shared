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

# [left, bottom, width, height]
# 0.2: 距离左边 20%
# 0.3: 距离下方 30%
# 0.6: 宽度占 60%
# 0.7: 高度占 70%
ax = plt.axes((0.2, 0.3, 0.6, 0.7))

ax.plot(np.arange(10), np.arange(10))

# 显示出来
plt.show()

