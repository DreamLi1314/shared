package com.geoviswtx.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_live_comment")
public class LiveCommentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long messageId;
    private String content;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private UserEntity userInfo;
}
