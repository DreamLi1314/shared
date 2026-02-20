package com.geoviswtx.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_message")
public class MessageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message_id;
    private String event;
    private String app_id;
    private Long timestamp;
    private String unique_message_id;
    private String author_open_id;
    private String room_code;
    private String push_type;
}
