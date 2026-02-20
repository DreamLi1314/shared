package com.geoviswtx.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class AppConnectResponse extends AppResponse {

    private AppUserInfo userInfo;

}
