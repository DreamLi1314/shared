package club.javafamily.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

   @PostMapping("/login")
   public String login(@RequestParam String userName,
                       @RequestParam String password)
   {
      System.out.println("Login: " + userName);
      Subject currentUser = SecurityUtils.getSubject();

      if(!currentUser.isAuthenticated()) {
         UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

         token.setRememberMe(true);

         try {
            currentUser.login(token);
         }
         catch (AuthenticationException e) {
            System.out.println("登录失败: " + token.getPrincipal() + ", " + e.getMessage() + "===" + e.getClass().getSimpleName());
            throw e;
         }
      }

      return "list";
   }

}
