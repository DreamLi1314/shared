package com.geoviswtx.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppGameDto implements Serializable {

    private String roomCode;

    private Long timestamp;

    private String sign;

    private String roundId;

}
