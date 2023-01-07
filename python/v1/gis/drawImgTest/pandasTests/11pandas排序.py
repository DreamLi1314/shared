
import pandas as pd
import numpy as np

df1 = pd.DataFrame(np.arange(12).reshape(3, 4),
                   index=['c', 'b', 'a'],
                   columns=list("xwzy"))

print("======== df1 =========\n", df1)

print("======== 按索引排序 =========\n")

# 默认按行索引排序
df2 = df1.sort_index()
print("======== df2 =========\n", df2)

# 按列索引排序
df3 = df1.sort_index(axis=1)
print("======== df3 =========\n", df3)

# 降序
df4 = df1.sort_index(axis=1, ascending=False)
print("======== df4 =========\n", df4)

print("======== 按值排序 =========\n")

df4.loc["b", "y"] = np.nan
df4.loc["c", "y"] = 11.0
df4.loc["c", "x"] = 18.0

print("======== df4 =========\n", df4)

# 根据 y,x 列的数据升序排列, 默认 nan 放在最后
df5 = df4.sort_values(by=['y', 'x'])
print("======== df5 =========\n", df5)

# 根据 y,x 列的数据降序排列, nan 放在最前面
print("======== df5 2 =========\n", df4.sort_values(by=['y', 'x'], ascending=False, na_position='first'))
