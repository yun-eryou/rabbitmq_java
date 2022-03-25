package com.example.rabbitmq.seven;


import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/*声明主题交换机，以及相关队列*/
public class ReceiveLogsTopic01 {


    public static final String EXCHANGE_NAME="topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

         channel.exchangeDeclare(EXCHANGE_NAME,"topic");
//         声明队列
        String queueName="Q1";
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,EXCHANGE_NAME,"*.orange.*");
        System.out.println("等待接收消息");
//        接收消息

        DeliverCallback deliverCallback=(var1,var2)->{
            String temp=new String(var2.getBody(),"utf-8");
            System.out.println("接收到的消息为"+temp+"绑定键"+ var2.getEnvelope().getRoutingKey()+"接收队列"+queueName);
        };
        channel.basicConsume(queueName,true,deliverCallback,var1->{});


    }

}
