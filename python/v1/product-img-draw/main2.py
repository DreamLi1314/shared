"""
@File    : 河南省2018年经济发展图.py
@Time    : 2020/04/20 11:29
@notice  : 参照https://www.cnblogs.com/feffery/p/12381322.html
"""

import geopandas as gpd
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib as mpl

plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签
plt.rcParams['axes.unicode_minus'] = False  # 用来正常显示负号
mpl.rcParams['font.family'] = 'Heiti TC'  # 修改了全局变量
mpl.rcParams['font.size'] = 9  # 字体大小，可以按照喜好设置

# 加载数据-矢量数据的位置
regions = gpd.GeoDataFrame.from_file('./shp/河南省.shp', encoding='utf-8')

# 加载已搜集的excel数据，如果是csv文件用pd.read_csv()方法
data = pd.read_excel('./data/henan_gdp.xlsx')

# 处理数据格式（Excel中的name字段中有空格，消除空格）
data['area_name'] = data['name'].str.replace(' ', '')

# 连接矢量数据属性表与Excel表格
reg = pd.merge(regions, data, left_on='name', right_on='area_name')
# 复制整张表
reg02 = reg.copy()
# # 专题地图制图
reg02['coords'] = reg02['geometry'].apply(lambda x: x.representative_point().coords[0])

reg02.plot(figsize=(8, 6),  # 图像大小
          column='avg_gdp',  # 分级设色字段
          scheme='quantiles',  # MapClassify-分级类型
          legend=True,  # 图例
          legend_kwds={"loc": "lower left"},
          cmap='Reds',  # 渐变色带的名称
          edgecolor='k')  # 边框颜色

# 地图标注
for n, i in enumerate(reg02['coords']):
    plt.text(i[0] - 0.13, i[1], reg02['area_name'][n])  # 标注位置X，Y，标注内容

plt.title('2018年河南省各地级市人均GDP(单位：元)')
plt.grid(True, alpha=0.5)  # 显示网格，透明度为50%
plt.show()