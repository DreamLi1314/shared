#!/usr/bin/python3

import numpy as np
import pandas as pd
import string

# 数组创建 DataFrame

df1 = pd.read_csv("../testData.csv")

print("==== df1 ======\n", df1)

df2 = pd.read_csv("../testData.csv", header=None)

print("==== df2 ======\n", df2)

df3 = pd.read_csv("../testData.csv", index_col=0)

print("==== df3 ======\n", df3)

print("==== df3.info() ======\n", df3.info())
