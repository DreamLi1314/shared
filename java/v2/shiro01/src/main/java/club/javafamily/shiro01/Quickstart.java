package club.javafamily.shiro01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class Quickstart {

    public static void main(String[] args) {

        // 通过 ini 文件创建一个 SecurityManager 的工厂(生产环境不会这么用)
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 获取 SecurityManager
        SecurityManager securityManager = factory.getInstance();

        // 设置 SecurityManager 为单例, 以后通过 SecurityUtils(与 Spring 整合后没有意义)
        SecurityUtils.setSecurityManager(securityManager);

        // 获取 Subject 对象
        Subject currentUser = SecurityUtils.getSubject();

        // 测试使用 Session 对象 ---> 注意这是 SE 项目, 不是 web 项目, 依然可以用 Session
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");

        System.out.println("===测试 Session 对象: someKey = " + value);

        // 测试登录
        if (!currentUser.isAuthenticated()) { // 如果用户没有登录
            // 通过用户名和密码构造 UsernamePasswordToken 用于验证
            // 这里的的 jack---JavaFamily 用户名和密码来自于 shiro.ini 文件
            UsernamePasswordToken token = new UsernamePasswordToken("jack", "JavaFamily");
            // 设置 RememberMe
            token.setRememberMe(true);
            try {
                // 执行登录
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                System.out.println("账户不存在" + token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException ice) {
                System.out.println("密码不正确: " + token.getPrincipal());
                return;
            } catch (LockedAccountException lae) {
                System.out.println("账户已经登录: " + token.getPrincipal());
                return;
            }
            catch (AuthenticationException ae) {
                System.out.println("登录失败! 请过会再试!");
                return;
            }
        }

        System.out.println("用户 " + currentUser.getPrincipal() + " 已成功登录!");

        // 测试 Role
        if (currentUser.hasRole("dev")) {
            System.out.println("用户" + currentUser.getPrincipal() + " 属于 dev Role");
        } else {
            System.out.println("用户" + currentUser.getPrincipal() + " 不是 dev Role");
        }

        // 测试权限
        if (currentUser.isPermitted("code:commit")) {
            System.out.println("用户 " + currentUser.getPrincipal() + " 拥有代码提交的权限.");
        } else {
            System.out.println("用户 " + currentUser.getPrincipal() + " 没有代码提交的权限.");
        }

        if (currentUser.isPermitted("code:test:write")) {
            System.out.println("用户 " + currentUser.getPrincipal() + " 拥有 code:test:write 权限");
        } else {
            System.out.println("用户 " + currentUser.getPrincipal() + " 没有 code:test:write 权限");
        }

        if (currentUser.isPermitted("code:test:delete")) {
            System.out.println("用户 " + currentUser.getPrincipal() + " 拥有 code:test:delete 权限");
        } else {
            System.out.println("用户 " + currentUser.getPrincipal() + " 没有 code:test:delete 权限");
        }


        //注销
        currentUser.logout();

        System.exit(0);
    }
}
