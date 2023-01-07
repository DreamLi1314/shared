#!/usr/bin/python3

import numpy as np

arr1 = np.array((1,2,3))

print(arr1)
print(type(arr1))

arr2 = np.array([1,2,3])

print(arr2)
print(type(arr2))

arr3 = np.array([[1,2,3], [4,5,6]])

print(arr3)
print(type(arr3))
print(arr3.ndim) # 2 维
print(arr3.shape) # (2, 3)
print(arr3.size) # 6 个元素
print(arr3.dtype) # int64

# 三维

list1 = [1,2,3]
list2 = [4,5,6]
list3 = [7,8,9]
list4 = [10,11,12]

dim3Array4 = np.array([[list1,list2], [list3, list4]])

print(dim3Array4)

print(type(dim3Array4))
print(dim3Array4.ndim) # 3 维
print(dim3Array4.shape) # (2, 2, 3)
print(dim3Array4.size) # 12 个元素
print(dim3Array4.dtype) # int64
