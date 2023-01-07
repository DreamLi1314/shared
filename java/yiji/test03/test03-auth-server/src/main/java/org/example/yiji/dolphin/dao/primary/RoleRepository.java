package org.example.yiji.dolphin.dao.primary;

import org.example.yiji.dolphin.dao.BaseRepository;
import org.example.yiji.dolphin.model.primary.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {

  Role findByName(String name);

}
