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

df = pd.read_excel('../data.xlsx')

print(df)

dfg = df.groupby(by='gender')

x_female = dfg.get_group('female')['count'].values.tolist()
print(x_female)

x_male = (-dfg.get_group('male')['count'].values).tolist()
print(x_male)

df_age = df.groupby(by='AgeGroup').sum(numeric_only = True)
labels = df_age.index.array
print(labels)

y = np.arange(len(labels))

plt.barh(y, x_female, tick_label = labels, label='女')
plt.barh(y, x_male, label='男')

plt.xlabel('人数')
plt.ylabel('年龄段(岁)')
plt.title('人口金字塔')
plt.legend()

# 显示出来
plt.show()

