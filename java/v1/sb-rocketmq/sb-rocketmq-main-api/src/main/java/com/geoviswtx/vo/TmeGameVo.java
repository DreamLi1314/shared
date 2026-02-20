package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmeGameVo implements Serializable {

    @JsonProperty("game_id")
    private Long gameId;

    private String app;

    @JsonProperty("room_id")
    private Long roomId;

    private String code;

}
