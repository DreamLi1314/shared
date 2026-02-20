
import pandas as pd
import numpy as np

years = [2021, 2022, 2023, 2024]
months = np.arange(12)
days = np.arange(31)
hours = np.arange(24)
size = 15

df1 = pd.DataFrame({
      "year": [years[i] for i in np.random.randint(0, len(years), size)],
      "month": [months[j] for j in np.random.randint(0, len(months), size)],
      "day": [days[k] for k in np.random.randint(0, len(days), size)],
      "hour": [hours[l] for l in np.random.randint(0, len(hours), size)],
      "data": [m for m in  np.random.randint(0, 100, size)]
})

print("======== dt1 =========\n", df1)

# 组装 PeriodIndex
period = pd.PeriodIndex(year=df1['year'], month = df1['month'],
               day = df1['day'], hour = df1['hour'],
               freq='H')

print("======== period =========\n", period)

# 添加到 DataFrame
df1['myKey'] = period

# 设置为索引

df1.set_index('myKey', inplace=True)

print("======== dt1 =========\n", df1)
