package club.javafamily.demo07_admin_user;

import org.flowable.engine.*;
import org.flowable.idm.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test01UserAdmin {


   ProcessEngine processEngine;

   @BeforeEach
   public void init() {
      processEngine = ProcessEngines.getDefaultProcessEngine();
   }

   @Test
   void testCreateUser() {
      final IdentityService identityService
         = processEngine.getIdentityService();

      final User user = identityService.newUser("wangwu");

      user.setFirstName("wang");
      user.setLastName("wu");
      user.setEmail("wangwu@qq.com");

      identityService.saveUser(user);
   }
}
