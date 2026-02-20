package com.inetsoft.global;

import com.inetsoft.base.Config;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class EnvConfig extends Config<Serializable> {
    private static EnvConfig config = new EnvConfig();

    public static EnvConfig getInstance() {
        return config;
    }

    // use split message, message like as [name:]content
    public static final String MSG_SPLIT_TOKEN = ":";
    // global coding
    public static final String GLOBAL_CODING = "UTF-8";
    // log out command
    public static final String LOGOUT_COMMAND = "exit()";

    public static final String CONNECTION_LOST_FLAG = "^*connection lose*^";

    public static void log(Class clazz, String msg) {
        System.out.println("[Error] from " + clazz.getName() + " : " + msg);
    }

    public static void writeContent(OutputStream out, String content) throws Exception {
        byte[] sendBytes = content.getBytes(EnvConfig.GLOBAL_CODING);

        // 将用户输入内容发送给 Server
        // 1. 先将用户输入数据的长度发送给 Server.
        out.write(sendBytes.length >> 8);
        out.write(sendBytes.length);
        // 2. 再将用户输入数据的内容发送给 Server
        out.write(sendBytes);
        out.flush();
    }

    public static String readContent(InputStream in) throws Exception {
        // 先读取数据的长度信息
        int first = in.read();

        if (first == -1) {
            // connection has close
            EnvConfig.log(EnvConfig.class, "Connection has been close.");
            return EnvConfig.CONNECTION_LOST_FLAG;
        }

        int second = in.read();
        // 得到数据长度
        int length = (first << 8) + second;

        byte[] bytes = new byte[length];
        in.read(bytes);
        // 得到用户输入数据的内容
        return new String(bytes, EnvConfig.GLOBAL_CODING);
    }
}
