package com.geoviswtx.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder(toBuilder = true)
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
public abstract class BaseEntity implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GenericGenerator(name = "snowFlakeIdGenerator", strategy = "com.emlog.conf.SnowFlakeIdGenerator")
   @GeneratedValue(generator = "snowFlakeIdGenerator")
   private Long id;

   @Temporal(TemporalType.TIMESTAMP)
   @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
   @CreatedDate
   private Date createDate;

   @Temporal(TemporalType.TIMESTAMP)
   @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
   @LastModifiedDate
   private Date updateDate;

}
