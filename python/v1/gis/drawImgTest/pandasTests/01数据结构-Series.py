#!/usr/bin/python3

import numpy as np
import pandas as pd
import string

# 列表创建 Series
s1 = pd.Series([5, 2, 3, 4])

print("==== s1 ======\n", s1)
print("==== type(s1) ======\n", type(s1))

# 字典创建 Series
dict = {
    "name": "JackLi",
    "age":  18,
    "tel": "18829346487"
}

print("==== pd.Series(dict) ======\n", pd.Series(dict))

dict = {
    string.ascii_uppercase[i]:i for i in range(10)
}

s2 = pd.Series(dict)

print("==== s2 ======\n", s2)
print("==== type(s2) ======\n", type(s2))


