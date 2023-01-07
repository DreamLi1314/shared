package org.example.yiji.dolphin.service;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.PO.PasswordUpdatePo;
import org.example.yiji.dolphin.model.VO.SimpleUserVo;
import org.example.yiji.dolphin.model.VO.UserLoginVo;
import org.example.yiji.dolphin.model.primary.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wanglin
 * @date 2020/5/11 19:42
 */
public interface UserService extends BaseService<User,Long> {

  User findByUsername(String username);
  User findByPhone(String phone);
  User findByMailbox(String mailbox);
  PageResponse<User> findBySearch(String str, int pageNum, int pageSize);
  User save(User user);
  User update(User user);
  UserLoginVo login(User user, HttpServletRequest request);
  SimpleUserVo check(String info, HttpServletRequest request);
  User reset(PasswordUpdatePo po, HttpServletRequest request);

  boolean resetPwd(Long userId, HttpServletRequest request);

  List<User> findAllByIds(Long[] ids);

  List<User> findAllByDept(Long deptId);
}
