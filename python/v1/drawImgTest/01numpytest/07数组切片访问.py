#!/usr/bin/python3

import numpy as np

arr1 = np.arange(9)

print("====== arr1 : \n", arr1)

# [includeStart:excludeEnd:Step]

print("====== arr1 : \n", arr1[1:3])

print("====== arr1 : \n", arr1[:3])

print("====== arr1 : \n", arr1[:-1])

print("====== arr1 : \n", arr1[:])

# 指定范围指定步长提取
print("====== arr1 : \n", arr1[::3])


arr2 = arr1.reshape((3,3))

print("====== arr2 : \n", arr2)

print("====== arr2[:2] : \n", arr2[:2])

print("====== arr2[:2] : \n", arr2[:2, :2])

print("====== arr2[:2] : \n", arr2[:2, 1])


