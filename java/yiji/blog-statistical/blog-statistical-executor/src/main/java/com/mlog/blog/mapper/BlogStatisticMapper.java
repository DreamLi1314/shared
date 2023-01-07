package com.mlog.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlog.blog.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jack Li
 * @since 2021-07-20
 */
public interface BlogStatisticMapper extends BaseMapper<SendBlogInfoDto> {

   /**
    * 根据起止时间查询博文详情信息
    * @param startTime 开始时间(包含)
    * @param endTime 结束时间(不包含)
    * @return List
    */
   List<SendBlogInfoDto> findAllByCreateTime(@Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime);

   /**
    * 根据起止时间查询发文数量
    * @param startTime 开始时间(包含)
    * @param endTime 结束时间(不包含)
    * @return List
    */
   List<BlogCountDto> findCountByCreateTime(@Param("startTime") Date startTime,
                                            @Param("endTime") Date endTime);

   /**
    * 根据起止时间分类查询发文数量
    * @param startTime 开始时间(包含)
    * @param endTime 结束时间(不包含)
    * @return List(复用 BlogCountDto)
    */
   List<BlogCountDto> findTypeCountByCreateTime(@Param("startTime") Date startTime,
                                                @Param("endTime") Date endTime);

   /**
    * 获取用户列表
    * @return 用户列表
    */
   List<SysUser> findAllUsers();
}
