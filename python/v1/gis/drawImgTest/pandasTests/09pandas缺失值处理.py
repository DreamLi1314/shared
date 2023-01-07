
import pandas as pd
import numpy as np

df1 = pd.DataFrame(np.arange(12).reshape(3, 4), index=list("abc"), columns=list("wxyz"))

print("======== df1 =========\n", df1)

df1.loc["b":, :"x"] = np.nan

print("======== df1 =========\n", df1)

print("======== pd.notnull(df1['w']) =========\n", pd.notnull(df1['w']))

# 打印 w 列不为 nan 的所有行数据
print("======== df1[pd.notnull(df1['w'])] =========\n", df1[pd.notnull(df1['w'])])

print("======== ****************** =========\n")

# 删除行中包含 nan 的行
df2 = df1.dropna(axis=0, how="any")
print("======== df2 =========\n", df2)

# 删除行中都是 nan 的行
df3 = df1.dropna(axis=0, how="all")
print("======== df3 =========\n", df3)

# 删除行中包含 nan 的行, 修改原数据
# df4 = df1.dropna(axis=0, how="any", inplace=True)
# print("======== df4 =========\n", df4) # None
# print("======== df1 =========\n", df1) # 被修改了

print("======== ****************** =========\n")

# nan 填充为 0
df5 = df1.fillna(0)
print("======== df5 =========\n", df5)

# nan 填充为 0
df6 = df1.fillna(0)
print("======== df5 =========\n", df6)

# nan 填充为 z 列的平均值
df7 = df1.fillna(df1['z'].mean())
print("======== df7 =========\n", df7)
