package com.my.sendemail.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendEmailServiceUtils {
    private static JavaMailSender mailSender;
    @Autowired
    public SendEmailServiceUtils(JavaMailSender mailSender) {
        SendEmailServiceUtils.mailSender = mailSender;
    }
    /** <blockquote>
    * 发送一条普通的邮件
    * @param to - 收件人
    * @param from - 发件人
    * @param subject - 主题
    * @param content - 内容
    * @throws Exception
    */  
    public static void sendSimpleEmail(String to, String from, String subject, String content) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();//创建简单邮件消息
        message.setFrom(from);//设置发送人
        message.setTo(to);//设置收件人
        message.setSubject(subject);//设置主题
        message.setText(content);//设置内容
        try {
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
    public static void sendHtmlEmail(String to, String from, String subject, String content) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();//创建一个MINE消息
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
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
    public static void sendAttachmentsEmail(String to, String from, String subject, String content, String filePath) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();//创建一个MINE消息
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);// true表示这个邮件是有附件的
            FileSystemResource file = new FileSystemResource(new File(filePath));//创建文件系统资源
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
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
    public static void sendInlineResourceEmail(String to, String from, String subject, String content, String rscPath, String rscId) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            helper.addInline(rscId, res);//添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现
            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        }

    }
}
