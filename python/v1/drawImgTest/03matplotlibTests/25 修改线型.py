#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
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
y1 = np.random.randint(7, 9, 30)
y2 = np.random.randint(5, 7, 30)
labels = np.arange(1, 31)

plt.plot(labels, y1, label='2019年7月汇率', color = 'r', linewidth=2)
plt.plot(labels, y2, label='2017年7月汇率', color = 'g', linewidth=2, ls='--')

plt.xlabel('日期')
plt.ylabel('汇率')

plt.legend(loc='lower right')

# 显示出来
plt.show()

