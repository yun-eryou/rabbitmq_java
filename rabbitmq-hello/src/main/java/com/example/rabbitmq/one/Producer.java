package com.example.rabbitmq.one;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//这是生产者
public class Producer {
//队列名称
    public static final String QUEUE_NAME="hello";

//    开始发消息
public static void main(String[] args) throws IOException, TimeoutException {

//    创建工厂
    ConnectionFactory factory = new ConnectionFactory();

//    工厂ip连接，rabbitmq的队列
    factory.setHost("192.168.20.129");
//    设置用户名，以及密码
    factory.setUsername("admin");
    factory.setPassword("123");

//    创建连接
    Connection connection = factory.newConnection();

//    获取信道
    Channel channel = connection.createChannel();
//    创建一个队列
//    第一个参数是队列名称，第二个参数是消息是否需要持久化（默认存放在内存中）
//    第三个参数是该队列是否只供一个消费者进行消费，是否进行消息的共享，如果是true可以进行多个消费者消费，false只能为1个（默认）
//    第四个参数，最后一个消费者断开以后，该队列是否进行删除，true删除，false不删除
//    其他参数：
    channel.queueDeclare(QUEUE_NAME,false,false,false,null);
    String message="hello world";
//    参数1，表示发送到哪一个交换机，第二个参数表示路由的key值是哪个
//    参数3，其他参数信息，参数4，是发送消息的消息体(必须调取二进制才能发消息）
    channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
    System.out.println("消息发送完毕");

}
}
