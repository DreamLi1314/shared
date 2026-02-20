#!/usr/bin/python3

import numpy as np
import pandas as pd
import string

# loc 使用自定义索引查询, iloc 使用自动生成的整数索引

df1 = pd.read_csv("../testData.csv", index_col=0)

print("==== df1 ======\n", df1)

# 访问单行数据
print("==== df1.loc['2022-12-17 20:00:00'] ======\n", df1.loc["2022-12-17 20:00:00"])

# pandas.core.series.Series
print("==== type(df1.loc['2022-12-17 20:00:00']) ======\n", type(df1.loc["2022-12-17 20:00:00"]))


# 访问多行数据
print("==== df1.loc 2 ======\n", df1.loc[['2022-12-08 20:00:00', '2022-12-17 20:00:00']])

# pandas.core.frame.DataFrame
print("==== type(df1.loc 2) ======\n", type(df1.loc[['2022-12-08 20:00:00', '2022-12-17 20:00:00']]))


# 访问指定位置的数据 (x, y)
print("==== df1.loc['2022-12-08 20:00:00', 'CurrentDir'] ======\n", df1.loc['2022-12-08 20:00:00', 'CurrentDir'])

# 访问指定行的多列数据 (x, y1),(x, y2)
print("==== df1.loc['2022-12-08 20:00:00', ['CurrentSpeed',  'CurrentDir']] ======\n", df1.loc['2022-12-08 20:00:00', ['CurrentSpeed', 'CurrentDir']])

# 切片操作 左闭右闭 [includeStart, includeEnd]
print("==== df1.loc[切片] ======\n", df1.loc['2022-12-08 20:00:00':'2022-12-10 20:00:00', 'Visibility':'CurrentDir'])

print("*********************** iloc ***********************\n")
# 使用自动生成的整数索引获取数据

print("==== df1.iloc[0] ======\n", df1.iloc[0])

print("==== df1.iloc[0, 1] ======\n", df1.iloc[0, 1])
print("==== df1.iloc[[0, 1]] ======\n", df1.iloc[[0, 1]])

print("==== df1.iloc[0:3, 1:4] ======\n", df1.iloc[0:3, 1:4])
