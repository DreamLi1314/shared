package com.inetsoft.main;

import com.inetsoft.client.Client;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        new Client().connect(Main.CHAT_HOST, Main.CHAT_PORT);
    }
}
