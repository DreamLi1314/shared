from hjnwtx.hjnLAPSTransform import LAPSTransfrom
from pyproj import Proj
import numpy as np
import netCDF4 as nc
import matplotlib.pyplot as plt

if __name__ == '__main__':
    path = "./Z_NAFP_C_BABJ_20251118140624_P_3DWind_RT_BEXAKMC_5m_5MIN-MUL-202511181400.nc"
    with nc.Dataset(path) as dataNC:
        print(dataNC)
        VWIND = dataNC["VWIND"][:].squeeze()*0.01
        UWIND = dataNC["UWIND"][:].squeeze()*0.01
        WWIND = dataNC["WWIND"][:].squeeze()*0.01
        xArr = dataNC["x"][:]
        yArr = dataNC["y"][:]
        reso = 5
        print(VWIND.shape)
    proj4_string = "+proj=utm +zone=49 +datum=WGS84 +units=m +no_defs"
    p = Proj(proj4_string)
    xMat ,yMat = np.meshgrid(xArr ,yArr)
    lonMat ,latMat = p(xMat ,yMat ,inverse=True)
    lapsT = LAPSTransfrom(p ,lonMat ,latMat ,step=reso /100000)
    latArr = lapsT.latArr
    lonArr = lapsT.lonArr
    for i in range(len(VWIND)):
        dataLL = lapsT.LambertToLatLon(VWIND[i])
        plt.pcolormesh(lonArr,latArr,dataLL)
        plt.show()