import numpy as np
from sklearn.metrics import r2_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVR
from tensorflow.python.ops.losses.losses_impl import mean_squared_error

np.random.seed(42)

wind_speeds = np.random.uniform(0,20, (10, 10))
tems = np.random.uniform(-30,40, (10, 10))
hums = np.random.uniform(0,100, (10, 10))

# 计算平均值
mean_wind_speeds = np.mean(wind_speeds)
print(mean_wind_speeds)

# 计算方差
var_wind_speeds = np.var(wind_speeds)
print(var_wind_speeds)

# 计算标准差
std_wind_speeds = np.std(wind_speeds)
print(std_wind_speeds)


from scipy import stats, ndimage
# 对风速数据进行高斯滤波
smoothed_wind_speeds = ndimage.gaussian_filter(wind_speeds, sigma=1)
# print(smoothed_wind_speeds)

import pandas as pd

lats = np.linspace(-90, 90, 10)
lons = np.linspace(-180, 180, 10)

data = []

for i, lat in enumerate(lats):
    for j, lon in enumerate(lons):
        data.append([lat, lon, wind_speeds[i, j], smoothed_wind_speeds[i, j], tems[i, j], hums[i]])

df = pd.DataFrame(data, columns=['lat', 'lon', 'wind_speed', 'smoothed_wind_speed', 'tem', 'humidity'])
print(df)


# scikit-learn
data = {
    'wind_speed': wind_speeds.flatten(),
    'tem': tems.flatten(),
    'humidity': hums.flatten()
}

df = pd.DataFrame(data)

x = df[['wind_speed', 'tem']]
y = df['humidity']

# 将数据分割为训练集和测试集
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=42)
# 标准化特征数据
scaler = StandardScaler()
x_train_scaled = scaler.fit_transform(x_train)
x_test_scaled = scaler.transform(x_test)

# 创建并训练支持向量回归模型
svr = SVR(kernel='rbf')
svr.fit(x_train_scaled, y_train)

# 使用训练好的模型进行预测
y_pred = svr.predict(x_test_scaled)
print("\n 真实值 vs 预测值：")
comparison_df = pd.DataFrame({'Actual': y_test, 'Predicted': y_pred})
print(comparison_df)

mse = mean_squared_error(y_test, y_pred)
r2 = r2_score(y_test, y_pred)
print("MSE: ", mse)
print("决定系数（R^2）", r2)


