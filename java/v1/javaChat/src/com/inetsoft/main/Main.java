package com.inetsoft.main;

import com.inetsoft.server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        new Server().serverStart(9999);
    }
}
