# -*- coding: utf-8 -*-

"""
@File    : 经济发展组合图.py
@Author  : fungis@163.com
@Time    : 2020/04/20 11:29
@notice  :
"""
'''
地图颜色（https://matplotlib.org/tutorials/colors/colormaps.html）
cmaps['Sequential'] = [
            'Greys', 'Purples', 'Blues', 'Greens', 'Oranges', 'Reds',
            'YlOrBr', 'YlOrRd', 'OrRd', 'PuRd', 'RdPu', 'BuPu',
            'GnBu', 'PuBu', 'YlGnBu', 'PuBuGn', 'BuGn', 'YlGn']
'''

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
# 处理数据格式
data['area_name'] = data['name'].str.replace(' ', '')
# 计算整合数据：人均产值= 产业产值/区域人均
data['data_one'] = data['第一产业']
data['data_two'] = data['第二产业']
data['data_three'] = data['第三产业']
#表格链接-连接矢量数据属性表与Excel表格
reg = pd.merge(regions, data, left_on='name', right_on='area_name')
reg03 = reg.copy()
# 列表 第一个是分级设色的字段，第二个是图名
data_plot = [('avg_gdp', '2018年河南省各市人均GDP(单位：元)'),
            ('data_one', '2018年河南省各市第一产业产值(单位：元)'),
            ('data_two', '2018年河南省各市第二产业产值(单位：元)'),
            ('data_three', '2018年河南省各市第三产业产值(单位：元)')]

#图纸大小设置
plt.figure(figsize=(16, 14))
for m, cal in enumerate(data_plot):

    reg03['coords'] = reg03['geometry'].apply(lambda x: x.representative_point().coords[0])

    reg03.plot(ax=plt.subplot(2, 2, m + 1),
              column=cal[0],  # 分级设色字段
              scheme='Quantiles',  # ['Equal_interval'|'Quantiles'|'Fisher_Jenks']
              legend=True,#是否显示图例
              legend_kwds={"loc": "lower left"},#图例的位置
              cmap='Pastel1',  # 色带的选择
              edgecolor='k'
              )

    for n, i in enumerate(reg03['coords']):
        plt.text(i[0] - 0.13, i[1], reg03['area_name'][n])

    plt.title(cal[1])
    plt.grid(True, alpha=0.5)

plt.show()
# plt.savefig('./images/河南省2018年经济组合拼接图__Pastel1_Quantiles.png', dpi=300)