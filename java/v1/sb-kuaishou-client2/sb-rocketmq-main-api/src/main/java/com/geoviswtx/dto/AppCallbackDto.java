package com.geoviswtx.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppCallbackDto extends DefaultMessage {

    private AppCallbackDataDto data;
    private String message_id;
    private String event;
    private String app_id;
    private Long timestamp;

}
