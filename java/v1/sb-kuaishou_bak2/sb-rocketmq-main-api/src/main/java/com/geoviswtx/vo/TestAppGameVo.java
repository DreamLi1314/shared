package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestAppGameVo extends AppGameVo {

    private Integer rand_id;
    private String comment;

    private Integer gift_id;
    private String gift_name;
    private Integer gift_val_total;
    private Integer gift_cnt;

}
