package com.inetsoft.main;

import com.inetsoft.server.Server;

public class Main {

    public static final String CHAT_HOST = "127.0.0.1";
    public static final int CHAT_PORT = 9999;


    public static void main(String[] args) throws Exception {
        new Server().serverStart(CHAT_PORT);
    }
}
