package com.geoviswtx.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginVo extends UserLoginBaseVo {

    private static final long serialVersionUID = 1L;

    /**
     * 单位名称
     */
//    private Object depart;
    /**
     * session
     */
    private String token;
    /**
     * 行政编码
     */
    private String areaCode;
    /**
     * 行政区域
     */
    private String areaName;
    /**
     * 角色名称
     */
    private List<HashMap<String, Object>> role;
    /**
     * 部门id
     */
    private Long regionId;
    /**
     * 菜单
     */
    //private List<FunctionVO> functions;
    /**
     * 接口
     */
//    private Object permissions;
    //private Set<String> typeIds;
}
