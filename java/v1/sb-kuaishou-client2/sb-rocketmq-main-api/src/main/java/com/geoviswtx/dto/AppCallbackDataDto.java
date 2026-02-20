package com.geoviswtx.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppCallbackDataDto implements Serializable {

    private String unique_message_id;
    private String author_open_id;
    private String room_code;
    private String push_type;
    private List<Object> payload;

}
