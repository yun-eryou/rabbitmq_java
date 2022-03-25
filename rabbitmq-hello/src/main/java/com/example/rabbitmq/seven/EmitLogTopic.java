package com.example.rabbitmq.seven;

import com.example.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//生产者
public class EmitLogTopic {
    public static final String EXCHANGE_NAME="topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        Map<String, String> bindingKeyMap = new HashMap<>();
        bindingKeyMap.put("quick.orange.rabbit","被队列 Q1Q2 接收到");
        bindingKeyMap.put("lazy.orange.elephant","被队列 Q1Q2 接收到");
        bindingKeyMap.put("quick.orange.fox","被队列 Q1 接收到");
        bindingKeyMap.put("lazy.brown.fox","被队列 Q2 接收到");
        bindingKeyMap.put("lazy.pink.rabbit","虽然满足两个绑定但只被队列 Q2 接收一次");
        bindingKeyMap.put("quick.brown.fox","不匹配任何绑定不会被任何队列接收到会被丢弃");
        bindingKeyMap.put("quick.orange.male.rabbit","是四个单词不匹配任何绑定会被丢弃");
        bindingKeyMap.put("lazy.orange.male.rabbit","是四个单词但匹配 Q2");

        for (Map.Entry<String, String> entry : bindingKeyMap.entrySet()) {


            String key = entry.getKey();
            String value = entry.getValue();

            channel.basicPublish(EXCHANGE_NAME,key,null,value.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息:"+value);
        }
    }
}
