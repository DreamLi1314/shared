package org.example.yiji.dolphin.dao.primary;

import org.example.yiji.dolphin.dao.BaseRepository;
import org.example.yiji.dolphin.model.primary.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

  User findByUsername(String username);
  User findByPhone(String phone);
  User findByMailbox(String mailbox);
  User findByUsernameOrPhoneOrMailbox(String username, String phone, String mailbox);

  @Query(value = "select * from tb_user where department_id = ?1", nativeQuery = true)
  List<User> findAllByDept(Long deptId);

  List<User> findAllByDepartmentIsNull();

}
