#!/usr/bin/python3

import numpy as np

arr1 = np.arange(12).reshape(3,4)

print("==== arr1 ======\n", arr1)


# sum
print("==== np.sum(arr1) ======\n", np.sum(arr1))
print("==== arr1.sum() ======\n", arr1.sum())
print("==== np.sum(arr1, axis=0) ======\n", np.sum(arr1, axis=0))
print("==== arr1.sum(axis=1) ======\n", arr1.sum(axis=1))

# mean 均值
print("==== arr1.mean(axis=1) ======\n", arr1.mean(axis=1))

# 中值
print("==== np.median(arr1, axis=1) ======\n", np.median(arr1, axis=1))

# 最值
print("==== arr1.max() ======\n", arr1.max())
print("==== arr1.min() ======\n", arr1.min())

# 极值(max - min)
print("==== np.ptp(arr1, axis=1) ======\n", np.ptp(arr1, axis=1))

# 标准差
print("==== arr1.std(axis=1) ======\n", arr1.std(axis=1))
