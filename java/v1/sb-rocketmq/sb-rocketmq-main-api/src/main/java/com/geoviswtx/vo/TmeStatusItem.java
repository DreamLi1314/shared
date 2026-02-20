package com.geoviswtx.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TmeStatusItem implements Serializable {
    private String status;
}
