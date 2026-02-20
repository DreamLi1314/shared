package com.inetsoft.web.vo;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageVO implements Serializable {

	protected String msg;
	private int type;
	
	// connection chat type. open/close
	public static final int CONNECTION_CHAT_TYPE = 1;
	// single chat type 
	public static final int SINGLE_CHAT_TYPE = 2;
	// group chat type
	public static final int GROUP_CHAT_TYPE = 4;
	
	public MessageVO(String msg, int type) {
		super();
		this.msg = msg;
		this.type = type;
	}
	public MessageVO(int type) {
		super();
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MessageVO [msg=" + msg + ", type=" + type + "]";
	}

	/**
	 * 当当前对象转化为 json 字符串.
	 * 
	 * @return
	 */
	public String toJson() {
		return gson.toJson(this);
	}
	
	public static Gson gson = new GsonBuilder()
			.disableHtmlEscaping() //防止特殊字符出现乱码
			.create();
}
