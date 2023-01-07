#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
from matplotlib.sankey import Sankey
import matplotlib.pyplot as plt

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

# 数据: 消费收入与支出数据 正为流入, 负为六春湖
flows = [0.7, 0.3, -0.3, -0.1, -0.3, -0.1, -0.1, -0.1]

# 流的标签列表
labels = ['工资', '副业', '生活', '购物', '深造', '运动', '其他', '买书']

# 流的方向 1: 向上, 0, 水平, -1 向下
orientations = [1, 1, 0, -1, 1, -1, 1, 0]

sankey = Sankey()

sankey.add(
    flows= flows,
    labels = labels,
    orientations=orientations,
    color='black',
    fc = 'lightgreen',
    patchlabel='生活消费',
    alpha = 0.7
)

diagrams = sankey.finish()

diagrams[0].text.set_fontsize(20)
diagrams[0].text.set_fontweight("bold")
diagrams[0].texts[4].set_color('r')

# 显示出来
plt.show()

