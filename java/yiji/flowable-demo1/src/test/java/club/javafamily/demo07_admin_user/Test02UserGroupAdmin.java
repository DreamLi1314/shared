package club.javafamily.demo07_admin_user;

import org.flowable.engine.*;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test02UserGroupAdmin {


   ProcessEngine processEngine;

   @BeforeEach
   public void init() {
      processEngine = ProcessEngines.getDefaultProcessEngine();
   }

   @Test
   void testCreateUserGroup() {
      final IdentityService identityService
         = processEngine.getIdentityService();

      // 创建分组并指定属性信息
      final Group group = identityService.newGroup("group2");
      group.setName("销售部");
      group.setType("sales");

      identityService.saveGroup(group);
   }

   /**
    * 分配用户到对应的组
    */
   @Test
   void testAssignUserGroup() {
      final IdentityService identityService
         = processEngine.getIdentityService();

      final Group devGroup = identityService.createGroupQuery()
         .groupId("group1")
         .singleResult();

      final List<User> list = identityService.createUserQuery()
         .list();

      for (User user : list) {
         System.out.println("user = " + user.getId());

         identityService.createMembership(user.getId(), devGroup.getId());
      }

   }
}
