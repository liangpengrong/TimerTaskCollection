package com.my.timertask.annotation.quartz_handler;

import com.my.timertask.util.quartz.QuartzJobFactory;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.triggers.CoreTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangpr
 * @date 2019/06/19
 */
public class QuartzJobService implements Job, ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(QuartzJobService.class);
    private static ApplicationContext applicationContext = null;
    private static Map<JobKey, QuartzJobHelper> jobLocalData = new HashMap<>();
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String key = QuartzJobFactory.JOB_DATA_MAP_KET;
        // 获取ApplicationContext
        try {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            // 获取当前定时任务执行的job
            if(jobDataMap != null && jobDataMap.containsKey(key)) {
                // 获取job对象中保存对应的实体类
                Object obj = jobDataMap.get(key);
                if(obj instanceof QuartzJobHelper) {
                    QuartzJobHelper helper = (QuartzJobHelper) obj;
                    // 获取ApplicationContext
                    applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
                    if(applicationContext == null) {
                        exceptionScheduler(helper, "执行调度任务:"+helper.getName()+"时发生错误，无法获取ApplicationContext", new NullPointerException());
                        return;
                    }
                    JobKey jobKey = context.getTrigger().getJobKey();
                    QuartzJobHelper copyHelper = new QuartzJobHelper();
                    BeanUtils.copyProperties(helper, copyHelper);
                    jobLocalData.put(jobKey, copyHelper);
                    // 执行验证
                    doIsCheck(jobKey);
                }
            }
        } catch (Exception e) {
            logger.error("执行调度任务时发生错误", e);
        }
    }
    /**
     * 判断是否执行定时任务并执行相应的否操作
     */
    private void doIsCheck(JobKey jobKey) throws Exception {
        QuartzJobHelper helper = jobLocalData.size() > 0 && jobLocalData.containsKey(jobKey)? jobLocalData.get(jobKey) : null;
        // 判断是否初始化时需要延迟执行
        if(helper.getInitialDelay() != null && helper.getInitialDelay() > 0) {
            long time = helper.getInitialDelay();
            helper.setInitialDelay(null);
            exceptionScheduler(helper, "调度任务:"+helper.getName()+"初始化时已延迟执行"+time+"毫秒", null);
            Thread.sleep(time);
        }
        // 获取方法所属的java bean
        Object bean = applicationContext.getBean(helper.getBeanName());
        if(bean != null) {
            Method method = bean.getClass().getMethod(helper.getMethodName(), new Class[]{});
            try {
                method.invoke(bean, new Object[]{});// 执行
            }catch (Exception e){
                exceptionScheduler(helper, "执行调度任务:"+helper.getName()+"时发生错误", e);
            }
        }else {

        }
    }
    // 异常调度器
    private void exceptionScheduler(QuartzJobHelper h, String mess, Exception e) {
        logger.error(mess, e);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
