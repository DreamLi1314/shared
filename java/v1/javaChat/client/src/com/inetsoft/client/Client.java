package com.inetsoft.client;

import com.inetsoft.global.EnvConfig;
import com.inetsoft.runtime.RuntimeConfig;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    /**
     * Connect to host:port server
     * @param host host name
     * @param port port
     */
    public void connect(String host, int port) throws Exception {
        Socket client = new Socket(host, port);
        RuntimeConfig runtimeConfig = RuntimeConfig.getInstance();

        System.out.println("Connected " + host + ":" + port);
        OutputStream out = client.getOutputStream();

        Runnable sendMsg = () -> {
            try {
                // 接受用户输入
                Scanner scanner = new Scanner(System.in);

                while (runtimeConfig.isServerRunning()) {
                    // 读取用户输入
                    String content = scanner.next();
                    EnvConfig.writeContent(out, content + "\n");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };

        // 接受 Server 的返回数据并将其打印输出
        Runnable receiveMsg = () -> {
            try {
                InputStream in = client.getInputStream();
                while(runtimeConfig.isServerRunning()) {
                    if(in.available() > 0) {
//                        in.transferTo(System.out);
                        String msg = EnvConfig.readContent(in);

                        if(EnvConfig.LOGOUT_COMMAND.equalsIgnoreCase(msg.trim())
                                || EnvConfig.CONNECTION_LOST_FLAG.equals(msg.trim())) {
                            System.exit(0);
                            return;
                        }

                        System.out.print(msg);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(sendMsg).start();
        new Thread(receiveMsg).start();
    }
}
