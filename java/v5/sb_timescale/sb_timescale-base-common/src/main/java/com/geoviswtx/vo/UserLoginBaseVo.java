package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginBaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime loginTime;
    /**
     * 登陆ip
     */
    private String loginIp;
    /**
     * 标记
     */
    private String sign;
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

}
