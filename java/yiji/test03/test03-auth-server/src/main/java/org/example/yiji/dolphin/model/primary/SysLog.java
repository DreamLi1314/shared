package org.example.yiji.dolphin.model.primary;

import org.example.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志
 * */
@Entity
@Table(name = "tb_log")
@Data
public class SysLog implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "idGenerator", strategy = "uuid")
  @GeneratedValue(generator = "idGenerator")
  private String id;//编号

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date time;//操作时间

  private String username;//用户名
  private String url;//请求信息
  private String type;//请求方式
  private String ip;//请求IP

}
