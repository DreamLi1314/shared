#!/usr/bin/python3

import numpy as np

arr = np.zeros((8, 8), dtype=int)

print("==== arr 0 ======\n", arr)

arr[1::2, ::2] = 1

print("==== arr 1 ======\n", arr)

arr[::2, 1::2] = 1

print("==== arr 2 ======\n", arr)
