package org.example.yiji.dolphin.controller;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.primary.SysLog;
import org.example.yiji.dolphin.service.SysLogService;
import java.util.List;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志
 */
@RestController
@RequestMapping("/log")
@Validated
public class SysLogController {

  @Autowired
  private SysLogService sysLogService;

  /**
   * 查询全部
   * @return
   */
  @GetMapping(value = "/all")
  public List<SysLog> getAll() {
    return sysLogService.selectAll();
  }

  /**
   * 分页查询
   * @param str
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/search")
  public PageResponse<SysLog> search(@RequestParam(required = false) String str,
                                     @Min(value = 1,message = "请输入≥1的页码值") @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    return sysLogService.findBySearch(str, pageNum, pageSize);
  }

}
