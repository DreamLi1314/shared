package org.example.yiji.dolphin.model.primary;

import org.example.alibaba.fastjson.annotation.JSONType;
import org.example.fasterxml.jackson.annotation.JsonIgnore;
import org.example.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.yiji.dolphin.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 部门
 * */
@Entity
@Table(name = "tb_department")
@Getter
@Setter
@JSONType(ignores = { "childDepartments", "users" })
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Department extends BaseEntity {

  @Column(nullable = false)
  private String name;//部门名

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department parentDepartment;//上级部门

  @OneToMany(mappedBy = "parentDepartment", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JsonIgnore
  private Set<Department> childDepartments;//子部门

  @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JsonIgnore
  private Set<User> users;//用户

  public Department() {
  }

  public Department(String name) {
    this.name = name;
  }
}
