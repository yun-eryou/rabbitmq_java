package com.example.rabbitmq.six;

import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogsDirect02 {

    public static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
//        声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
//        声明一个队列名字
        channel.queueDeclare("disk",false,false,false,null);
//        多重绑定
        channel.queueBind("disk",EXCHANGE_NAME,"error");

        DeliverCallback deliverCallback=(var1, var2)->{
            String temp=new String(var2.getBody(),"utf-8");
            System.out.println("direct02打印接收到的消息"+temp);
        };


        channel.basicConsume("disk",true,deliverCallback,var1->{});
    }
}
