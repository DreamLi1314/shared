import folium

# 示例地面自动站数据 (latitude, longitude, name)
stations = [
    {"lat": 39.9042, "lon": 116.4074, "name": "北京站"},
    {"lat": 31.2304, "lon": 121.4737, "name": "上海站"},
    {"lat": 22.3193, "lon": 114.1694, "name": "广州站"},
    {"lat": 45.7797, "lon": 126.7320, "name": "哈尔滨站"},
    # 添加更多站如需要
]

def draw_stations_map():
    # 创建地图，中心在北京附近
    m = folium.Map(location=[35.0, 110.0], zoom_start=5)

    # 添加每个站的标记
    for station in stations:
        folium.Marker(
            location=[station["lat"], station["lon"]],
            popup=station["name"],
            tooltip=station["name"]
        ).add_to(m)

    # 保存地图为HTML文件
    m.save("stations_map.html")
    print("地图已保存为 stations_map.html")

    # 如果想在浏览器中打开（可选）
    # import webbrowser
    # webbrowser.open("stations_map.html")

if __name__ == "__main__":
    draw_stations_map()
