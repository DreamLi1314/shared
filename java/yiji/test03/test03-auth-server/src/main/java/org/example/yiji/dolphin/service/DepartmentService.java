package org.example.yiji.dolphin.service;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.primary.Department;

/**
 * @author wanglin
 * @date 2020/5/11 19:42
 */
public interface DepartmentService extends BaseService<Department,Long> {

  Department findByName(String name);
  PageResponse<Department> findBySearch(String str, int pageNum, int pageSize);

}
