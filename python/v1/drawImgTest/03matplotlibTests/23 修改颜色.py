#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(12, 8), dpi=80)

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

for name,hex in matplotlib.colors.cnames.items():
    print("{} ---- {}".format(name, hex))

# 数据
data = np.random.randint(100, 1000, 6)
labels = ['购物', '人情往来', '餐饮美食', '通信物流', '生活用品', '其他']

# plt.plot(labels, data, color = 'red') # 字符
# plt.plot(labels, data, color = 'r') # 字符简称
plt.plot(labels, data, color = (0.5, 1, 0.5)) # RGB

# 显示出来
plt.show()

