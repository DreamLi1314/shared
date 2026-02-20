package com.dreamli.crud.controller;

import com.dreamli.crud.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    public LoginController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/test-error")
    public String gotoError(String name) throws Exception {
        if(StringUtils.isEmpty(name)) {
            throw new UserNotFoundException("user not found...");
        }

        return "/";
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

        session.setAttribute(BaseController.ACTIVE_USE_FLAG, username);

        return "redirect:/dashboard";
    }

    private final MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
}
