#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt
from pywaffle import Waffle

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

plt.figure(figsize=(4,4), dpi=80,
           FigureClass=Waffle,
           rows=10,
           columns=10,
           values=[10, 85,5],
           colors=[ 'red', '#20B2AA', '#d3d3d3'],
           vertical=True,
           title={'label':"电影<<少年的你>>上座率"},
           legend={'loc': 'upper right', 'labels': [ '情侣座', '占座', '空座']})

# 显示出来
plt.show()

