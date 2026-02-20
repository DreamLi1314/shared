#!/usr/bin/python3

import numpy as np

data = np.loadtxt("../testData.csv", dtype=str, delimiter=",", skiprows = 1)

print("==== data ======\n", data)

print("==== type(data) ======\n", type(data))

data2 = np.loadtxt("../testData.csv", dtype=str, delimiter=",", skiprows = 1, unpack=True)

print("==== unpack data2 ======\n", data2)