package hello;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {

	@MessageMapping("/all") // 指定映射地址, 前端发送 message 时全地址为 prefix + mapping
	@SendTo("/topic/broadcast") // 指定订阅地址, 该方法返回的数据所有订阅该地址的客户端都会收到
	public Greeting chat(ChatMessage message) throws Exception {
		System.out.println("receiving:" + message);
		return new Greeting(message.getName() + ": "
			+ HtmlUtils.htmlEscape(message.getWords()));
	}

}
