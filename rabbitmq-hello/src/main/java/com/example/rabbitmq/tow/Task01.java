package com.example.rabbitmq.tow;

import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class Task01 {
//指定的队列名称
    public static final String QUEUE_NAME="hello";
//发送大量消息
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        //    创建一个队列
//    第一个参数是队列名称，第二个参数是消息是否需要持久化（默认存放在内存中）
//    第三个参数是该队列是否只供一个消费者进行消费，是否进行消息的共享，如果是true可以进行多个消费者消费，false只能为1个（默认）
//    第四个参数，最后一个消费者断开以后，该队列是否进行删除，true删除，false不删除
//    其他参数：
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //从控制台中接收信息来发送
        Scanner in=new Scanner(System.in);

        while (in.hasNext()){
            String message=in.next();
 //    参数1，表示发送到哪一个交换机，第二个参数表示路由的key值是哪个
//    参数3，其他参数信息，参数4，是发送消息的消息体(必须调取二进制才能发消息）
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成:"+message);
        }
    }
}
