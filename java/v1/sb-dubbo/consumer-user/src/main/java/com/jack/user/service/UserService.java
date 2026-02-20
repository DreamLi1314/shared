package com.jack.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jack.ticket.service.TicketService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Reference
    TicketService ticketService;

    public void buyTicket() {
        String ticket = ticketService.getTicket();

        System.out.println("买到票了, ticket: " + ticket);
    }
}
