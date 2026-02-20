import requests
import os

def download_file(url, destination):
    try:
        response = requests.get(url)
        with open(destination, 'wb') as file:
            file.write(response.content)
        print("success")
    except Exception as e:
        print("failed:", e)

url = "http://10.1.109.140:9000/public-data/metStation/jiangxi.csv"
destination = "C:\\tmp/jiangxi.csv"
download_file(url, destination)

stat = os.stat(destination)
print(stat.st_size)
