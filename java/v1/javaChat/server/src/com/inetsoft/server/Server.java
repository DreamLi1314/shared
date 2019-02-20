package com.inetsoft.server;

import com.inetsoft.global.EnvConfig;
import com.inetsoft.runtime.RuntimeConfig;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class Server {

    /**
     * start up this server
     * @throws Exception
     */
    public void serverStart(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server has start up...");
        RuntimeConfig runtimeEnv = RuntimeConfig.getInstance();

        while (runtimeEnv.isServerRunning()) {
            Socket socket = serverSocket.accept();

            String name = UUID.randomUUID().toString();

            sockets.put(name, socket);
            sayHello(socket.getOutputStream(), name);

            Predicate<Socket> serverPredicate = (client) -> client.isConnected() && client != socket;

            // 向其他用户发送当前用户的上线通知
            broadcast(name + " is login...\n", serverPredicate);

            Runnable runnable = () -> {
              try {
                  InputStream in = socket.getInputStream();

                  while(runtimeEnv.isServerRunning()) {
                      String msg = EnvConfig.readContent(in);

                      if (EnvConfig.CONNECTION_LOST_FLAG.equals(msg)) {
                          // connection has close
                          sockets.remove(name);
                          return;
                      }

                      if(EnvConfig.LOGOUT_COMMAND.equalsIgnoreCase(msg.trim())) {
                          // log out
                          sockets.remove(name);
                          EnvConfig.writeContent(socket.getOutputStream(), EnvConfig.LOGOUT_COMMAND);
                          broadcast(name + " has log out..\n", serverPredicate);
                          return;
                      }

                      if(msg.indexOf(EnvConfig.MSG_SPLIT_TOKEN) < 0) {
                          // 这是一条广播信息
                          broadcast(" [from " + name + "]: " + msg, serverPredicate);
                      }
                      else {
                          // 向特定的用户发送消息
                          String[] content = msg.split(EnvConfig.MSG_SPLIT_TOKEN);
                          if(content.length != 2) {
                              EnvConfig.log(this.getClass(), "Message split error!");
                              continue;
                          }
                          String clientName = content[0];
                          String message = content[1];
                          Socket targetClient = sockets.get(clientName);

                          if(targetClient == null) {
                              EnvConfig.log(this.getClass(), "Target user isn't exist or lose connection.");
                              continue;
                          }

                          OutputStream out = targetClient.getOutputStream();

                          EnvConfig.writeContent(out, name + " tells you:" + message);
                      }

                  }
              }
              catch (Exception ignore) {
                  ignore.printStackTrace();
              }
            };

            new Thread(runnable).start();
        }

    }

    private void sayHello(OutputStream out, String name) throws Exception {
        EnvConfig.writeContent(out, "Hello, "+ name + "\n");
    }

    private void broadcast(String msg, Predicate<Socket> predicate) {
        final String message = "[Broadcast]:" + msg;
        sockets.values().stream()
                .filter((client) -> predicate.test(client))
                .forEach((client) ->
                {
                    try {
                        OutputStream out = client.getOutputStream();
                        EnvConfig.writeContent(out, message);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    // all socket of connected
    private Map<String, Socket> sockets = new ConcurrentHashMap<>(10);
}
