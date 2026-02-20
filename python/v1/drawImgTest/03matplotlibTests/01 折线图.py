#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(15, 6), dpi=80)

# 数据
x = np.arange(4, 19)
y_max = np.array([i for i in np.random.randint(20, 40, len(x))])
y_min = np.array([i for i in np.random.randint(0, 20, len(x))])

# 绘图
# plt.plot(x, y_max)
# plt.plot(x, y_min)

plt.plot(x, y_max, x, y_min)

# 保存图片
plt.savefig('./01折线图.png')

# 显示出来
plt.show()

