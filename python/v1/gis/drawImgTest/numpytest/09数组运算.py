#!/usr/bin/python3

import numpy as np

arr1 = np.arange(9).reshape(3,3)

print("==== arr1 ======\n", arr1)


arr2 = np.arange(9, 18).reshape(3,3)

print("==== arr2 ======\n", arr2)

print("==== arr1 + arr2 ======\n", arr1 + arr2)

print("==== arr1 * arr2 ======\n", arr1 * arr2)


arr3 = np.arange(24).reshape(4,6)
print("==== arr3 ======\n", arr3)

arr4 = np.array([0,1,2,3,4,5])
print("==== arr4 ======\n", arr4)

print("==== arr3 + arr4 ======\n", arr3 + arr4)

print("==== arr3 * arr4 ======\n", arr3 * arr4)


arr5 = np.arange(4).reshape(4,1)
print("==== arr5 ======\n", arr5)

print("==== arr3 + arr5 ======\n", arr3 + arr5)

print("==== arr3 * arr5 ======\n", arr3 * arr5)