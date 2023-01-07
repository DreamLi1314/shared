package org.example.yiji.dolphin.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
//  @GenericGenerator(name = "idGenerator", strategy = "uuid")
//  @GeneratedValue(generator = "idGenerator")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

}
