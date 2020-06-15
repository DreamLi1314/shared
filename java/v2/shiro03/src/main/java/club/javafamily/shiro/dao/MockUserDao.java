package club.javafamily.shiro.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("userDao")
public class MockUserDao implements UserDao {

   @Override
   public Object getUser(Object userName) {
      for (Map.Entry<String, String> entrty : mockUsers.entrySet()) {
         if(userName.equals(entrty.getKey())) {
            return entrty.getValue();
         }
      }

      return null;
   }

   @Override
   public Object getPassword(Object userName) {
      return mockUsers.get(userName);
   }

   private static final Map<String, String> mockUsers = new HashMap<>();

   static {
      mockUsers.put("admin", "admin");

      mockUsers.put("jack", "jack");

      mockUsers.put("user", "user");
   }

}
