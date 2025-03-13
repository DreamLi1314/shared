# -*- coding: utf-8 -*-

"""
@File    : provinceMapMake.py
@Time    : 2020/04/20 16:49
@notice  : 地图颜色（https://matplotlib.org/tutorials/colors/colormaps.html）
"""

import geopandas as gpd
import matplotlib.pyplot as plt
import matplotlib as mpl
import contextily

plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签
plt.rcParams['axes.unicode_minus'] = False  # 用来正常显示负号
mpl.rcParams['font.family'] = 'Heiti TC'  # 修改了全局变量
mpl.rcParams['font.size'] = 12  # 字体大小，可以按照喜好设置

# 输入图名
Map_name = '河南省行政区划'
# 加载数据-矢量数据的位置
# regions = gpd.GeoDataFrame.from_file('./shp/河南省.shp', encoding='utf-8')
# 加载数据-读取矢量数据的属性表
data = gpd.read_file('./shp/河南省.dbf', encoding='utf-8')
# 复制一份该表的数据
reg02 = data.copy()
# # 专题地图制图
reg02['coords'] = reg02['geometry'].apply(lambda x: x.representative_point().coords[0])

reg02.plot(figsize=(8, 6),  # 图像大小
          column='name',  # 分级设色字段
          # scheme='quantiles',  # MapClassify-分级类型
          legend=False,  # 图例
          cmap='Pastel1_r',  # 渐变色带的名称#Set2
          edgecolor='k')  # 边框颜色

# 地图标注
for n, i in enumerate(reg02['coords']):
    plt.text(i[0] - 0.2, i[1], reg02['name'][n], fontsize=8, horizontalalignment="left")  # 标注位置X，Y，标注内容

# 添加注记底图
base_map_labels_service_url = 'http://t0.tianditu.gov.cn/DataServer?T=vec_w&X={x}&Y={y}&L={z}&tk=b281531d9217518d07a7e809a610f7ae'
# contextily.add_basemap(plt, source=base_map_labels_service_url)
# contextily.add_basemap(plt, crs=reg02.crs.to_string(), source=contextily.providers.OpenStreetMap.Mapnik)

plt.title('Python-{}图'.format(Map_name), fontsize=18, fontweight='bold')
plt.grid(True, alpha=0.5)  # 显示网格，透明度为50%
plt.show()
# plt.savefig('./images/{}.png'.format(Map_name), dpi=300)