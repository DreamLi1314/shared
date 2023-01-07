package com.mlog.yiji.global.service;

import com.mlog.yiji.global.vo.GeoVo;

public interface CacheService {

    //存
    void setCommonCache(String key,GeoVo value);
    //取
    GeoVo getCommonCache(String key);

}
