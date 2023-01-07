package com.mlog.blog.service.impl;

import com.mlog.blog.entity.*;
import com.mlog.blog.mapper.BlogStatisticMapper;
import com.mlog.blog.service.IBlogStatisticService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jack Li
 * @since 2021-07-20
 */
@Service
public class BlogStatisticImpl
   extends ServiceImpl<BlogStatisticMapper, SendBlogInfoDto>
   implements IBlogStatisticService
{

   @Transactional(rollbackFor = Exception.class)
   @Override
   public List<SendBlogInfoDto> findAllByCreateTime(Date startTime, Date endTime) {
      return getBaseMapper().findAllByCreateTime(startTime, endTime);
   }

   @Transactional(rollbackFor = Exception.class)
   @Override
   public List<BlogCountDto> findCountByCreateTime(Date startTime, Date endTime) {
      return getBaseMapper().findCountByCreateTime(startTime, endTime);
   }

   @Transactional(rollbackFor = Exception.class)
   @Override
   public List<BlogCountDto> findTypeCountByCreateTime(Date startTime, Date endTime) {
      return getBaseMapper().findTypeCountByCreateTime(startTime, endTime);
   }

   @Transactional(rollbackFor = Exception.class)
   @Override
   public List<SysUser> findAllUsers() {
      return getBaseMapper().findAllUsers();
   }
}
