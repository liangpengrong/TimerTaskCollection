package com.my.timertask.util;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.my.timertask.entity.dto.SendEmailDTO;

@Component
public class SendEmailServiceUtils {
    private static JavaMailSenderImpl thisMailSender;
    private static JavaMailSenderImpl initJavaMailSender(final SendEmailDTO mail) {
        JavaMailSenderImpl mailSender = null;
        try {
            if(thisMailSender == null) {
                mailSender = new JavaMailSenderImpl();
            }else {
                mailSender = thisMailSender;
            }
            mailSender.setHost(mail.getHost());
            mailSender.setUsername(mail.getUsername());
            mailSender.setPassword(mail.getWarrantCode());
            mailSender.setDefaultEncoding(mail.getEncoding());
            mailSender.setPort(Integer.parseInt(mail.getPort()));
            mailSender.setProtocol(mail.getProtocol());
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.ssl.enable", "true");
            mailSender.setJavaMailProperties(properties);
            thisMailSender = mailSender;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailSender;
    }
    /** <blockquote>
    * 发送一条普通的邮件
    * @param to - 收件人
    * @param from - 发件人
    * @param subject - 主题
    * @param content - 内容
    * @throws Exception
    */  
    public static void sendSimpleEmail(final SendEmailDTO mail) throws Exception{
        try {
            SimpleMailMessage message = new SimpleMailMessage();//创建简单邮件消息
            JavaMailSenderImpl mailSender = initJavaMailSender(mail);
            
            message.setFrom(mail.getUsername());//设置发送人
            message.setTo(mail.getToUser());//设置收件人
            message.setSubject(mail.getTitle());//设置主题
            message.setText(mail.getContent());//设置内容
            mailSender.send(message);//执行发送邮件
        } catch (Exception e) {
            throw e;
        }
    }
    /** <blockquote>
    * 发送一条HTTP的邮件
    * @param to - 收件人
    * @param from - 发件人
    * @param subject - 主题
    * @param content - 内容
    * @throws Exception
    */  
    public static void sendHtmlEmail(final SendEmailDTO mail) throws Exception{
        try {
            JavaMailSenderImpl mailSender = initJavaMailSender(mail);
            MimeMessage message = mailSender.createMimeMessage();//创建一个MINE消息
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mail.getUsername());
            helper.setTo(mail.getToUser());
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getContent(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        }

    }
    /** <blockquote>
    * 发送一条带有附件的邮件
    * @param to - 收件人
    * @param from - 发件人
    * @param subject - 主题
    * @param content - 内容
    * @param filePath - 附件路径
    * @throws Exception
    */  
    public static void sendAttachmentsEmail(final SendEmailDTO mail) throws Exception{
        try {
            JavaMailSenderImpl mailSender = initJavaMailSender(mail);
            MimeMessage message = mailSender.createMimeMessage();//创建一个MINE消息
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mail.getUsername());
            helper.setTo(mail.getToUser());
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getContent(), true);// true表示这个邮件是有附件的
            FileSystemResource file = new FileSystemResource(new File(mail.getAnnex()));//创建文件系统资源
            String fileName = mail.getAnnex().substring(mail.getAnnex().lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);//添加附件
            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        }
    }
    /** <blockquote>
     * 发送一条嵌入静态资源的邮件
     * @param to - 收件人
     * @param from - 发件人
     * @param subject - 主题
     * @param content - 内容
     * @param rscPath - 静态资源路径
     * @param rscId - 静态资源ID
     * @throws Exception
     */  
    public static void sendInlineResourceEmail(final SendEmailDTO mail) throws Exception{
        try {
            JavaMailSenderImpl mailSender = initJavaMailSender(mail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mail.getUsername());
            helper.setTo(mail.getToUser());
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getContent(), true);
            FileSystemResource res = new FileSystemResource(new File(mail.getRscPath()));
            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            helper.addInline(mail.getRscId(), res);//添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现
            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        }

    }
}
