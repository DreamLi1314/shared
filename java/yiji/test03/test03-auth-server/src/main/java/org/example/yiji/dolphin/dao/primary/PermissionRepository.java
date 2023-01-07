package org.example.yiji.dolphin.dao.primary;

import org.example.yiji.dolphin.dao.BaseRepository;
import org.example.yiji.dolphin.model.primary.Permission;

import java.util.List;

public interface PermissionRepository extends BaseRepository<Permission, Long> {

  List<Permission> findByMethodName(String methodName);

}
