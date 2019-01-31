package com.my.timertask.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.Getter;
import lombok.Setter;
/** <blockquote>
* 发邮件服务配置
* @author [liangpr]
* @date [2019-01-23 11:15:37]
*/
@Configuration
public class JavaMailSenderConfig {
    private static Logger logger = LoggerFactory.getLogger(JavaMailSenderConfig.class);
    public static final String JAVA_MAIL_SENDERCONFIG_BEAN_NAME = "javaMailSender";
    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.default-encoding}")
    private String defaultEncoding;
    @Value("${mail.port}")
    private int port;
    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.properties.mail.smtp.auth}")
    private String mail_smtp_auth;
    @Value("${mail.properties.mail.smtp.ssl.enable}")
    private String mail_smtp_ssl_enable;
    @Bean(value=JAVA_MAIL_SENDERCONFIG_BEAN_NAME)
    public JavaMailSenderImpl initJavaMailSender() {
        JavaMailSenderImpl mailSender = null;
        try {
            mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setUsername(username);
            mailSender.setPassword(password);
            mailSender.setDefaultEncoding(defaultEncoding);
            mailSender.setPort(port);
            mailSender.setProtocol(protocol);
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.auth", mail_smtp_auth);
            properties.setProperty("mail.smtp.ssl.enable", mail_smtp_ssl_enable);
            mailSender.setJavaMailProperties(properties);
            logger.info("=========================================JavaMailSenderImpl初始化完成=========================================");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=========================================JavaMailSenderImpl初始化失败", e);
        }
        return mailSender;
    }
}
