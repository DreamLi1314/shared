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
 * 功能
 */
@Entity
@Table(name = "tb_function")
@Setter
@Getter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Function {

    @Id
    private String key;//key
    @Column(nullable = false)
    private String name;//功能名

    private Boolean head;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "function_permission",
            joinColumns = @JoinColumn(name = "function_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;//接口

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Function parentFunction;//父权限

    @OneToMany(mappedBy = "parentFunction", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnore
    @JSONField(serialize = false)
    private Set<Function> childFunctions;//子权限

    @ManyToMany(mappedBy = "functions", fetch = FetchType.LAZY)
    @JsonIgnore
    @JSONField(serialize = false)
    private Set<Role> roles;//角色

    public Function() {
    }

    public Function(String name, String key, Boolean head, Function parentFunction, Set<Permission> permissions) {
        this.name = name;
        this.key = key;
        this.head = head;
        this.parentFunction = parentFunction;
        this.permissions = permissions;
    }
}
