package com.my.sendemail.util.quartzutils;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.my.sendemail.po.TimedTaskPo;
import com.my.sendemail.service.inter.ScheduleJobServiceI;

/** <blockquote>
* 定时任务调度类
* @author [liangpr]
* @date [2019-01-18 09:16:11]
*/
public class QuartzJobFactory implements Job{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String key = QuartzJobServiceUtils.JOB_DATA_MAP_KET;
        //获取JobExecutionContext中的service对象    
        try {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            // 获取当前定时任务执行的job
            if(jobDataMap != null && jobDataMap.containsKey(key)) {
                // 获取job对象中保存对应表的实体类
                Object obj = jobDataMap.get(key);
                if(obj != null && obj instanceof TimedTaskPo) {
                    TimedTaskPo po = (TimedTaskPo) obj;
                    if(StringUtils.isNumeric(po.getOperaStatus()) && QuartzOperatingStatusEnum.立即运行.getKey() == Integer.parseInt(po.getOperaStatus())
                            && StringUtils.isNumeric(po.getJobStatus()) && QuartzJobStatusEnum.可用.getKey() == Integer.parseInt(po.getJobStatus())) {
                        //获取Spring中的上下文
                        ApplicationContext appCtx = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
                        if(appCtx != null && po.getName() != null && po.getMethodName() != null) {
                            Object bean = appCtx.getBean(po.getBeanName());
                            Method method = bean.getClass().getMethod(po.getMethodName(), null);
                            method.invoke(bean, new Object[] {});
                        }else {
                            logger.error("无法获取JobExecutionContext对象中保存的Spring上下文环境，无法执行定时任务");
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            logger.error("定时任务执行失败",e);
        }
    }

}
