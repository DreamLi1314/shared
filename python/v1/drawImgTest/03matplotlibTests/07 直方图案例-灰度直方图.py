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
random = np.random.RandomState(100)

x = random.randn(10000)

print("==== x ======\n", x)

# 绘制频数图
plt.hist(x, bins=25, density=True)

# 显示出来
plt.show()

