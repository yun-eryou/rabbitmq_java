package com.example.rabbitmq.three;


import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//这是发送者
public class Task2 {

    public static final String TASK_QUEUE_NAME="ack_queue";


    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
//        开启发布确认
        channel.confirmSelect();

//声明队列
        boolean durable=true;//需要持久化
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
        Scanner in=new Scanner(System.in);
        while (in.hasNext()){
            String message=in.next();
//            设置生产者发送消息为持久化消息(保存到磁盘中）
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN ,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息"+message);


        }
    }


}
