package com.geoviswtx.service;

import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@ServerEndpoint("/websocket/{userId}")
public class DispatcherMessageService {
    private Session session;
    private static CopyOnWriteArraySet<DispatcherMessageService> messages = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionPool = new HashMap<>();

    public DispatcherMessageService() {
        System.out.println("init...");
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        this.session = session;
        messages.add(this);
        sessionPool.put(userId, session);
    }

    @OnClose
    public void onClose() {
        messages.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public static void sendAllMessage(String mes) {
        for (DispatcherMessageService message : messages) {
            if(message.session.isOpen()) {
                message.session.getAsyncRemote().sendText(mes);
            }
        }
    }

    public static void sendOneMessage(String userId, String msg) {
        Session session = sessionPool.get(userId);

        if(session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(msg);
        }
    }

    public void sendManyMessage(String[] userIds, String msg) {
        for (String userId : userIds) {
            sendOneMessage(userId, msg);
        }
    }
}
