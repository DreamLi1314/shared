#!/usr/bin/python3

import numpy as np
import pandas as pd
import string

# 数组创建 DataFrame

arr1 = np.array([['a', 1, 'c'], ['d',2,'f']])

df1 = pd.DataFrame(arr1, index=["r1", "r2"], columns=["c1", "c2", "c3"])

print("==== df1 ======\n", df1)


print("==== df1.shape ======\n", df1.shape)

print("==== df1.dtypes ======\n", df1.dtypes)

print("==== df1.ndim ======\n", df1.ndim) # 2

# 行索引
print("==== df1.index ======\n", df1.index) # Index(['r1', 'r2'], dtype='object')

# 列索引
print("==== df1.columns ======\n", df1.columns) # Index(['c1', 'c2', 'c3'], dtype='object'

# 数据
print("==== df1.values ======\n", df1.values)

print("==== type(df1.values) ======\n", type(df1.values))

# 打印前 n 行数据, 默认为 5
print("==== df1.head() ======\n", df1.head(1))

# 打印后 n 行数据, 默认为 5
print("==== df1.tail() ======\n", df1.tail(1))

# 显示内存存储信息
print("==== df1.info() ======\n", df1.info())

# 显示数值统计信息
print("==== df1.describe() ======\n", df1.describe())
