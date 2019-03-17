package com.dreamli.crud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class BaseController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                       HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    public static final String ACTIVE_USE_FLAG = "activeUser";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
}
