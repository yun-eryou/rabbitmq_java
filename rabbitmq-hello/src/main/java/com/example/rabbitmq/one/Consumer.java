package com.example.rabbitmq.one;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者，是用来接收消息的
public class Consumer {
    //队列的名称
    public static final String QUEUE_NAME = "hello";

    public static final String FED_EXCHANGE = "fed_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
//        创建连接工厂
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
//        设置连接的ip地址
        factory.setHost("192.168.20.130");
//        设置账号以及密码
        factory.setPassword("123");
        factory.setUsername("admin");
//        创建一个新的连接
        Connection connection = factory.newConnection();
//创建一个信道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(FED_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("node2_queue",true,false,false,null);
        channel.queueBind("node2_queue",FED_EXCHANGE,"routingKey");

//        声明接收消息
        com.rabbitmq.client.DeliverCallback deliverCallback=(consumerTaf, message)->{
            new String(message.getBody());
            System.out.println(message);
        };
//        取消消息的回调
        com.rabbitmq.client.CancelCallback cancelCallback= consumerTag->{
            System.out.println("消息消费被中断");
        };

//        接收消息
//        第一个参数，消费哪一个队列， 第二个参数，消费成功之后是否要自动答，true自动答，false手动答
//        第三个参数，消费者未成功消费的回调，第四个参数，消费者取消消费时的回调
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
