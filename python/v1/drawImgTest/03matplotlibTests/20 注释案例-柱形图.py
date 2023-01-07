#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(15, 6))

# 数据
x = np.arange(4, 10)
y_max = np.array([i for i in np.random.randint(20, 40, len(x))])

print("==== x ======\n", x)
print("==== y_max ======\n", y_max)

# 绘图

barWidth = 0.3
# width: 柱子宽度, 默认 0.8
# tick_label: x 轴刻度, 默认取 x 的值
bars = plt.bar(x, y_max, width=barWidth, tick_label = list("abcdef"))

def show_values(bars):
    # pass
    for bar in bars:
        # 获取柱形高度和宽度
        print('height: {}, width: {}'.format(bar.get_height(), bar.get_width()))

        plt.text(bar.get_x() + bar.get_width() / 2,
                 bar.get_height(),
                 '{}'.format(bar.get_height()),
                 fontdict=dict(fontsize=14),
                 ha="center",
                 va="bottom")

show_values(bars)

# 显示出来
plt.show()

