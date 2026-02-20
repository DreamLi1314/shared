
import pandas as pd
import numpy as np

dt1 = pd.date_range(start='20220301', end='20220531', freq='D')
print("======== dt1 =========\n", dt1)
print("======== type(dt1) =========\n", type(dt1)) # pandas.core.indexes.datetimes.DatetimeIndex

dt2 = pd.date_range(start='20220301', end='20220531', freq='10D')
print("======== dt2 =========\n", dt2)

# 从 3-01 开始取 5 个数据, 每个数据间隔 10 天
dt3 = pd.date_range(start='20220301', periods=5, freq='10D')
print("======== dt3 =========\n", dt3)

dt4 = pd.date_range(start='2022-12-30', periods=10, freq='M')
print("======== dt4 =========\n", dt4)

dt5 = pd.date_range(start='2022/12/30', periods=10, freq='H')
print("======== dt5 =========\n", dt5)

# 包左不包右
dt6 = pd.date_range(start='2022/12/30', end='2022/12/31', freq='H', closed='left')
print("======== dt6 =========\n", dt6)


