
import pandas as pd
import numpy as np

s1 = pd.Series(["A", "b", "C", "bbhello", "1 23", np.nan, "  hj  "])

print("======== s1 =========\n", s1)

print("======== s1.str =========\n", s1.str)

print("======== s1.str.count('b') =========\n", s1.str.count('b'))

print("======== s1.str.contains('b') =========\n", s1.str.contains('b'))

print("======== s1.str.strip() =========\n", s1.str.strip())

print("======== s1.str.lstrip() =========\n", s1.str.lstrip())

df1 = pd.DataFrame({
    "key1": list("abcdef"),
    "key2": [ "hee", "fv", "w", 123, "123", np.nan ]
})

print("======== df1 =========\n", df1)

print("======== df1.columns.str.upper() =========\n", df1.columns.str.upper())

df1.columns = df1.columns.str.upper()

print("======== df1 2 =========\n", df1)

print("======== df1['KEY2'].str.upper() =========\n", df1['KEY2'].str.upper())