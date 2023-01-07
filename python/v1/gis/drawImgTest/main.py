#!/usr/bin/python3

from datetime import datetime
import calendar
import matplotlib.pyplot as plt
from pylab import *  # 支持中文
import numpy as np

import matplotlib.dates as md

mpl.rcParams['font.sans-serif'] = ['SimHei']

now = datetime.datetime.now()

print(now)

datetimeHourPattern = "%Y-%m-%d %H:00:00";

dataTime = datetime.datetime.strptime(now.strftime(datetimeHourPattern), datetimeHourPattern)

print(dataTime)

names = list(range(130))

for i in range(len(names) - 4):
    names[i] = (dataTime + datetime.timedelta(days = i))

x = range(len(names))
y = np.random.rand(len(names))
y1 = np.random.rand(len(names))
# plt.plot(x, y, 'ro-')
# plt.plot(x, y1, 'bo-')
# pl.xlim(-1, 11) # 限定横轴的范围
# pl.ylim(-1, 110) # 限定纵轴的范围

plt.figure(figsize=(9, 3))

plt.plot(x, y, marker='o', mec='r', mfc='w', label=u'y=x^2曲线图')
plt.plot(x, y1, marker='*', ms=10, label=u'y=x^3曲线图')

plt.legend()  # 让图例生效

plt.xticks(x, names, rotation=0)
plt.margins(0)

# plt.subplots_adjust(bottom=0.15)

plt.xlabel("范围")  # X轴标签
plt.ylabel("值")  # Y轴标签
plt.title("绘图示例 1")  # 标题

plt.grid()

plt.gcf().autofmt_xdate()

plt.gca().xaxis.set_major_formatter(md.DateFormatter("%H"))

plt.show()