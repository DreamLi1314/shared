package org.example.yiji.dolphin.model.VO;

import org.example.fasterxml.jackson.annotation.JsonFormat;
import org.example.mlog.export.annotation.Exportable;
import org.example.mlog.lens.annotation.TableLensColumn;
import lombok.Data;

import java.util.Date;

/**
 * 用户
 */
@Data
@Exportable("用户列表")
public class UserVo {

  private Long id;//编号
  @TableLensColumn(order = 1, localizedHeader = "账户")
  private String username;//用户名
  @TableLensColumn(order = 2, localizedHeader = "姓名")
  private String nickName; // 姓名
  @TableLensColumn(order = 3, localizedHeader = "联系方式")
  private String phone;//手机
  private String mailbox;//邮箱
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;//创建时间
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date lastTime;//最后操作时间
  @TableLensColumn(order = 4, localizedHeader = "所属部门")
  private String departmentName;//部门名称
  @TableLensColumn(order = 5, localizedHeader = "用户角色")
  private String roleName;//角色名称
  private Long departmentId;//部门id
  private String dept;//部门
  private Long roleId;//角色id
}
