package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TmeConnectItem implements Serializable {
    private String token;

    @JsonProperty("user_info")
    private TmeUserInfo userInfo;
}
