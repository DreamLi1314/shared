package com.jack.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置授权规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() // 首页都允许访问
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        // 开启自动配置的登录功能. 都是 SpringSecurity 处理
        // 1. 访问资源时自动跳转到 /login 登录页面.
        // 2. 登录错误重定向到 /login?error
        // 3. 默认 /login 的 POST 请求处理登录请求. 定制 LoginPage 后请求处理逻辑的 URI 为 /loginPage
        // 4. 默认用户名和密码的表单名为 username 和 password
        // http.formLogin();
        http.formLogin().usernameParameter("username")
                .passwordParameter("pwd")
                .loginPage("/userlogin"); // 定制登录页面, 请求处理 URI 为/userlogin

        // 开启自动配置的注销功能
        // 发送 /logout 的 POST 请求表示用户注销, 并销毁 Session.
        // http.logout(); // 账户注销成功后跳转到 login 页面
        http.logout().logoutSuccessUrl("/"); // 注销成功后跳转到首页

        // 开启 rememberMe 的自动配置
        // SpringSecurity 会向浏览器发送 Cookie 保存登录信息.过期时间为 14 天
        // 或者注销时删除 Cookie
        http.rememberMe().rememberMeParameter("remeber"); // 自定义登录页时的记住我请求参数
    }

    /**
     * 定义认证规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("VIP1", "VIP2", "VIP3")
            .and()
            .withUser("level1").password(new BCryptPasswordEncoder().encode("level1")).roles("VIP1")
            .and()
            .withUser("level2").password(new BCryptPasswordEncoder().encode("level2")).roles("VIP1", "VIP2")
            .and()
            .withUser("level13").password(new BCryptPasswordEncoder().encode("level13")).roles("VIP1", "VIP3");
    }
}
