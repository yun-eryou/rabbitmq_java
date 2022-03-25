package com.example.rabbitmq.three;

import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

//这是消息消费端
public class Worker03 {
    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {


        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待接收,时间1秒");


        DeliverCallback deliverCallback=(var1,var2)->{
//            睡眠1秒
            SleepUtils.sleep(1);
             String temp=new String(var2.getBody(),"utf-8");
            System.out.println("接收到的消息"+temp);
//            手动应答的代码
//            第一个参数：消息的标记tag
//            第二个参数：是否批量应答 false不批量，true表示批量
            channel.basicAck(var2.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback cancelCallback=(var1)->{
            System.out.println("消费者取消接口回调逻辑"+var1);
        };
        int prefetchCount=2;//设置预取值，预取值是2
        channel.basicQos(prefetchCount);
//        采用手动应答
        boolean aotuAck=false;
        channel.basicConsume(TASK_QUEUE_NAME,aotuAck,deliverCallback,cancelCallback);

    }
}
