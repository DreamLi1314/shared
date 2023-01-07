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

# 数据
y1 = np.random.randint(100, 1000, 6)
y2 = np.random.randint(100, 1000, 6)
labels = ['家庭', '小说', '心理', '科技', '儿童', '其他']

plt.bar(labels, y1, label = '地区一', color = '#FFCC00')
plt.bar(labels, y2, bottom=y1, label = '地区二', color = '#B0C4DE')

plt.title("地区一二各类图书销量情况")
plt.xlabel('图书种类')
plt.ylabel('销量(本)')
plt.legend()

plt.grid(True, axis='y', color = 'gray', alpha = 0.3)

# 显示出来
plt.show()

