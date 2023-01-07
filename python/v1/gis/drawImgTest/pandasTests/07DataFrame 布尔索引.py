#!/usr/bin/python3

import numpy as np
import pandas as pd
import string

# loc 使用自定义索引查询, iloc 使用自动生成的整数索引

df1 = pd.read_csv("../testData.csv", index_col=0)

print("==== df1 ======\n", df1)

# 访问单行数据

# 统计流速大于 1.5 的数据
print("==== df1[df1['Wind100'] > 10] ======\n", df1[df1['CurrentSpeed'] > 1.5])

# 统计流速大于 1.5 且流向大于 230 的数据
print("==== df1[(df1['CurrentSpeed'] > 1.5) & df1['CurrentDir'] > 230] ======\n",
      df1[(df1['CurrentSpeed'] > 1.5) & (df1['CurrentDir'] > 230)])
