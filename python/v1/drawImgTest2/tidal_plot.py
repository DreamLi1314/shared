import csv
import matplotlib.pyplot as plt
import numpy as np
import datetime
from matplotlib.dates import DateFormatter, HourLocator, DayLocator, AutoDateLocator
# 中文支持
plt.rcParams['font.sans-serif'] = ['SimHei']
# 有时间负号也不能显示
plt.rcParams['axes.unicode_minus'] = False

data_time_list = []
data_list = []
# 打开csv文件
with open('./testData.csv', newline='') as csv_file:
    # 创建csv读取器
    reader = csv.reader(csv_file)
     # 读取表头
    headers = next(reader)
     # 获取指定列的索引s
    col_index = headers.index('TidalHeight')
    data_index = headers.index('index')
     # 迭代读取每一行数据
    for row in reader:
        # 获取指定列的数据并输出
        if row[col_index] == "-":
            data_time_list.append(row[data_index])
            data_list.append(float(0.0))
        else:
            data_time_list.append(row[data_index])
            data_list.append(float(row[col_index]))
# #截取时间
time_mon_day_list = []
time_hour_list = []

for i in range(0,72):
    time_data = data_time_list[i]
    time_mon_day = time_data.split(" ")[0].split("-")[0] + time_data.split(" ")[0].split("-")[1] + time_data.split(" ")[0].split("-")[2]
    time_mon_hour = time_data.split(" ")[1].split(":")[0]
    time_mon_day_list.append(time_mon_day)
    time_hour_list.append(time_mon_hour)

time_value_01 = [datetime.time(a, 0) for a in range(int(time_hour_list[0]), 24, 1)]



day_start = time_mon_day_list[0]
day_end = time_mon_day_list[-1]


s_day = datetime.datetime.strptime(day_start, '%Y%m%d')
s_end = datetime.datetime.strptime(day_end, '%Y%m%d')
day_num =  int(str(s_end - s_day).split(" ")[0])

data_time_list = []
days_1 = [s_day + datetime.timedelta(days=0)]
days = [s_day + datetime.timedelta(days=i+1) for i in range(day_num)]

time_values_02 = [datetime.time(i, 0) for i in range(0, 24, 1)]




data_time_list_1 = [datetime.datetime.combine(day, time_1) for day in days_1 for time_1 in time_value_01]

data_time_list_2 = [datetime.datetime.combine(day, time) for day in days for time in time_values_02]

data_time_list_1.extend(data_time_list_2)

print(data_time_list_1)

t = np.array(data_time_list_1)[:78]
s = np.array(data_list)[:78]

mean_level = s.mean()

 # 绘制潮汐图
fig, ax = plt.subplots(figsize=(10,4))
ax.plot(t, s, color='blue', lw=2)



# # 使用fill_between()函数填充颜色
ax.fill_between(t, 0, s, where=s >= 0, facecolor='red', alpha=0.2,interpolate=True)
ax.fill_between(t, 0, s, where=s < 0, facecolor='blue', alpha=0.2,interpolate=True)

# 图例
plt.legend(ncol=3, bbox_to_anchor=[0.61, -0.4], edgecolor='none')

# grid 网格
plt.grid(visible=True, axis='y', linewidth=1, linestyle='-', color='gray')
plt.grid(visible=False, axis='x', which='major', linewidth=1, linestyle='-', color='lightgray')
plt.grid(visible=True, axis='x', which='minor', linewidth=1, linestyle='-', color='lightgray')

# 设置标题
ax.set_title('潮汐',fontsize=20, fontweight='bold')
#plt.title('潮汐', pad=45, fontdict={'size': 20, 'fontweight': 'bold'})

# 设置刻度标签
plt.xlabel('时间/日期', labelpad=10, fontdict={'size': 14, 'fontweight': 'bold'})
plt.ylabel('潮高 (m)', labelpad=15, fontdict={'size': 14, 'fontweight': 'bold'})

# # 隐藏轴脊
ax.spines['right'].set_color("none")
ax.spines['left'].set_color("none")



# 设置刻度格式
ax.xaxis.set_major_locator(AutoDateLocator())
day_df = DateFormatter('%m-%d')
ax.xaxis.set_major_formatter(day_df)

# 设置刻度样式
day_locator = DayLocator(interval=1)
ax.xaxis.set_major_locator(day_locator)
ax.xaxis.set_tick_params('major', length=1, labelsize=12, pad=25)




# ax.xaxis.set_minor_locator(plt.MultipleLocator(3))
hour_df = DateFormatter('%H')
ax.xaxis.set_minor_formatter(hour_df)
hour_locator = HourLocator(interval=3)
ax.xaxis.set_minor_locator(hour_locator)
ax.xaxis.set_tick_params('minor', length=5, labelsize=10, pad=2)

# + datetime.timedelta(hours=3)
plt.xlim(t[0]+datetime.timedelta(hours=1), t[-1])

# 设置统一的纵坐标高度
# ax.axhline(mean_level, color='red', lw=2,linestyle='--')


# 展示图形
#plt.show()

# import sys
# sys.exit()

save_path = "./chaoxi.png"

plt.savefig(save_path,dpi=300,bbox_inches="tight")  # 保存图片到本地文件系统中，不要再次显示。 