#!/usr/bin/python3

import numpy as np

arr1 = np.array([[3,1,8], [2,1,9], [9,0,10]])

print("==== arr1 ======\n", arr1)

# 排序
arr1.sort()

print("==== sort arr1 ======\n", arr1)

arr1.sort(axis=0)

print("==== sort 0 axis arr1 ======\n", arr1)

# 条件判断

print("==== all ======\n", np.all(arr1 > 5))
print("==== any ======\n", np.any(arr1 > 9))

# 去重

arr2 = np.array([1,3,1,4,1,5])

print("==== arr2 ======\n", arr2)

print("==== unique arr2 ======\n", np.unique(arr2))

# 拼接

arr3 = np.arange(12).reshape(2, 6)
arr4 = np.arange(12, 24).reshape(2, 6)

arr5 = np.vstack((arr3, arr4))
print("==== arr5 vstack ======\n", arr5)

arr6 = np.hstack((arr3, arr4))
print("==== arr6 hstack ======\n", arr6)

# 转置
print("==== arr6.T ======\n", arr6.T)

arr7 = arr6.reshape((2,3,4))

print("==== arr7.swapaxes ======\n", arr7.swapaxes(2, 0))
print("==== arr7.transpose ======\n", arr7.transpose(1, 2, 0))


