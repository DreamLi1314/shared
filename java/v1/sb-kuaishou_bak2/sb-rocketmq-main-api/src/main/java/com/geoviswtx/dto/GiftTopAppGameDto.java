package com.geoviswtx.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
public class GiftTopAppGameDto extends AppGameDto {
    // 置顶礼物列表，礼物id英文逗号分割
    private String giftList;

    private String giftExtendInfo;
}
