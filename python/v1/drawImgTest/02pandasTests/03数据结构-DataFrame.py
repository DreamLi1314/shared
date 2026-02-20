#!/usr/bin/python3

import numpy as np
import pandas as pd
import string

# 数组创建 DataFrame

arr1 = np.array([['a', 'b', 'c'], ['d','e','f']])

df1 = pd.DataFrame(arr1)

print("==== df1 ======\n", df1)
print("==== type(df1) ======\n", type(df1)) # pandas.core.frame.DataFrame


df2 = pd.DataFrame(arr1, index=["r1", "r2"], columns=["c1", "c2", "c3"])

print("==== df2 ======\n", df2)


