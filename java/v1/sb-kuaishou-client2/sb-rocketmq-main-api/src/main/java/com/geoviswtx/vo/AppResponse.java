package com.geoviswtx.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AppResponse implements Serializable {

    // 如果result 不是 1， 会有error_msg
    private Integer result;

    private String errorMsg;

}
