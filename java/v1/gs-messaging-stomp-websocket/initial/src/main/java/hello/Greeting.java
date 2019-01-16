package hello;

/**
 * @Description: Server 返回客户端的对象
 * @Warning:
 * @Author: dreamli
 * @Package: initial - hello.Greeting.java
 * @Date: Jan 17, 2019 12:12:48 AM
 * @Version: 1.0.0
 */
public class Greeting {
	private String content;

	public Greeting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Greeting(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Greeting [content=" + content + "]";
	}

}
