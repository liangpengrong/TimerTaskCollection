package com.my.timertask.service.inter;

import com.my.timertask.entity.po.TimedSendEmailPO;
import com.my.timertask.entity.vo.TimedSendEmailVo;
import com.my.timertask.util.enumdata.EmailTypeEnum;

public interface TimedSendEmailServiceI {
    
    /**
    * 创建发送邮件定时任务
    * @param to  - 收件人
    * @param subject - 标题
    * @param content - 内容
    * @return - 是否发送成功
    * @throws Exception
    */
    boolean addTimedSendSimpleEmail(final TimedSendEmailVo vo, EmailTypeEnum mailType);
}
