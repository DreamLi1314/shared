#!/usr/bin/python3

import numpy as np

# 生成指定 shape 且满足均匀分布的随机数 0-1
print(np.random.rand(3,2))
print("==")

# 生成指定 shape 且满足均匀分布的随机数 low - high
print(np.random.uniform(low = 1, high = 100, size = (3,2)))
print("==")

# 生成指定 shape 且满足正态分布的随机数, 平均数为 0， 标准差为 1
print(np.random.randn(3,2))
print("==")

# 生成指定 shape 且满足正态分布的随机数, 平均数为 loc， 标准差为 scale
print(np.random.normal(loc=50, scale = 5, size=(3,2)))
print("==")

# 指定上下限范围选取随机整整
print(np.random.randint(1, 100, (3,2)))


