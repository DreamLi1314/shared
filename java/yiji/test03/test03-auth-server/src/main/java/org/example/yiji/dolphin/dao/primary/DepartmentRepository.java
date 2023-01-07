package org.example.yiji.dolphin.dao.primary;

import org.example.yiji.dolphin.dao.BaseRepository;
import org.example.yiji.dolphin.model.primary.Department;

public interface DepartmentRepository extends BaseRepository<Department, Long> {

  Department findByName(String name);

}
