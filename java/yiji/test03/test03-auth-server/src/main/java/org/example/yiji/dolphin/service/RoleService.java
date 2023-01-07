package org.example.yiji.dolphin.service;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.primary.Role;

/**
 * @author wanglin
 * @date 2020/5/11 19:42
 */
public interface RoleService extends BaseService<Role,Long> {

  Role findByName(String name);
  PageResponse<Role> findBySearch(String str, int pageNum, int pageSize);

}
