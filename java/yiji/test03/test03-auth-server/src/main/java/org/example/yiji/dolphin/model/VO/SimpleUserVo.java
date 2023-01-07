package org.example.yiji.dolphin.model.VO;

import org.example.yiji.dolphin.model.primary.User;
import lombok.Data;
import org.apache.org.examplemons.lang.StringUtils;

/**
 * 找回密码返回信息
 */
@Data
public class SimpleUserVo {

    private String username;//用户名
    private String phone;//手机
    private String mailbox;//邮箱

    public SimpleUserVo(User user) {
        this.username = user.getUsername();
        if(StringUtils.isNotEmpty(user.getPhone())){
            this.phone = user.getPhone().replaceAll("(^\\d{3})\\d.*(\\d{4})", "$1****$2");
        }
        if(StringUtils.isNotEmpty(user.getMailbox())){
            this.mailbox = user.getMailbox().replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2");
        }
    }

}
