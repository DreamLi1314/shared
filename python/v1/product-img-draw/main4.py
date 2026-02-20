import matplotlib.pyplot as plt
import cartopy.crs as ccrs
import cartopy.feature as cfeature
import matplotlib as mpl

plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签
plt.rcParams['axes.unicode_minus'] = False  # 用来正常显示负号
mpl.rcParams['font.family'] = 'Heiti TC'  # 修改了全局变量
mpl.rcParams['font.size'] = 9  # 字体大小，可以按照喜好设置

def draw_map():
    # 创建一个新的figure和一个带有投影的axes
    fig, ax = plt.subplots(figsize=(10, 5), subplot_kw={'projection': ccrs.PlateCarree()})

    # 添加地图底图
    ax.add_feature(cfeature.LAND)
    ax.add_feature(cfeature.OCEAN)
    ax.add_feature(cfeature.COASTLINE)
    ax.add_feature(cfeature.BORDERS, linestyle=':')

    # 添加国家边界（可选）
    ax.add_feature(cfeature.NaturalEarthFeature('physical', 'coastline', '10m',
                                                edgecolor='blue'))

    # 绘制专题图数据，例如人口密度或某种统计数据
    # 假设我们有一些数据点
    lats = [40, 41, 42, 43]  # 纬度
    lons = [-105, -104, -103, -102]  # 经度
    values = [10, 20, 30, 40]  # 示例值，可以是任何你想表示的数据

    sc = ax.scatter(lons, lats, s=20, c=values, cmap='viridis', transform=ccrs.PlateCarree())

    # 添加颜色条
    cb = plt.colorbar(sc, ax=ax, orientation='vertical', label='示例值')

    # 设置地图范围和投影
    ax.set_extent([-180, 180, -90, 90])  # 设置地图的经纬度范围

    # 显示地图
    plt.show()


draw_map()