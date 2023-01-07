#!/usr/bin/python3

import numpy as np
import pandas as pd
import string


dict = {
    string.ascii_uppercase[i]:i for i in range(10)
}

s2 = pd.Series(dict)

print("==== s2 ======\n", s2)
print("==== type(s2) ======\n", type(s2))

# 切片访问
print("==== s2[:5] ======\n", s2[:5])
print("==== s2[:5:2] ======\n", s2[:5:2])

# 索引访问

print("==== s2['C'] ======\n", s2['C'])

print("==== s2[['A', 'C', 'F']] ======\n", s2[['A', 'C', 'F']])

# 布尔索引

print("==== s2 > 5 ======\n", s2 > 5)

print("==== s2[s2 > 5] ======\n", s2[s2 > 5])

# 索引

print("==== s2.index ======\n", s2.index)
print("==== len(s2.index) ======\n", len(s2.index))
print("==== type(s2.index) ======\n", type(s2.index)) # pandas.core.indexes.base.Index 可迭代类型


# 数据
print("==== s2.values ======\n", s2.values)
print("==== len(s2.values) ======\n", len(s2.values))
print("==== type(s2.values) ======\n", type(s2.values)) # numpy.ndarray 可迭代类型


