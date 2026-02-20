package club.javafamily.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class ContextTest {

   @Test
   public void test1() throws Exception {
      SimpleHash simpleHash = new SimpleHash("MD5", "jack", null, 1024);
      byte[] bytes = simpleHash.getBytes();

      System.out.println("111" + bytes);

      String string = new String(bytes); // 这里已经乱码

      System.out.println("2222" + string);

      byte[] bytes1 = string.getBytes();

      System.out.println("3333333" + bytes1);
   }

}
