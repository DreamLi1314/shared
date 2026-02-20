package com.geoviswtx.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserInfo implements Serializable {

    private String authorOpenId;

    private String userName;

    private String headUrl;

}
