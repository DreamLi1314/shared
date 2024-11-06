package com.geoviswtx.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dreamli
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public abstract class BaseEntity implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GenericGenerator(name = "snowFlakeIdGenerator", strategy = "com.geoviswtx.conf.SnowFlakeIdGenerator")
   @GeneratedValue(generator = "snowFlakeIdGenerator")
   private Long id;

   @Temporal(TemporalType.TIMESTAMP)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @CreatedDate
   private Date createTime;

   @Temporal(TemporalType.TIMESTAMP)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @LastModifiedDate
   private Date updateTime;

}
