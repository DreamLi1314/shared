package org.example.yiji.dolphin.model.primary;

import org.example.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * */
@Entity
@Table(name = "tb_user")
@Data
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;//编号

  @Column(nullable = false,unique = true)
  private String username;//用户名

  private String nickName; //姓名

  @Column(nullable = false)
  private String password;//密码

  @Column(unique = true)
  private String phone;//手机

  @Column(unique = true)
  private String mailbox;//邮箱

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;//创建时间

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastTime;//最后操作时间

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "department_id")
  private Department department;//部门

  /**
   * 部门字符串
   */
  private String dept;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "role_id")
  private Role role;//角色

  public User() {
  }

  public User(String username, String password, String phone, String mailbox, Date createTime, Date lastTime, Role role) {
    this.username = username;
    this.password = password;
    this.phone = phone;
    this.mailbox = mailbox;
    this.createTime = createTime;
    this.lastTime = lastTime;
    this.role = role;
  }

  public User(String nickName, String username, String password, String phone, String mailbox, Date createTime, Date lastTime, Role role) {
    this.nickName = nickName;
    this.username = username;
    this.password = password;
    this.phone = phone;
    this.mailbox = mailbox;
    this.createTime = createTime;
    this.lastTime = lastTime;
    this.role = role;
  }

  public User(String username,
              String nickName,
              String password,
              String phone,
              String mailbox,
              String dept,
              Role role)
  {
    this.username = username;
    this.nickName = nickName;
    this.password = password;
    this.phone = phone;
    this.mailbox = mailbox;
    this.dept = dept;
    this.role = role;
  }
}
