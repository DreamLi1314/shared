package com.geoviswtx.constant;

/**
 * @author zz
 * @date 2024/1/9 16:28
 */
public enum FileType {
    NC("NC文件格式"),
    EXCEL("EXCEL文件格式"),
    XML("xml文件格式"),
    GRIB("GRIB文件格式"),
    OTHER("未知文件格式");

    private String des;

    FileType(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }
}
