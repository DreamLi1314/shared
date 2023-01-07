#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(8, 8))

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

# 数据
data = np.random.randint(100, 1000, 6)
labels = ['购物', '人情往来', '餐饮美食', '通信物流', '生活用品', '其他']

print("==== data ======\n", data)

dev_position = [ 0, 0, 0.3, 0, 0, 0 ]

plt.pie(data, radius=1.2, labels=labels, autopct='%.1f%%', shadow=True, startangle=90, explode=dev_position)

# 显示出来
plt.show()

