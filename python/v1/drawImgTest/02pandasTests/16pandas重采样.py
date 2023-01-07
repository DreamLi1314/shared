
import pandas as pd
import numpy as np

df1 = pd.DataFrame(np.random.randint(0, 100, (100, 1)),
                   index=pd.date_range(start='2022-01-01', periods=100))
print("======== dt1 =========\n", df1)

# 降采样

# 统计每月的平均值
print("======== df1.resample('M').mean() =========\n", df1.resample('M').mean())

# 每 10 天统计求和
print("======== df1.resample('10D').sum() =========\n", df1.resample('10D').sum())