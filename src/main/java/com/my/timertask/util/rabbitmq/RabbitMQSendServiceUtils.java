package com.my.timertask.util.rabbitmq;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.my.timertask.config.RabbitMQColnfig.MsgSendConfirmCallBack;
import com.my.timertask.config.RabbitMQColnfig.MsgSendReturnstCallBack;
import com.my.timertask.util.bean.BeanFactoryUtils;

public class RabbitMQSendServiceUtils {
    private final static Logger logger = LoggerFactory.getLogger(RabbitMQSendServiceUtils.class);
    private RabbitMQSendServiceUtils () {};
    
    public static RabbitTemplate getRabbitTemplate() {
        RabbitTemplate bean = BeanFactoryUtils.getBean(RabbitTemplate.class);
        bean.setConfirmCallback(BeanFactoryUtils.getBean(MsgSendConfirmCallBack.class));
        bean.setReturnCallback(BeanFactoryUtils.getBean(MsgSendReturnstCallBack.class));
        return bean;
    }
    /** <blockquote>
    * 向消息队列中发送消息
    * @param key1 - 分发消息的交换机名称
    * @param key2 - 用来匹配消息队列的路由Key
    * @param obj - 消息体
    * @return - 是否发送成功
    */
    public static boolean send(String key1, String key2, Object obj) {
        boolean retBool = false;
        try {
            RabbitTemplate rabbitTemplate = getRabbitTemplate();
            rabbitTemplate.convertAndSend(key1, key2, obj, new CorrelationData(new Date().getTime()+""));
            retBool = true;
        } catch (Exception e) {
            logger.error("向消息队列发送消息时失败，传入的参数为key1："+key1+"  key2："+key2+"  obj："+obj, e);
            retBool = false;
        }
        return retBool;
    }
}
