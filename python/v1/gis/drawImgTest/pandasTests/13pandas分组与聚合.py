
import pandas as pd
import numpy as np

company = list("ABC")
nickList = list("WXYZ")
dataSize = 10

df1 = pd.DataFrame({
    "commany": [company[x] for x in np.random.randint(0, len(company), dataSize)],
    "nick": [nickList[x] for x in np.random.randint(0, len(nickList), dataSize)],
    "age": np.random.randint(18, 50, dataSize),
    "salary": np.random.randint(5, 50, dataSize)
})

print("======== df1 =========\n", df1)

# 分组

# 根据公司名分组
companyGroup = df1.groupby(by='commany')

print("======== companyGroup =========\n", companyGroup) # DataFrameGroupBy
# [(commany, DataFrame)]
print("======== list(companyGroup) =========\n", list(companyGroup))

# 遍历 companyGroup

for i,j in companyGroup:
    print("======== company =========\n", i)
    print("======== group data =========\n", j)
    print("*********************\n")

# 聚合

# 求每个分组的平均值
print("======== companyGroup.mean() =========\n", companyGroup.mean())

# 求每个分组的最大值
print("======== companyGroup.max() =========\n", companyGroup.max())
print("======== companyGroup.agg('max') =========\n", companyGroup.agg('max'))

# 不同的列求不同的聚合值,可以使用字典的方式
print("======== 不同的列求不同的聚合值 =========\n", companyGroup.agg({
    'salary': 'median',
    'age': 'mean'
}))

print("======== 测试 =========\n")

print("======== list(companyGroup) =========\n", list(companyGroup))

print("======== companyGroup.count() =========\n", companyGroup.count())

print("======== xxx =========\n")
print(list(companyGroup["age"]))

print("======== xxx count =========\n")
print(companyGroup["age"].count())

print("======== xxx count A=========\n")
print(companyGroup["age"].count()['A'])


# 按多个条件分组, 返回 Series --- 复合索引
grouped = df1.groupby(by=['commany', 'nick']).count()['age']
print("======== grouped =========\n", grouped)
print("======== type(grouped) =========\n", type(grouped))
print("======== grouped['A'] =========\n", grouped['A'])

# 按多个条件分组, 返回 DataFrame

grouped1 = df1.groupby(by=['commany', 'nick']).count()[['age']]
print("======== grouped1 =========\n", grouped1)
print("======== type(grouped1) =========\n", type(grouped1))
print("======== grouped1['A'] =========\n", grouped1.loc['A', 'age'])

grouped2 = df1[['age']].groupby(by=[df1['commany'], df1['nick']]).count()
print("======== grouped2 =========\n", grouped2)
print("======== type(grouped2) =========\n", type(grouped2))
print("======== grouped2['A'] =========\n", grouped2.loc['A', 'age'])


