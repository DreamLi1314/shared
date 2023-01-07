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
 * 角色
 * */
@Entity
@Table(name = "tb_role")
@Setter
@Getter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Role extends BaseEntity {

  @Column(nullable = false,unique = true)
  private String name;//角色名

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinTable(name = "role_function",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "function_id"))
  private Set<Function> functions;//功能

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JsonIgnore
  @JSONField(serialize = false)
  private Set<User> users;//用户

  @Override
  public String toString() {
    return "Role{" +
        "id=" + super.getId() +
        ", name='" + name + '\'' +
        '}';
  }

  public Role() {
  }

  public Role(String name, Set<Function> functions) {
    this.name = name;
    this.functions = functions;
  }
}
