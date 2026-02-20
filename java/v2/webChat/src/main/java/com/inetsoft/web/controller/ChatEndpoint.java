package com.inetsoft.web.controller;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.inetsoft.web.vo.ConnectionMsgVO;
import com.inetsoft.web.vo.MessageVO;

/**
 * 多例, 每个客户端一个 @ServerEndpoint 实例
 * @author Administrator
 *
 */
@ServerEndpoint("/chat/{userName}")
public class ChatEndpoint {
	public String userName;
	public Session session;

	private static final Set<ChatEndpoint> sessions = new CopyOnWriteArraySet<>();
	private static int GUEST_NAME_INDEX = 0;

	/**
	 * 收到客户端发送的消息
	 * 
	 * @param session webSocket Session obj
	 * @param msg     客户端发送的消息
	 * @param last    是否是最新的
	 * @throws Exception
	 */
	@OnMessage
	public void receiveMsg(Session session, String msg, boolean last) throws Exception {
		if(!session.isOpen()) {
			return;
		}
		
		MessageVO message = MessageVO.gson.fromJson(msg, MessageVO.class);
		
		if(message.getType() == MessageVO.CONNECTION_CHAT_TYPE) {
			System.out.println(message.getMsg());
		}
		else if(message.getType() == MessageVO.GROUP_CHAT_TYPE) {
			broadcat(message.toJson());
		}
	}

	/**
	 * 连接建立
	 * 
	 * @param session webSocket Session obj
	 */
	@OnOpen
	public void open(Session session, @PathParam("userName") String userName) {
		if(!session.isOpen()) {
			return;
		}
		
		if (userName == null || userName.isEmpty()) {
			this.userName = "Guest" + GUEST_NAME_INDEX++;
		} else {
			this.userName = userName;
		}
		
		this.session = session;

		// 需要首先 Check 用户名的合法性以及是否冲突
		sessions.add(this);

		// 除了当前 Session 外所有打开的 Session 对象集合
//		Set<Session> openSessions = session.getOpenSessions();

		// 广播 WelcomeMsgVO
		ConnectionMsgVO msgVO = new ConnectionMsgVO("<div style='color: red'>Welcome to "+ this.userName + ".<div>", getSessions());
		broadcat(msgVO.toJson());
	}

	/**
	 * Get sessions of all opened.
	 * @return
	 */
	private Set<String> getSessions() {
		return sessions.stream().map((endPoint) -> endPoint.userName).collect(Collectors.toSet());
	}
	
	/**
	 * 用当前 Session 将 msg 广播出去
	 * 
	 * @param session 要发送广播的 Session
	 * @param msg     要广播的内容
	 */
	private void broadcat(String msg) {
		sessions.stream()
			.forEach((endPoint) -> {
				try {
					if(endPoint != null && endPoint.session.isOpen()) {
						synchronized (endPoint) {
							endPoint.session.getBasicRemote().sendText(msg);
						}
					}
					
				} catch (Exception e) {
					System.out.println("Connection lost...");
					sessions.remove(endPoint);
					try {
						endPoint.session.close();
					} catch (IOException ignore) {
					}
					
					broadcat(msg);
				}
		});
	}

	/**
	 * 连接关闭
	 * @param session webSocket Session obj
	 * @throws IOException 
	 */
	@OnClose
	public void close(Session session) throws IOException {
		System.out.println("====close connection===id=" + session.getId() + "==isOpen=" + session.isOpen());
		
		if(!session.isOpen()) {
			return;
		}
		
		sessions.remove(this);
		broadcat(new ConnectionMsgVO("<div style='color: red'>"+ this.userName + " has log out.....<div>", getSessions()).toJson());
	}
	
	/**
	 * 异常处理器
	 * @param session 发生异常的 Session
	 * @param e Exception
	 */
	@OnError
	public void errorHandle(Session session, Throwable e) {
		if(session.isOpen()) {
			try {
				session.close();
			} catch (IOException e1) {
			}
		}
	}
}
