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
# 等差数列 [-pi, pi]
x = np.linspace(-np.pi, np.pi, 1000, endpoint=True)
y1, y2 = np.sin(x), np.cos(x)

# 绘图
plt.plot(x,y1, x, y2)

#  设置坐标轴的标签

plt.xlabel('x 轴', labelpad=10)
plt.ylabel('y 轴')

# 设置坐标轴的刻度范围
plt.xlim(x.min() * 1.5, x.max() * 1.5)
plt.ylim(-1.5, 1.5)

# 设置刻度线及刻度标签
plt.xticks([-np.pi, -np.pi/2, 0, np.pi/2, np.pi],
           [r'$-\pi$', r'$-\pi/2$', 0, r'$\pi/2$', r'$-\pi$'],
           rotation = 45)

# 设置标题
plt.title('正弦函数与余弦函数', loc='left', pad=30)

# 设置图例
# fancybox: 圆角
# ncol: 列数, 默认 1
plt.legend(['正弦', '余弦'], title = "图例标题", loc = 'upper left', shadow = True, fancybox = True, ncol = 2)

# 设置网格
# plt.grid(b = True)
# axis: x, y, both
plt.grid(b = True, axis='both', linewidth = 0.3)

# 设置参考线

# 水平参考线
plt.axhline(y = 0, linestyle = '--')
# 垂直参考线
plt.axvline(x = 0, linestyle = '--')
# 水平参考区域
plt.axhspan(ymin=0.5, ymax=1, alpha = 0.3)
# 垂直参考区域
plt.axvspan(xmin=0, xmax=np.pi / 2, alpha = 0.3)

# 添加指向性注释
# plt.annotate('最小值', (-np.pi / 2, -1), (-2, -1.25), arrowprops=dict(arrowstyle='-|>'))
plt.annotate('最小值', (-np.pi / 2, -1), (-2, -1.25), arrowprops=dict(arrowstyle='simple'))

# 添加非指向性注释
plt.text(3.2, 0.1, 'y=sin(x)', fontdict=dict(fontsize=16), bbox=dict(alpha=0.5))
plt.text(3.2, -0.9, 'y=cos(x)', fontdict=dict(fontsize=16), bbox=dict(alpha=0.5))

# 添加数据表
#          0    pi/2    pi
# sin(x)   0    1       0
# cos(x)   1    0       -1
# cellText: 数据域
# cellLoc: 数据对齐方式
# colWidths: 列宽度
# rowLabels: 行标题
# colLabels: 列标题
# colColours: 列标题背景色
# loc: 表格位置
plt.table(cellText= [ [0,1,0], [1,0,-1] ],
          cellLoc='center',
          colWidths=[0.1] * 3,
          rowLabels=['sin(x)', 'cos(x)'],
          colLabels=[ '0', r'$\pi/2$', r'$\pi$'],
          colColours=['red'] * 3,
          loc='lower right')

# 显示出来
plt.show()

