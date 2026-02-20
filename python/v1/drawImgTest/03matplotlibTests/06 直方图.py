#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(20, 8), dpi=80)

# 数据
scores = np.random.randint(0, 100, 50)

print("==== scores ======\n", scores)

# 绘图

# bar, step, barstacked, stepfilled
# 绘制频数图
plt.hist(scores, bins=10, histtype='stepfilled')

# 绘制频率图
# plt.hist(scores, bins=10, histtype='step', density=True)

# 绘制累积频率图
# plt.hist(scores, bins=10, histtype='barstacked', density=True, cumulative=True)

# 显示出来
plt.show()

