package com.geoviswtx.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_live_like")
public class LiveLikeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long messageId;
    private Integer count;
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity userInfo;
}
