package org.example.yiji.dolphin.model.PO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author wanglin
 * @Date 2021/4/14 20:29
 */
@Data
public class UserAddPo {
    @NotBlank(message = "用户名不能为空")
    private String username;//用户名
    @NotBlank(message = "姓名不能为空")
    private String nickName;//用户名
    @NotBlank(message = "密码不能为空")
    private String password;//密码
    private String phone;//手机
    private String mailbox;//邮箱
    private Long departmentId;//部门编号
    private String dept;//部门
    private Long roleId;//角色编号
}
