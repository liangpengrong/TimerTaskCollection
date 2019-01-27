package com.my.sendemail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.my.sendemail.service.inter.EmailServiceI;
import com.my.sendemail.util.SendEmailServiceUtils;

@Service
public class EmailServiceImpl implements EmailServiceI {
    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Value("${mail.username}")
    private String from;
    
    /**
    * 发送简单邮件
    * @param to  - 收件人
    * @param subject - 标题
    * @param content - 内容
    * @return - 是否发送成功
    * @throws Exception
    */
    @Override
    public boolean sendSimpleEmail(String to, String subject, String content){
        boolean issend = false;
        try {
            // 发送邮件
            SendEmailServiceUtils.sendSimpleEmail(to, from, subject, content);
            // 无异常则发送成功
            issend = true;
        } catch (Exception e) {
            logger.error("邮件发送失败，发件人："+from+"  收件人："+to+"  发送内容："+content, e);
            issend = false;
        }
       return issend; 
    }
}
