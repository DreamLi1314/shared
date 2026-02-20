import numpy as np
from keras.src.losses import mean_squared_error
from keras.src.utils.jax_layer_test import input_shape
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVR
import tensorflow as tf
import pandas as pd

np.random.seed(42)
tf.random.set_seed(42)

wind_speeds = np.random.uniform(0,20, (10, 10))
tems = np.random.uniform(-10,40, (10, 10))
hums = np.random.uniform(0,100, (10, 10))

data = {
    'wind_speed': wind_speeds.flatten(),
    'tem': tems.flatten(),
    'humidity': hums.flatten()
}

df = pd.DataFrame(data)

# scikit-learn
x = df[['wind_speed', 'tem']]
y = df[['humidity']]

# 将数据分割为训练集和测试集
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=42)

# 标准化特征数据
scaler = StandardScaler()
x_train_scaled = scaler.fit_transform(x_train)
x_test_scaled = scaler.transform(x_test)

# 创建并训练支持向量回归模型
# svr = SVR(kernel='rbf')
# svr.fit(x_train_scaled, y_train)

# 创建 Tensorflow 模型
model = tf.keras.Sequential([
    tf.keras.layers.Dense(64, activation='relu',input_shape=[x_train_scaled.shape[1]]),
    tf.keras.layers.Dense(64, activation='relu'),
    tf.keras.layers.Dense(1)
])

# 编译模型
model.compile(optimizer='adam', loss= 'mean_squared_error')

# 训练模型
history = model.fit(x_train_scaled, y_train, epochs=100, validation_split=0.2, verbose=0)

# 使用训练好的模型进行预测
y_pred = model.predict(x_test_scaled).flatten()

print("\n 真实值 vs 预测值：")
comparison_df = pd.DataFrame({'Actual': y_test.to_numpy().reshape(-1), 'Predicted': y_pred})
print(comparison_df)

# 模型评估
mse_tool = tf.keras.losses.MeanSquaredError()
mse = mse_tool(y_test, y_pred).numpy()
rmse_tool = tf.keras.metrics.RootMeanSquaredError()
rmse_tool.update_state(y_test, y_pred)
rmse = rmse_tool.result().numpy()
# 计算R²（决定系数）
r2_tool = tf.keras.metrics.R2Score()
r2_tool.update_state(y_test, y_pred)
r2 = r2_tool.result().numpy()

print("MSE: ", mse)
print("rmse: ", rmse)
print(f"R²（决定系数）：{r2}")



