package com.example.rabbitmq.eight;

import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

//死信队列测试
public class Consumer01 {

//普通交换机的名称
    public static final String NORMAL_EXCHANGE="normal_exchange";

//    死信交换机的名称
    public static final String DEAD_EXCHANGE = "dead_exchange";
//  普通队列的名称
public static final String NORMAL_QUEUE="normal_exchange";
//死信队列的名称
public static final String DEAD_QUEUE="dead_exchange";

    public static void main(String[] args) throws Exception {


        Channel channel = RabbitMqUtils.getChannel();

//        声明死信交换机，和普通交换机，类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE,"direct");
        channel.exchangeDeclare(DEAD_EXCHANGE,"direct");
//        声明普通队列
        Map<String,Object>map=new HashMap<>();
//        设置过期时间
//        map.put("x-message-ttl",100000); 由生产者设置
//           正常队列要设置死信交换机
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE);
//        设置死信的routingkey
        map.put("x-dead-letter-routing-key","lisi");


        channel.queueDeclare(NORMAL_QUEUE,false,false,false,map);
        //        声明死信队列
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

//        绑定普通的交换机与普通队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
//        绑定死信的交换机和死信队列
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        System.out.println("等待接收消息");
        DeliverCallback deliverCallback=(var1 ,var2)->{
            String s = new String(var2.getBody());
            if (s.equals("info5")){
                System.out.println("这个消息是被拒绝的"+s);
                channel.basicReject(var2.getEnvelope().getDeliveryTag(),false);
            }else{
                channel.basicAck(var2.getEnvelope().getDeliveryTag(),false);
                System.out.println("consumer01接收的消息   "+new String(var2.getBody(),"utf-8"));
            }

        };
        //开启手动应答
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,(var1)->{});
    }

}
