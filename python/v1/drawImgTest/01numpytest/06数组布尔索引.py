#!/usr/bin/python3

import numpy as np

arr1 = np.arange(1, 10).reshape(3,3)

print(arr1)

print(arr1 > 5)

# 获取小于 5 的所有数
print(arr1[arr1 > 5]) #[6 7 8 9]

# 小于 5 的数全部改成 0
arr1[arr1 > 5] = 0
print(arr1)

print("==============")

# 小于 10 的数改为 0， 所有大于 10 的数改为 1
arr2 = np.arange(24).reshape(4, 6)

arr3 = np.where(arr2 < 10, 0, 1)

print("====arr2==", arr2) # 不变
print("===arr3===", arr3)

#  clip 小于 min 的数全部改为 min， 大于 max 的数全部改为 max
arr4 = arr2.clip(min = 10, max = 18)
print("===arr4===", arr4)
