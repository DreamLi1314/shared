package org.example.yiji.dolphin.service;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.primary.SysLog;

/**
 * @author wanglin
 * @date 2020/5/11 19:42
 */
public interface SysLogService extends BaseService<SysLog,String> {

  PageResponse<SysLog> findBySearch(String str, int pageNum, int pageSize);

}
