package com.mlog.blog.entity;

import com.mlog.export.annotation.Exportable;
import com.mlog.lens.annotation.TableLensColumn;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jack Li
 * @date 2022/1/25 5:52 下午
 * @description
 */
@Data
@Exportable("[博客统计] %s-%s")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="发文数量统计 Dto", description="")
public class BlogCountDto {
   @TableLensColumn(order = 1, localizedHeader = "姓名")
   private String nickname;

   @TableLensColumn(order = 2, localizedHeader = "数量")
   private Integer count;
}
