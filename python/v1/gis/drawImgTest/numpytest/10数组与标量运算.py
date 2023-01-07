#!/usr/bin/python3

import numpy as np

arr1 = np.arange(9).reshape(3,3)

print("==== arr1 ======\n", arr1)

print("==== arr1+2 ======\n", arr1 + 2)

print("==== arr1/0 ======\n", arr1 / 0)

# nan 不是一个数字， inf 正无穷大， -inf 负无穷大
print("==== np.nan ======\n", np.nan)

print("==== np.inf ======\n", np.inf)

print("==== np.nan == np.nan ======\n", np.nan == np.nan) # false
print("==== np.inf == np.inf ======\n", np.inf == np.inf) # true


arr2 = np.arange(9, dtype=float).reshape(3,3)

arr2[:, 2] = np.nan
arr2[:, 1] = 0

print("==== arr2 ======\n", arr2)

# 判断数组中 nan 的个数
print("==== arr2 != arr2 ======\n", arr2 != arr2)
print("==== np.isnan(arr2) ======\n", np.isnan(arr2))
# 1
print("==== arr2[arr2 != arr2] ======\n", arr2[arr2 != arr2])
print("==== len(arr2[arr2 != arr2]) ======\n", len(arr2[arr2 != arr2]))
# 2
print("==== np.count_nonzero(arr2 != arr2) ======\n", np.count_nonzero(arr2 != arr2))
print("==== np.count_nonzero(np.isnan(arr2)) ======\n", np.count_nonzero(np.isnan(arr2)))

# nan 与任何值的和均为 nan

print("==== np.sum(arr1) ======\n", np.sum(arr1))
print("==== np.sum(arr2) ======\n", np.sum(arr2))

print("==== np.sum(arr1, axis=1) ======\n", np.sum(arr1, axis=1))
