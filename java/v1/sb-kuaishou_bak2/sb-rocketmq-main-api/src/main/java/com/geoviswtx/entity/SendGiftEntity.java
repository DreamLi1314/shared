package com.geoviswtx.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_send_gift")
public class SendGiftEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long messageId;
    private String uniqueNo;
    private String giftId;
    private String giftName;
    private Integer giftCount;
    private Double giftUnitPrice;
    private Double giftTotalPrice;
    @ManyToOne
    private UserEntity userInfo;
}