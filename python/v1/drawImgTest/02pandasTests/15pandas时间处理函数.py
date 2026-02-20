
import pandas as pd
import numpy as np

dt1 = pd.to_datetime('2022-05-22', format='%Y-%m-%d')
print("======== dt1 =========\n", dt1)
print("======== type(dt1) =========\n", type(dt1)) # pandas._libs.tslibs.timestamps.Timestamp

# 2022-05-22 16:49:12
dt2 = pd.to_datetime('05/22/2022 16:49:12', format='%m/%d/%Y %H:%M:%S')
print("======== dt2dt2 =========\n", dt2)


