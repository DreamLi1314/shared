#!/usr/bin/python3

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.dates import DateFormatter, HourLocator, DayLocator, AutoDateLocator
from matplotlib.ticker import FixedLocator, MultipleLocator
from PIL import Image


def draw_axhline(y, plot):
    plt.axhline(y=y, ls='--', color='orange')


df = pd.read_csv('./testData.csv', index_col='index')
# index 转化为 Timestamp
df.index = pd.to_datetime(df.index)

# 绘制风速折线图 10m风 WindKnot 、 10m 阵风 GustKnot、风向 WindDir
wind_knot = df['WindKnot']
gust_knot = df['GustKnot']
wind50_knot = df['Wind50']
wind_dir = df['WindDir']

x_dates = df.index.tolist()
x = np.arange(len(x_dates))

# 开始绘图
plt.figure(figsize=(20, 4))
# [left, bottom, width, height]
ax = plt.axes((0.05, 0.3, 0.9, 0.45))

# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

# 绘图
knot_plot = plt.plot(wind_knot, color='#88BA15', lw=2, label='10m风')
plt.plot(gust_knot, color='#3FD2D4', lw=2, label='10m阵风')
plt.plot(wind50_knot, ls='--', color='black', lw=2, label='50m风')

# 设置阈值线
draw_axhline(17, knot_plot)

# 图例
plt.legend(ncol=3, bbox_to_anchor=[0.61, -0.4], edgecolor='none')

# grid 网格
plt.grid(visible=True, axis='y', linewidth=1, linestyle='-', color='gray')
plt.grid(visible=False, axis='x', which='major', linewidth=1, linestyle='-', color='lightgray')
plt.grid(visible=True, axis='x', which='minor', linewidth=1, linestyle='-', color='lightgray')

# 设置标题
plt.title('Wind', pad=45, fontdict={'size': 20, 'fontweight': 'bold'})

# 设置刻度标签
plt.xlabel('Date/Time', labelpad=10, fontdict={'size': 14, 'fontweight': 'bold'})
plt.ylabel('Wind Speed', labelpad=15, fontdict={'size': 14, 'fontweight': 'bold'})

# 隐藏轴脊
ax.spines['right'].set_color("none")
ax.spines['left'].set_color("none")

# 设置刻度格式
ax.xaxis.set_major_locator(AutoDateLocator())
ax.xaxis.set_minor_locator(AutoDateLocator())
hour_df = DateFormatter('%H')
day_df = DateFormatter('%m-%d')
ax.xaxis.set_major_formatter(day_df)
ax.xaxis.set_minor_formatter(hour_df)

# 设置刻度样式
hour_locator = HourLocator(interval=3)
day_locator = DayLocator(interval=1)
ax.xaxis.set_major_locator(day_locator)
ax.xaxis.set_minor_locator(hour_locator)

ax.xaxis.set_tick_params('major', length=0, labelsize=12, pad=20)
ax.xaxis.set_tick_params('minor', length=0, labelsize=10, pad=5)


y_step = 5
ax.yaxis.set_major_locator(MultipleLocator(y_step))
ax.yaxis.set_tick_params('both', length=0)
plt.ylim(0, gust_knot.max() + (y_step - gust_knot.max() % y_step))

import datetime

plt.xlim(df.index[0] - datetime.timedelta(hours=3), df.index[-1] + datetime.timedelta(hours=3))

# 绘制风向
# wind_dir_icon = Image.open('./wind-dir-icon.png')

# plt.text(x_dates[0], 26 , 'A')

# for xd in np.arange(0, len(x_dates), 3):
#     icon = wind_dir_icon.rotate(wind_dir[xd])
#     plt.figimage(icon, 275 + xd * 22, 750)
#
# plt.show()


def uv(angle):
    if 0 < angle <= 180:
        dx = 1
        dy = np.tan(np.deg2rad(90 - angle))
    else:
        dx = -1
        dy = np.tan(np.deg2rad(angle - 270))

    return dx, dy

import matplotlib.dates as mdates

ax2 = plt.axes((0.05, 0.75, 0.9, 0.07), sharex=ax)
arrow_para = dict(headwidth=800, headlength=750,  headaxislength=600, width=1E-5, pivot='tip')
abbrs = ['N', 'NNE', 'NE', 'ENE', 'E', 'ESE', 'SE', 'SSE', 'S', 'SSW', 'SW', 'WSW', 'W', 'WNW', 'NW', 'NNW']
ticklabels = []
xs = []

for xd in np.arange(0, len(x_dates), 6):
    wdir = wind_dir[xd]
    t = df.index[xd]
    # x = mdates.datestr2num(t.strftime('%y-%m-%d %H'))
    x = mdates.date2num(t)
    u, v = uv(angle=wdir)
    ax2.quiver(x, 0.2, u, v, **arrow_para)

    ticklabels.append(abbrs[int(wdir % 360 / 22.5)])
    xs.append(x)
plt.axis('off')

ax3 = plt.axes((0.05, 0.85, 0.9, 0.03))
ax3.xaxis.set_ticks(xs)
ax3.set_xlim(mdates.date2num(df.index[0] - datetime.timedelta(hours=3)), mdates.date2num(df.index[-1] + datetime.timedelta(hours=3)))
ax3.xaxis.set_ticklabels(ticklabels)
# ax3.xaxis.ticklabel.set_fontsize(8)
ax3.spines['top'].set_color('none')
ax3.spines['left'].set_color('none')
ax3.spines['right'].set_color('none')
ax3.yaxis.set_visible(False)
ax3.tick_params(axis='both', which='both', length=0)
ax3.spines['bottom'].set_color('none')

plt.show()

