package com.my.timertask.service.inter;

public interface EmailServiceI {
    
    /**
    * 发送简单邮件
    * @param to  - 收件人
    * @param subject - 标题
    * @param content - 内容
    * @return - 是否发送成功
    * @throws Exception
    */
    public boolean sendSimpleEmail(String to, String subject, String content);
}
