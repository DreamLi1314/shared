#!/usr/bin/python3

import numpy as np

list1 = [1,2,3]
list2 = [4,5,6]
list3 = [7,8,9]
list4 = [10,11,12]

dim3Arr1= np.array([[list1, list2], [list3, list4]])

print(dim3Arr1)

print(dim3Arr1.shape) # (2, 2, 3)

# 修改数组 shape， 需要确保数组 size 不变
dim3Arr2 = dim3Arr1.reshape((3,2,2))

print(dim3Arr2)

print(dim3Arr2.shape) # (3, 2, 2)

print(dim3Arr2.dtype)

dim1Arr4 = np.arange(12)
print(dim1Arr4.shape) # (12,)

print(dim3Arr1.reshape(12,))
print(dim3Arr1.reshape(12,1))
print(dim3Arr1.reshape(1,12))

# 将高维数组转化为一维
print(dim3Arr1.flatten())


# 修改数组类型
dim3Arr3 = dim3Arr2.astype("int8")

print(dim3Arr2.dtype)
print(dim3Arr3.dtype)
