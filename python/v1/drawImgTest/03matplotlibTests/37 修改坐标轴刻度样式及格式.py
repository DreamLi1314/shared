#!/usr/bin/python3

import numpy as np
import pandas as pd
import string
import matplotlib.pyplot as plt
from matplotlib.dates import DateFormatter, HourLocator

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

x_dates = pd.date_range(start='2022/12/28 05:00:00', periods=126, freq='H')
y = np.random.randint(0, 25, 126)

ax = plt.axes()

plt.plot(x_dates, y, '-d', ms = 8, mfc = 'y')
plt.xlabel('时间(h)')
plt.ylabel('平均风速(km/h)')
plt.title("未来10天风速预报")

# 设置刻度样式
locator = HourLocator(interval=3)
ax.xaxis.set_major_locator(locator)
# plt.tick_params(direction = 'in', length=6, width = 2, labelsize=12)
ax.xaxis.set_tick_params(labelrotation=-45, direction = 'in', length=6, width = 2, labelsize=12)

# 设置刻度格式
df = DateFormatter('%H:%M')
ax.xaxis.set_major_formatter(df)

# 显示出来
plt.show()

