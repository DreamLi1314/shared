package com.inetsoft.web.vo;

import java.util.Set;

/**
 * 当 Socket 连接成功后发送欢迎词和当前在线的所有用户名
 * @author Administrator
 *
 */
public class ConnectionMsgVO extends MessageVO {

	private Set<String> names;

	public ConnectionMsgVO() {
		super(CONNECTION_CHAT_TYPE);
	}

	public ConnectionMsgVO(String msg, Set<String> names) {
		super(msg, CONNECTION_CHAT_TYPE);
		this.names = names;
	}
	
	@Override
	public String toString() {
		return "WelcomeMsgVO [names=" + names + ", msg=" + msg + "]";
	}

	@Override
	public String toJson() {
		return gson.toJson(this);
	}
}
