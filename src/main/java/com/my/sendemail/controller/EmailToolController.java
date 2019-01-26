package com.my.sendemail.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.my.sendemail.config.QuartzConfug;
import com.my.sendemail.service.inter.EmailServiceI;

@Controller
public class EmailToolController {
    private static Logger logger = LoggerFactory.getLogger(EmailToolController.class);
    @Resource(name="emailServiceImpl")
    private EmailServiceI emailServiceI; 
    public void sendSimpleMail(){
        StringBuilder content = new StringBuilder();
        for(int i=0;i<1000;i++) {
            content.append("干嘛呢干嘛呢干嘛呢");
        }
        emailServiceI.sendSimpleEmail("1985928228@qq.com", "王攀，你在干嘛呢说话", content.toString());
        System.out.println("执行了发邮件的方法,邮件内容："+content.toString());
    }
    @Test
    public void mainText() {
        EmailToolController emailTool = new EmailToolController();
        emailTool.sendSimpleMail();
    }
}