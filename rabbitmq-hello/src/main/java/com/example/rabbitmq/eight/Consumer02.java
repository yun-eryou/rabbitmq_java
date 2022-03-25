package com.example.rabbitmq.eight;

import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

//死信队列测试
public class Consumer02 {

//普通交换机的名称

//死信队列的名称
public static final String DEAD_QUEUE="dead_exchange";

    public static void main(String[] args) throws Exception {


        Channel channel = RabbitMqUtils.getChannel();


        System.out.println("等待接收消息");
        DeliverCallback deliverCallback=(var1 ,var2)->{
            System.out.println("consumer02接收的消息   "+new String(var2.getBody(),"utf-8"));
        };

        channel.basicConsume(DEAD_QUEUE,true,deliverCallback,(var1)->{});
    }

}
