package com.geoviswtx.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class TmeGetCodeResponse extends TmeResponse<TmeCodeItem> {
}
