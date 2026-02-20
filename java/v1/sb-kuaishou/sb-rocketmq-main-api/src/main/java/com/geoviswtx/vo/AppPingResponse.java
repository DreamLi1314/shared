package com.geoviswtx.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class AppPingResponse extends AppResponse {
    // 1-开启状态；2-断开状态
    private Integer taskStatus;
}
