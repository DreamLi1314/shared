package org.example.yiji.dolphin.model.secondary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import java.io.Serializable;

/**
 * 盐
 */
@Data
@Entity
@Table(name = "tb_salt")
@Proxy(lazy=false)
public class Salt implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;
  private String detail;

}
