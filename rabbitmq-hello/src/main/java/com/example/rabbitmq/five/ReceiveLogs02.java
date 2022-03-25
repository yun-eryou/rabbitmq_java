package com.example.rabbitmq.five;
//负责消息的接收

//消息1
import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogs02 {

//    交换机名称
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

//        声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
//        声明一个队列,临时队列。队列名称是随机的,消费者断开与队列连接之后，就自动删除了
        String queue = channel.queueDeclare().getQueue();
//        绑定交换机与队列
        channel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("等待接收消息，接收到的消息打印到屏幕上");

        DeliverCallback deliverCallback=(var1,var2)->{
          String temp=new String(var2.getBody(),"utf-8");
            System.out.println("02接收到的消息"+temp);
        };


        channel.basicConsume(queue,true,deliverCallback,var1->{});

    }
}
