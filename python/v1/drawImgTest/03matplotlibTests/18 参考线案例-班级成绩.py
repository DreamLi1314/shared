#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt

# figsize 图像宽高, 单位:英寸
# dpi: 分辨率
# facecolor: 背景色
# edgecolor: 边框颜色
plt.figure(figsize=(15, 10), dpi=80)

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

# 数据
man_scores = np.random.randint(70, 100, 6)
women_scores = np.random.randint(70, 100, 6)
classes = ['高二1班', '高二2班', '高二3班', '高二4班', '高二5班', '高二6班']

avg = (man_scores.mean() + women_scores.mean()) / 2

x = np.arange(len(man_scores))

# 绘图
bar_width = 0.3
plt.bar(x - bar_width / 2, man_scores, width=bar_width, label = '男生平均成绩')
plt.bar(x + bar_width / 2, women_scores, width=bar_width, label = '女生平均成绩')

# 设置刻度标签
plt.xticks(x, labels=classes)

# 设置轴 Label
plt.xlabel('班级')
plt.ylabel('平均分')

# 设置参考线
plt.axhline(y=avg, ls = '--', linewidth=1.2, label = '学生平均成绩')

# 设置图例
plt.legend(loc = 'lower right')

# 设置标题
plt.title("高二各班男生女生平均成绩", pad=10)

# 显示出来
plt.show()

