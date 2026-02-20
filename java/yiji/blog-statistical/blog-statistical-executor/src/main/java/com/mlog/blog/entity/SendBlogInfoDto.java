package com.mlog.blog.entity;

import com.mlog.export.annotation.Exportable;
import com.mlog.lens.annotation.TableLensColumn;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Jack Li
 * @since 2021-07-20
 */
@Data
@Exportable("【博客统计】- %s")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="博客统计 Dto", description="")
public class SendBlogInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableLensColumn(order = 1, localizedHeader = "姓名")
    private String nickname;

    @TableLensColumn(order = 2, localizedHeader = "文章")
    private String title;

    @TableLensColumn(order = 3, localizedHeader = "发文时间")
    private String createTime;

    @TableLensColumn(order = 4, localizedHeader = "最后更新时间")
    private String updateTime;

    @TableLensColumn(order = 5, localizedHeader = "预览链接")
    private String url;
}
