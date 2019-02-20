package com.inetsoft.main;

import com.inetsoft.client.Client;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        new Client().connect("127.0.0.1", 9999);
    }
}
