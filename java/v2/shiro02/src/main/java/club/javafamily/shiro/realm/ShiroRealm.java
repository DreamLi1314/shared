package club.javafamily.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class ShiroRealm implements Realm {
   @Override
   public String getName() {
      return null;
   }

   @Override
   public boolean supports(AuthenticationToken token) {
      return false;
   }

   @Override
   public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
      return null;
   }
}
