
import pandas as pd
import numpy as np

df1 = pd.DataFrame(np.ones((2, 3)),
                   index=['A', 'B'],
                   columns=list("abc"))

print("======== df1 =========\n", df1)

df2 = pd.DataFrame(np.zeros((3, 3)),
                   index=['A', 'B', 'C'],
                   columns=list("xyz"))

print("======== df2 =========\n", df2)


print("======== join: 行索引相同合并(左右拼接) =========\n")

print("======== df1.join(df2) =========\n", df1.join(df2))
print("======== df2.join(df1) =========\n", df2.join(df1))


print("======== merge =========\n")

df3 = pd.DataFrame(np.ones((2, 3)),
                   index=['A', 'B'],
                   columns=list("abc"))
print("======== df3 =========\n", df3)

df4 = pd.DataFrame(np.arange(9).reshape((3, 3)), index=list("hkp"), columns=list("fax"))

df4.loc['k', 'a'] = 1

print("======== df4 =========\n", df4)

# df3 里面有两个 1 的数据与 df4 匹配到,  所以组合起来有两条数据
# 遍历 df3的每一行的 'a' 列, 如果 df4 中有那些行的数据与 df3 数据相等, 则一对多拼接数据, 继续遍历下一行
print("======== df3.merge(df4, on='a') =========\n", df3.merge(df4, on='a'))


# 内连接 inner 取交集(默认)
# 外连接 outer 取并集
df3.loc['A', 'a'] = 100
print("======== df3 =========\n", df3)

print("======== df3.merge(df4, on='a', how='outer') =========\n", df3.merge(df4, on='a', how='outer'))

# 左连接, 以 df3 为参考
print("======== df3.merge(df4, on='a', how='left') =========\n", df3.merge(df4, on='a', how='left'))

# 右连接, 以 df4 为参考
print("======== df3.merge(df4, on='a', how='right') =========\n", df3.merge(df4, on='a', how='right'))


print("======== 按指定列拼接 =========\n")

df5 = pd.DataFrame(np.zeros((3, 4)), index = list("ABC"), columns = list("MONP"))
df6 = pd.DataFrame(np.zeros((2, 5)), index = list("AB"), columns = list("VWXYZ"))

df5.loc[:, "O"] = list('abc')
df6.loc[:, "X"] = list('cd')

print("======== df5 =========\n", df5)
print("======== df6 =========\n", df6)

print("======== df5.merge(df6, left_on='O', right_on='X') =========\n", df5.merge(df6, left_on='O', right_on='X'))
print("======== df5.merge(df6, left_on='O', right_on='X', how='outer') =========\n", df5.merge(df6, left_on='O', right_on='X', how='outer'))
print("======== df5.merge(df6, left_on='O', right_on='X', how='left') =========\n", df5.merge(df6, left_on='O', right_on='X', how='left'))
print("======== df5.merge(df6, left_on='O', right_on='X', how='right') =========\n", df5.merge(df6, left_on='O', right_on='X', how='right'))
