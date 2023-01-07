package com.mlog.blog.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jack Li
 * @date 2022/1/25 5:52 下午
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="系统用户", description="")
public class SysUser {
   private String id;

   private String username;

   private String nickname;
}
