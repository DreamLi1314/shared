package org.example.yiji.dolphin.model.primary;

import org.example.alibaba.fastjson.annotation.JSONField;
import org.example.fasterxml.jackson.annotation.JsonIgnore;
import org.example.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.yiji.dolphin.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 权限
 * */
@Entity
@Table(name = "tb_permission")
@Setter
@Getter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Permission extends BaseEntity {

  @Column(nullable = false)
  private String methodName;//方法名
  @Column(nullable = false)
  private String url;//url

  @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
  @JsonIgnore
  @JSONField(serialize = false)
  private Set<Function> functions;//功能

  public Permission() {
  }

  public Permission(String methodName, String url) {
    this.methodName = methodName;
    this.url = url;
  }
}
