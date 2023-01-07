package com.mlog.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mlog.blog.entity.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jack Li
 * @since 2021-07-20
 */
public interface IBlogStatisticService extends IService<SendBlogInfoDto> {

   /**
    * 根据起止时间查询博文详情信息
    * @param startTime 开始时间(包含)
    * @param endTime 结束时间(不包含)
    * @return List
    */
   List<SendBlogInfoDto> findAllByCreateTime(Date startTime, Date endTime);

   /**
    * 根据开始时间查询发文数量
    * @param startTime 开始时间(包含)
    * @return List
    */
   default List<BlogCountDto> findCountByCreateTime(Date startTime) {
      return findCountByCreateTime(startTime, new Date());
   }

   /**
    * 根据起止时间查询发文数量
    * @param startTime 开始时间(包含)
    * @param endTime 结束时间(不包含)
    * @return List
    */
   List<BlogCountDto> findCountByCreateTime(Date startTime, Date endTime);

   /**
    * 根据起止时间分类查询发文数量
    * @param startTime 开始时间(包含)
    * @return List(复用 BlogCountDto)
    */
   default List<BlogCountDto> findTypeCountByCreateTime(Date startTime) {
      return findTypeCountByCreateTime(startTime, new Date());
   }

   /**
    * 根据起止时间分类查询发文数量
    * @param startTime 开始时间(包含)
    * @param endTime 结束时间(不包含)
    * @return List(复用 BlogCountDto)
    */
   List<BlogCountDto> findTypeCountByCreateTime(Date startTime,
                                                Date endTime);

   /**
    * 获取用户列表
    * @return 用户列表
    */
   List<SysUser> findAllUsers();
}
