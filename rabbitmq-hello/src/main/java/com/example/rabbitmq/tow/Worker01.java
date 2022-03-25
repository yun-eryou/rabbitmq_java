package com.example.rabbitmq.tow;


import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

//这是一个工作线程，相当于之间的消费者
public class Worker01 {
    public static final String QUEUE_NAME="hello";
    public static void main(String[] args) throws Exception {


//        接收消息
        Channel channel = RabbitMqUtils.getChannel();

//        接收到了消息，需要做什么，使用的时lambda表达式
        DeliverCallback deliverCallback=( var1,var2)->{
//            System.out.println(var2.getBody());
//            System.out.println("接收到的消息："+var2.getBody());
            String s = new String(var2.getBody());
            System.out.println("接收到的消息"+s);
        };
//      消息执行被取消时会执行下面的内容
        CancelCallback cancelCallback=(temp)->{
            System.out.println("消息被取消了"+temp);
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        System.out.println("c2等待接收消息 ");
    }
}
