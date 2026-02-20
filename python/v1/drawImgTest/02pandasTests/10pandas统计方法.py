
import pandas as pd
import numpy as np

df1 = pd.read_csv('./testData.csv', delimiter=",", index_col=0)

df1[df1 == "-"] = np.nan

print("======== df1 =========\n", df1)

# 均值
print("======== df1['Wind100'].mean() =========\n", df1['Wind100'].mean())

# 转列表
print("======== df1['WindDir'].tolist() =========\n", df1['WindDir'].tolist())
print("======== len(df1['WindDir'].tolist()) =========\n", len(df1['WindDir'].tolist()))

# 转字典 set
print("======== set(df1['WindDir'].tolist()) =========\n", set(df1['WindDir'].tolist()))

# 统计去重后的人数
# 1.
print("======== len(set(df1['WindDir'].tolist())) =========\n", len(set(df1['WindDir'].tolist())))
# 2.
print("======== len(df1['WindDir'].unique()) =========\n", len(df1['WindDir'].unique()))

# 统计 100m 风速最大值所对应的时间
print("======== df1['Wind100'].max() =========\n", df1['Wind100'].max())
print("======== df1['Wind100'].idxmax() =========\n", df1['Wind100'].idxmax())
