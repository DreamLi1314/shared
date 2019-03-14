package com.dreamli.crud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    public LoginController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostMapping("/login")
    public String login(String username, String password,
                        Map<String, Object> params,
                        HttpSession session)
    {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            // 我不知道为什么不能使用 error-msg 的变量名. Thymeleaf 页面会报错.
            params.put("errorMsg",
               messageSource.getMessage("crud.login.emptyUsernameOrPwd",
                  null, LocaleContextHolder.getLocale()));
            LOGGER.warn("Username or password is empty. Username:"
                    + username + ", Password: " + password);
            return "index.html";
        }

        session.setAttribute(ACTIVE_USE_FLAG,username);

        return "redirect:/dashboard";
    }

    private final MessageSource messageSource;

    public static final String ACTIVE_USE_FLAG = "activeUser";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
}
