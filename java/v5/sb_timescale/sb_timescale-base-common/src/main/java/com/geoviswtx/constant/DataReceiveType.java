package com.geoviswtx.constant;

/**
 * @author zz
 * @date 2024/1/9 16:28
 */
public enum DataReceiveType {
    HTTP("HTTP传输方式"),
    FTP("FTP传输方式"),
    SL("专线传输方式"),
    SHARE("共享文件夹传输方式");

    private String des;

    DataReceiveType(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }
}
