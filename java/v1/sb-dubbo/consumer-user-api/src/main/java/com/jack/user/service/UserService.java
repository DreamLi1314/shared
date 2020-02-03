package com.jack.user.service;

import com.jack.ticket.service.TicketService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public final TicketService ticketService;

    public UserService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void buyTicket() {
        String ticket = ticketService.getTicket();

        System.out.println("买到票了, ticket: " + ticket);
    }
}
