package com.jack.provider.service;

import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "<从你的全世界路过>";
    }
}
