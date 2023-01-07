package org.example.yiji.dolphin.service;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.primary.Permission;

import java.util.List;

/**
 * @author wanglin
 * @date 2020/5/11 19:42
 */
public interface PermissionService extends BaseService<Permission,Long> {

  List<Permission> findByMethodName(String methodName);
  PageResponse<Permission> findBySearch(String str, int pageNum, int pageSize);

}
