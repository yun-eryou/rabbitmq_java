package com.example.rabbitmq.eight;

import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

//死信队列 生产者
public class Producer {

    //普通交换机的名称
    public static final String NORMAL_EXCHANGE="normal_exchange";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

//        AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().expiration("10000").build();

//        发一个死信消息,设置ttl的时间
        for (int i = 0; i < 10; i++) {
            String message="info"+i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
