#!/usr/bin/python3

import numpy as np


def fillNanArray(arr):
    # 按列进行遍历
    for i in range(arr.shape[1]):
        currentCols = arr[:,i]

        nanCount = np.count_nonzero(currentCols != currentCols)

        if nanCount != 0: # 当前列存在 nan
            noneNanCols = currentCols[currentCols == currentCols]
            avg = noneNanCols.mean()
            currentCols[np.isnan(currentCols)] = avg

arr1 = np.arange(12, dtype=float).reshape(3,4)

print("==== arr1 ======\n", arr1)

arr1[1,2:] = np.nan

print("==== arr1 ======\n", arr1)

fillNanArray(arr1)

print("==== fillNanArray arr1 ======\n", arr1)