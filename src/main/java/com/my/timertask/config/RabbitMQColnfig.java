package com.my.timertask.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQColnfig {
    /**
    * 发送消息的确认回调
    * @return
    */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }
    @Bean
    public MsgSendReturnstCallBack msgSendReturnstCallBack(){
        return new MsgSendReturnstCallBack();
    }
    
    /**
    * 生产者的消息确认回调，用来当生产者的消息发送失败时记录日志<br>
    * 说明：
    * 如果消息没有到exchange,则confirm回调,ack=false
    * 如果消息到达exchange,则confirm回调,ack=true
    * exchange到queue成功,则不回调return
    * exchange到queue失败,则回调return
    * @author [liangpr]
    * @date [2019-04-02 18:29:27]
    */
    public class MsgSendConfirmCallBack implements ConfirmCallback{
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            // 发送失败时候
            if(!ack) {
                System.out.println("CallBackConfirm 消息消费失败！");
                System.out.println("CallBackConfirm Cause: " + cause);
            }
        }
    }
    /**
     * 生产者的消息确认回调，用来当生产者的消息发送失败时记录日志<br>
     * 说明：
     * 如果消息没有到exchange,则confirm回调,ack=false
     * 如果消息到达exchange,则confirm回调,ack=true
     * exchange到queue成功,则不回调return
     * exchange到queue失败,则回调return
     * @author [liangpr]
     * @date [2019-04-02 18:29:27]
     */
     public class MsgSendReturnstCallBack implements ReturnCallback{
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey){
            System.out.println("return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
        }
     }
}