package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.geoviswtx.dto.AppGameDto;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppGameVo implements Serializable {

    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("room_id")
    private String roomId;

    public AppGameDto toDto() {
        return AppGameDto.builder()
                .timestamp(Instant.now().getEpochSecond())
                .roomCode(roomId)
                .roundId(gameId)
                .build();
    }

}
