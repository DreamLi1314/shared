package hello;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	@MessageMapping("/hello") // 指定映射地址, 前端发送 message 时全地址为 prefix + mapping
	@SendTo("/topic/greetings") // 指定订阅地址, 该方法返回的数据所有订阅该地址的客户端都会收到
	public Greeting greeting(HelloMessage helloMessage) throws Exception {
		Thread.sleep(1000); // 模拟 Server 处理业务逻辑所需要的时间
		System.out.println("receiving:" + helloMessage);
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(helloMessage.getName()));
	}
	
}
