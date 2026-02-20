package com.mlog.blog.controller;


import com.mlog.blog.entity.BlogCountDto;
import com.mlog.blog.entity.SendBlogInfoDto;
import com.mlog.blog.service.IBlogStatisticService;
import com.mlog.lens.core.ExportTableLens;
import com.mlog.lensexporter.service.ExportService;
import com.mlog.lensexporter.utils.ExportUtil;
import com.mlog.utils.date.DateUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jack Li
 * @since 2021-07-20
 */
@Api("博客统计 Api")
@RestController
public class BlogStatisticController {

   private final IBlogStatisticService blogStatisticService;
   private final ExportService exportService;

   public BlogStatisticController(IBlogStatisticService blogStatisticService,
                                  ExportService exportService)
   {
      this.blogStatisticService = blogStatisticService;
      this.exportService = exportService;
   }

   @ApiOperation("按起止时间查询发文详情")
   @GetMapping("/send-article")
   public List<SendBlogInfoDto> list(
      @ApiParam("开始时间") @RequestParam("startTime") Date startTime,
      @ApiParam("结束时间") @RequestParam(value = "endTime", required = false) Date endTime,
      @ApiParam("是否导出") @RequestParam(value = "export", defaultValue = "false")
         Boolean export,
      HttpServletResponse response)
      throws Exception
   {
      final List<SendBlogInfoDto> blogStatistics
         = blogStatisticService.findAllByCreateTime(startTime, endTime);

      if(export) {
         ExportTableLens tableLens = ExportUtil.buildExportableTableLens(
            blogStatistics, SendBlogInfoDto.class);
         tableLens.setIgnoreTitle(false);
         tableLens.setTableName(String.format(tableLens.getTableName(),
            DateUtil.formatOnlyNormalDate(startTime)));

         tableLens.setSheetName("博客统计");

         exportService.export(tableLens, response);
         return null;
      }

      return blogStatistics;
   }

   @ApiOperation("按起止时间统计发文数")
   @GetMapping("/send-count")
   public List<BlogCountDto> count(
      @ApiParam("开始时间") @RequestParam("startTime") Date startTime,
      @ApiParam("结束时间") @RequestParam(value = "endTime", required = false) Date endTime,
      @ApiParam("是否导出") @RequestParam(value = "export", defaultValue = "false")
         Boolean export,
      HttpServletResponse response)
      throws Exception
   {
      final List<BlogCountDto> blogStatistics
         = blogStatisticService.findCountByCreateTime(startTime, endTime);

      if(export) {
         ExportTableLens tableLens = ExportUtil.buildExportableTableLens(
            blogStatistics, BlogCountDto.class);
         tableLens.setIgnoreTitle(false);
         tableLens.setTableName(String.format(tableLens.getTableName(),
            DateUtil.formatOnlyNormalDate(startTime),
            DateUtil.formatOnlyNormalDate(endTime == null ? new Date() : endTime)));

         tableLens.setSheetName("博客统计");

         exportService.export(tableLens, response);

         return null;
      }

      return blogStatistics;
   }

   @ApiOperation("按起止时间统计各个部门发文数")
   @GetMapping("/send-count-type")
   public List<BlogCountDto> countType(
      @ApiParam("开始时间") @RequestParam("startTime") Date startTime,
      @ApiParam("结束时间") @RequestParam(value = "endTime", required = false) Date endTime,
      @ApiParam("是否导出") @RequestParam(value = "export", defaultValue = "false")
         Boolean export,
      HttpServletResponse response)
      throws Exception
   {
      final List<BlogCountDto> blogStatistics
         = blogStatisticService.findTypeCountByCreateTime(startTime, endTime);

      if(export) {
         ExportTableLens tableLens = ExportUtil.buildExportableTableLens(
            blogStatistics, BlogCountDto.class);
         tableLens.setIgnoreTitle(false);
         tableLens.setTableName(String.format(tableLens.getTableName(),
            DateUtil.formatOnlyNormalDate(startTime),
            DateUtil.formatOnlyNormalDate(endTime == null ? new Date() : endTime)));

         tableLens.setSheetName("博客统计");

         exportService.export(tableLens, response);

         return null;
      }

      return blogStatistics;
   }

}

