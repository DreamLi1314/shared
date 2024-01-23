package com.geoviswtx.dto;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmeCallbackDto extends DefaultMessage {

    private String cmd;
    private String msg_type;
    private Object data;

}
