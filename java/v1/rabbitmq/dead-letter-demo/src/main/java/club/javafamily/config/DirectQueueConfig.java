package club.javafamily.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Li
 * @date 2023/6/8 上午9:39
 * @description
 */
@Configuration
public class DirectQueueConfig {

    /**
     * 普通交换机名称
     */
    public static final String X_EXCHANGE = "X";

    /**
     * 死信交换机名称
     */
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    /**
     * 普通队列名称
     */
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    /**
     * 死信队列名称
     */
    public static final String DEAD_LETTER_QUEUE = "QD";

    /**
     * 声明 XExchange
     */
    @Bean
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 声明 yExchange
     */
    @Bean
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明队列QA
     */
    @Bean
    public Queue queueA(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        // 设置死信路由键
        arguments.put("x-dead-letter-routing-key", "YD");
        // 设置过期时间
        arguments.put("x-message-ttl", 10000);
        return new Queue(QUEUE_A, true, false, false, arguments);
    }

    /**
     * 声明队列QB
     */
    @Bean
    public Queue queueB(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        // 设置死信路由键
        arguments.put("x-dead-letter-routing-key", "YD");
        // 设置过期时间
        arguments.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    /**
     * 死信队列QD
     */
    @Bean
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    /**
     * 绑定
     */
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA, @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindingX(){
        return new Binding(QUEUE_B, Binding.DestinationType.QUEUE, X_EXCHANGE, "XB", null);
    }

    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}


