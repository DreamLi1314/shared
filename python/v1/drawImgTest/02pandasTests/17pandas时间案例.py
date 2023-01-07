
import pandas as pd
import numpy as np

df1 = pd.read_csv('./testData.csv')
# print("======== dt1 =========\n", df1)

# 将数据列转化为 Timestamp 并设置为 DataFrame 的索引
df1['index'] = pd.to_datetime(df1['index'])

print("======== type(pd.to_datetime(df1['index'])) =========\n",
      type(pd.to_datetime(df1['index']))) # pandas.core.series.Series

print("======== type(pd.to_datetime(df1['index'])[0]) =========\n",
      type(pd.to_datetime(df1['index'])[0])) # pandas._libs.tslibs.timestamps.Timestamp

df1.set_index('index', inplace=True)

print("======== dt1 =========\n", df1)
print("======== df1.info() =========\n", df1.info())


