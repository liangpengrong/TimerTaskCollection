package com.my.timertask.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.my.timertask.dao.TimedSendEmailDaoI;
import com.my.timertask.entity.dto.SendEmailDTO;
import com.my.timertask.entity.po.TimedSendEmailPO;
import com.my.timertask.entity.po.TimedTaskPO;
import com.my.timertask.entity.vo.TimedSendEmailVo;
import com.my.timertask.service.inter.TimedSendEmailServiceI;
import com.my.timertask.util.email.EmailTypeEnum;
import com.my.timertask.util.email.SendEmailServiceUtils;
import com.my.timertask.util.enumdata.TimedSysGroupEnum;
import com.my.timertask.util.quartzutils.QuartzJobServiceUtils;
import com.my.timertask.util.quartzutils.QuartzJobStatusEnum;
import com.my.timertask.util.rabbitmq.RabbitMQSendServiceUtils;

@Service
public class TimedSendEmailServiceImpl implements TimedSendEmailServiceI {
    private static Logger logger = LoggerFactory.getLogger(TimedSendEmailServiceImpl.class);
    @Value("${mail.default-encoding}")
    private String mailencoding;
    @Value("${mq.config.exchanger.log-exchanger}")
    private String key1;
    @Value("${mq.config.queue.log-info-routingkey}")
    private String routingkey1;
    @Value("${mq.config.queue.log-error-routingkey}")
    private String routingkey2;
    @Autowired
    private ApplicationContext appContent;
    @Autowired
    private TimedSendEmailDaoI timedSendEmainDaoI;
    /**
    * 发送简单邮件
    * @param to  - 收件人
    * @param subject - 标题
    * @param content - 内容
    * @return - 是否发送成功
    * @throws Exception
    */
    @Override
    public boolean addTimedSendSimpleEmail(final TimedSendEmailVo vo, EmailTypeEnum mailType) {
        
        boolean issend = false;
        TimedSendEmailPO po = new TimedSendEmailPO();
        try {
            String time = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());
            po.setHost(vo.getHost());
            po.setUsername(vo.getUsername());
            po.setWarrantCode(vo.getWarrantCode());
            po.setPort(vo.getPort());
            po.setProtocol(vo.getProtocol());
            po.setToUser(vo.getToUser());
            po.seteTitle(vo.geteTitle());
            po.seteContent(vo.geteContent());
            po.seteType(mailType.getKey());
            po.seteAnnex(vo.getAnnex());
            po.setCreateDate(time);
            po.setCreateUserId("10001");
            po.setName(vo.getTimdeName());
            po.setGroup(vo.getGroup());
            po.setSysGroup(TimedSysGroupEnum.发送邮件.getKey());
            po.setStartDate(vo.getStartDate());
            po.setStopDate(vo.getStopDate());
            po.setCron(vo.getCron());
            po.setModifyDate(null);
            po.setModifyUserId(null);
            // 保存到配置表中
            // int count = timedSendEmainDaoI.addOneTimedSendEmail(po);
            // 加入到定时任务表中并判断是否启动
            /*if(count > 0 ) {
                issend = addEmailJobs(po, vo.getRun(), vo.getSelfRun());
            }*/
            // 放入消息队列
            if(true) {
                while (true) {
                    Thread.sleep(1000);
                    TimedSendEmailPO ppp1 = new TimedSendEmailPO(), ppp2 = new TimedSendEmailPO();
                    BeanUtils.copyProperties(po, ppp1);
                    BeanUtils.copyProperties(po, ppp2);
                    boolean send = RabbitMQSendServiceUtils.send(key1, "", ppp1);
                }
                //boolean send2 = RabbitMQSendServiceUtils.send(key1, routingkey2, ppp2);
            }
        } catch (Exception e) {
            logger.error("", e);
            e.printStackTrace();
        }
        return issend; 
    }
    private boolean addEmailJobs(final TimedSendEmailPO mail, boolean run, boolean selfRun) throws Exception {
        boolean retBool = false;
        String time = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());
        TimedTaskPO taskPo = new TimedTaskPO();
        taskPo.setName(mail.getName());
        taskPo.setGroup(mail.getGroup());
        taskPo.setSysGroup(TimedSysGroupEnum.发送邮件.getKey());
        taskPo.setCron(mail.getCron());
        taskPo.setJobStatus(QuartzJobStatusEnum.可用.getKey().toString());
        taskPo.setMethodName("sendEmail");
        taskPo.setMethodDataType(String.class.getName());
        taskPo.setMethodData(mail.getId().toString());
        taskPo.setStartDate(mail.getStartDate());
        taskPo.setStopDate(mail.getStopDate());
        String[] bean = appContent.getBeanNamesForType(TimedSendEmailServiceImpl.class);
        taskPo.setBeanName(bean != null && bean.length >= 1?bean[0] : null);
        taskPo.setBeanPath(this.getClass().getResource("/").getPath());
        if(selfRun) {
            taskPo.setSelfStart("1");
        }else {
            taskPo.setSelfStart("0");
        }
        taskPo.setCreateDate(time);
        // 加入到定时任务表中并启动
        retBool = QuartzJobServiceUtils.addJobsToScheduler(new TimedTaskPO[] {taskPo}, new boolean[] {run})[0];
        return retBool;
    }
    public void sendEmail(String mailId) {
        try {
            logger.info("传入的邮件id为："+mailId);
            if(mailId != null && StringUtils.isNumeric(mailId)) {
                TimedSendEmailPO ppo = new TimedSendEmailPO();
                ppo.setId(Integer.parseInt(mailId));
                // 根据id查询邮件定时任务的表
                List<TimedSendEmailPO> listTimedSendEmail = timedSendEmainDaoI.listTimedSendEmail(ppo);
                if(listTimedSendEmail != null && listTimedSendEmail.size() > 0) {
                    ppo = listTimedSendEmail.get(0);
                    // 根据表中的信息组装邮件下发实体类
                    SendEmailDTO dtomail = new SendEmailDTO(ppo.getHost(),ppo.getUsername()
                            ,ppo.getToUser(),ppo.geteTitle(),ppo.geteContent(),ppo.getWarrantCode()
                            ,mailencoding,ppo.getPort(),ppo.getProtocol(),ppo.geteType(),ppo.geteAnnex(),null,null);
                    // 判断为普通类型的邮件
                    
                    if(EmailTypeEnum.普通类型邮件.getKey().equals(ppo.geteType())) {
                            SendEmailServiceUtils.sendSimpleEmail(dtomail);
                            logger.info("邮件id为："+mailId + ppo.getUsername()+"要发送给"+ppo.getToUser()+"的普通邮件发送成功");
                    }else if (EmailTypeEnum.网页类型邮件.getKey().equals(ppo.geteType())) {
                        SendEmailServiceUtils.sendHtmlEmail(dtomail);
                        logger.info("邮件id为："+mailId + ppo.getUsername()+"要发送给"+ppo.getToUser()+"的网页邮件发送成功");
                    }else if (EmailTypeEnum.带附件的邮件.getKey().equals(ppo.geteType())) {
                        SendEmailServiceUtils.sendAttachmentsEmail(dtomail);
                        logger.info("邮件id为："+mailId + ppo.getUsername()+"要发送给"+ppo.getToUser()+"的带附件的邮件发送成功");
                    }
                    
                }
            }else {
                logger.info("传入的邮件id为null，无法执行发送邮件");
            }
        } catch (Exception e) {
            logger.error("id为"+mailId+"的邮件发送失败", e);
        }
        
    }
}
