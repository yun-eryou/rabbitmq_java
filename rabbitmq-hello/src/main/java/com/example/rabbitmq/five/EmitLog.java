package com.example.rabbitmq.five;


import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//负责发消息
public class EmitLog {
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        Scanner in=new Scanner(System.in);
        while (in.hasNext()){
            String message=in.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息"+message);
        }
    }
}
