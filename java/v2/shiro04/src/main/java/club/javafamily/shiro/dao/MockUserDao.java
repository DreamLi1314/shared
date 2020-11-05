package club.javafamily.shiro.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component("userDao")
public class MockUserDao implements UserDao {

   public void addUser(String userName, String password) {
      SimpleHash simpleHash = new SimpleHash("MD5", password, userName, 1024);
      mockUsers.put(userName, simpleHash.toHex());
   }

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

   {
      String userName = "admin";
      String pwd = "admin";
      // 使用 MD5 加密用户密码.
//      SimpleHash simpleHash = new SimpleHash("MD5", pwd, null, 1024);

      // 在使用 MD5 加密的基础上使用 MD5 盐值加密, 避免当不同用户设置相同密码时加密后的密文也一样的问题.
      // salt 需要使用一个唯一性的值, 比如 user id.
      // 假设我们 UserName 唯一, 那么我们就用 UserName 作为 MD5 盐值加密的盐.
      SimpleHash simpleHash = new SimpleHash("MD5", pwd, userName, 1024);

//      mockUsers.put("admin", new String(simpleHash.getBytes()));
      /**
       * 为什么上面代码不对? 因为传入 SimpleHash 的密码会被转化为 byte[], 然后再 hash 编码 hashIterations 次.
       *     ByteSource sourceBytes = this.convertSourceToBytes(source);
       *     this.hash(sourceBytes, saltBytes, hashIterations);
       *
       *     simpleHash 的 {@link SimpleHash#getBytes()} 是被 hash 编码后的 byte[],
       *     如果用来构造 String 就乱码了, 在密码匹配时
       *     {@link org.apache.shiro.authc.credential.HashedCredentialsMatcher#doCredentialsMatch }
       *     通过乱码的 String 再转化为 byte[] 自然就是错误的.
       */
      mockUsers.put(userName, simpleHash.toHex());

      userName = "jack";
      pwd = "jack";
      addUser(userName, pwd);

      userName = "user";
      pwd = "user";
      addUser(userName, pwd);
   }

}
