#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt
import matplotlib.patches as patches

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

x = np.random.randint(1, 2, 8)
y = np.arange(1, 9)
labels = [ '报告提交', '数据分析', '数据录入', '实地执行', '问卷确定', '试访', '问卷设计', '项目确定' ]
left = [ 10, 8.5, 6.9, 4.2, 3.1, x[7], x[7], 0 ]

ax = plt.axes()

plt.barh(y, x, left = left, tick_label = labels, color='#BF8500')

plt.title('任务甘特图', fontdict={'size': 16})
plt.xlabel('日期')

plt.grid(axis='x')

# 隐藏上方及右方轴脊
ax.spines['top'].set_color("none")
ax.spines['right'].set_color("none")

# 显示出来
plt.show()

