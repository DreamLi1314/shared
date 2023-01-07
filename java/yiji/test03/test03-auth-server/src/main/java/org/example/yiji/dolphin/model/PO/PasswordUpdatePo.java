package org.example.yiji.dolphin.model.PO;

import lombok.Data;

/**
 * @Description:
 * @Author wanglin
 * @Date 2021/1/26 13:46
 */
@Data
public class PasswordUpdatePo {

    private String old;//旧密码
    private String password;//新密码

}
