package com.my.timertask.service.impl;

import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.my.timertask.entity.po.TimedSendEmailPO;
import com.rabbitmq.client.Channel;

@Service
public class LogTest {
    private volatile static int index = 0;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="${mq.config.queue.log-info}", durable="true"),
            exchange = @Exchange(value="${mq.config.exchanger.log-exchanger}",type=ExchangeTypes.FANOUT)
    ))
    public void aaa(TimedSendEmailPO po,Channel channel, Message message) throws Exception {
        if(index <= 10) {
            System.out.println("___"+po.toString());
            // 手动签收消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            index ++;
        }else {
            System.out.println("丢弃消息");
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            throw new NullPointerException("阿萨飒");
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="${mq.config.queue.log-error}"),
            exchange = @Exchange(value="${mq.config.exchanger.log-exchanger}",type=ExchangeTypes.FANOUT)
    ))
    public void aaa2(TimedSendEmailPO po,Channel channel, Message message) throws Exception {
        if(index <= 10) {
            System.out.println("****"+po.toString());
            // 手动签收消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            index ++;
        }else {
            // 丢弃消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            throw new NullPointerException("Z飒飒打撒");
        }
        
    }
}
