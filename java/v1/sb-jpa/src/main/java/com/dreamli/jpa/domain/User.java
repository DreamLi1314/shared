package com.dreamli.jpa.domain;

import javax.persistence.*;

// 标注这是一个实体类
@Entity
// 指定表名
@Table(name = "t_user")
public class User {

    @Id  // 标注主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 让主键自增
    private int id;

    @Column(length = 30) // 标注列, 可以修改列名,长度等
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
