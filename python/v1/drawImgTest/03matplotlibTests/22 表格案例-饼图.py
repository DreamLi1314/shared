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
data = np.random.randint(100, 1000, 6)
labels = ['购物', '人情往来', '餐饮美食', '通信物流', '生活用品', '其他']

plt.pie(data, labels=labels, autopct='%.1f%%')

plt.legend(bbox_to_anchor=[1.1,1.1])

# 表格
plt.table(cellText=[data], cellLoc='center',
          colLabels=labels,
          rowLabels=['金额(元)'],
          loc='lower center')

# 显示出来
plt.show()

