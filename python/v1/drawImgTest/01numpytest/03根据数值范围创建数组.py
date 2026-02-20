#!/usr/bin/python3

import numpy as np


a1 = np.arange(10)
print(a1)
print(type(a1))

a2 = [i for i in range(10)]
print(a2)
print(type(a2))


a3 = np.arange(1, 30, 5)
print(a3)
print(type(a3))

a4 = np.array(range(1, 30, 5))
print(a4)
print(type(a4))