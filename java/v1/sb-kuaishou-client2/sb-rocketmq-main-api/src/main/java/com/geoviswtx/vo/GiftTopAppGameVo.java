package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geoviswtx.dto.AppGameDto;
import com.geoviswtx.dto.GiftTopAppGameDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiftTopAppGameVo extends AppGameVo {

    // 置顶礼物列表，礼物id英文逗号分割
    private String giftList;

    private String giftExtendInfo;

    @Override
    public GiftTopAppGameDto toDto() {
        GiftTopAppGameDto appGameDto = new GiftTopAppGameDto();
        appGameDto.setTimestamp(Instant.now().getEpochSecond());
        appGameDto.setRoomCode(getRoomId());
        appGameDto.setRoundId(getGameId());
        appGameDto.setGiftList(giftList);
        appGameDto.setGiftExtendInfo(giftExtendInfo);

        return appGameDto;

    }
}
