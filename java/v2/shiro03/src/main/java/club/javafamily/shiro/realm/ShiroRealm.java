package club.javafamily.shiro.realm;

import club.javafamily.shiro.dao.UserDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

public class ShiroRealm extends AuthenticatingRealm {

   /**
    * 获取认证信息
    * @param token 调用 Subject.login 传入的 token 对象
    * @return 认证信息
    * @throws AuthenticationException 当认证失败时
    */
   @Override
   protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
      System.out.println("doGetAuthenticationInfo: " + token.getPrincipal());

      Object userName = token.getPrincipal();

      Object user = userDao.getUser(userName);

      if(user == null) {
//         throw new UnknownAccountException("用户不存在!");
         // 也可以直接返回 null, shiro 会帮我们判断并抛出合适的异常
         return null;
      }

      // principal 为当前用户的用户名
      Object principal = userName;
      // credentials 为数据库中用户真实的密码
      Object credentials = userDao.getPassword(userName);
      // realmName realm 的名字, 直接调用父类的 getName 方法就好了
      String realmName = getName();
      // 使用 MD5 盐值加密的盐, 必须唯一.
//      ByteSource credentialsSalt = new SimpleByteSource((String) userName);
      ByteSource credentialsSalt = ByteSource.Util.bytes(userName); // 底层就是上面的代码

      SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);

      return authenticationInfo;
   }

   public UserDao getUserDao() {
      return userDao;
   }

   public void setUserDao(UserDao userDao) {
      this.userDao = userDao;
   }

   private UserDao userDao;
}
