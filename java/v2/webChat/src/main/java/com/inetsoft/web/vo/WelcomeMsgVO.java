package com.inetsoft.web.vo;

import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 当 Socket 连接成功后发送欢迎词和当前在线的所有用户名
 * @author Administrator
 *
 */
public class WelcomeMsgVO extends MessageVO {

	private Set<String> names;

	public WelcomeMsgVO() {
		super(WELCOME_TYPE);
	}

	public WelcomeMsgVO(String msg, Set<String> names) {
		super(msg, WELCOME_TYPE);
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
