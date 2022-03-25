package com.example.rabbitmq.four;


import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

//验证发布确认
/*1，单个确认模式 使用的时间 比较哪种是比较好的
2，批量确认模式
3，异步批量确认模式

* */
public class ConfirmMessage  {

//    发消息的个数
    public static final int MESSAGE_COUNT=1000;

    public static void main(String[] args) throws Exception {
//        单个确认
//        ConfirmMessage.publishMessageIndividually();

//        批量确认
//        ConfirmMessage.publishMessageBatch();

//        异步确认
        ConfirmMessage.publishMessageAsync();
    }

    public static void publishMessageIndividually() throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
//        队列的声明
        String temp= UUID.randomUUID().toString();

        channel.queueDeclare(temp,false,false,false,null);
//开启发布确认
        channel.confirmSelect();
//        记录开始时间

        long l = System.currentTimeMillis();
//        批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message=""+i;
            channel.basicPublish("",temp,null,message.getBytes());
//            单个消息就确认
            boolean b = channel.waitForConfirms();
            if (b){
                System.out.println("消息发送成功了");
            }
        }
//      结束时间
        long timeMillis = System.currentTimeMillis();
        System.out.println("使用了"+(timeMillis-l)+"毫秒");

    }

//    批量发布确认
    public static void publishMessageBatch() throws Exception {


        Channel channel = RabbitMqUtils.getChannel();
//        队列的声明
        String temp= UUID.randomUUID().toString();

        channel.queueDeclare(temp,false,false,false,null);
//开启发布确认
        channel.confirmSelect();
//        记录开始时间

        long l = System.currentTimeMillis();
//      批量确认消息的大小
        int batchSize=100;

//        批量发消息

        for (int i = 1; i <=MESSAGE_COUNT; i++) {
            String message=""+i;
            channel.basicPublish("",temp,null,message.getBytes());
            boolean b = channel.waitForConfirms();
            if (b){
                System.out.println("发布消息成功");
            }
            if (i%batchSize==0){ //每batchSize个大小确认一次
                channel.waitForConfirms();
            }
        }

//      结束时间
        long timeMillis = System.currentTimeMillis();
        System.out.println("使用了"+(timeMillis-l)+"毫秒");
    }

//    异步发布确认
    public  static void publishMessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
//        队列的声明
        String temp= UUID.randomUUID().toString();

        channel.queueDeclare(temp,false,false,false,null);
//开启发布确认
        channel.confirmSelect();

        /*线程安全有序的哈希表，适用与高并发的情况下
          1，轻松的将序号与消息进行关联
          2，轻松的批量删除条目，只要给到序号
          3，支持高并发（多线程）
        * */
       ConcurrentSkipListMap<Long,String>outstandingConfirms=new ConcurrentSkipListMap<>();

//            消息成功的回调函数
        ConfirmCallback ack=(long var1, boolean var3)->{
            System.out.println("确认消息的编号"+var1);
            if (var3){
//            2，删除掉已经确认的消息，剩下的就是未确认的消息
            ConcurrentNavigableMap<Long, String> confirmed =
                    outstandingConfirms.headMap(var1);
            confirmed.clear();
            }else{
                outstandingConfirms.remove(var1);
            }

        };
//        消息失败的回调函数
//        参数1：消息的标识 。参数2：是否为批量确认
        ConfirmCallback nack=(long var1, boolean var3)->{
//            3，打印未确认的消息都有哪些
            String s = outstandingConfirms.get(var1);


            System.out.println("未确认的消息"+s+"tag:"+var1);
        };
        //        消息的监听器 ,监听哪些消息成功了，哪些消息失败了
//      第一个参数：监听哪些消息成功了，第二个参数：监听哪些消息失败了
        channel.addConfirmListener(ack,nack);   //这是异步通知的

//        记录开始时间
        long l = System.currentTimeMillis();
//        批量发送
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message="消息"+i;
            channel.basicPublish("",temp,null,message.getBytes(StandardCharsets.UTF_8));

//         1，   此处记录下所有要发送消息的总和，
            outstandingConfirms.put(channel.getNextPublishSeqNo(),message);
            System.out.println("记录该消息"+message);
        }
        //      结束时间
        long timeMillis = System.currentTimeMillis();
        System.out.println("                          异步使用了"+(timeMillis-l)+"毫秒                  ");

    }
}
