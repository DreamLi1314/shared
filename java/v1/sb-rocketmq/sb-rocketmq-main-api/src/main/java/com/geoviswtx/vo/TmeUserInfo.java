package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TmeUserInfo implements Serializable {

    @JsonProperty("open_id")
    private String openId;

    private String nick;

    private String logo;

}
