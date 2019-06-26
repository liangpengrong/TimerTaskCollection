package com.my.timertask.util.quartz;

import com.my.timertask.annotation.quartz_handler.QuartzJobHelper;
import com.my.timertask.annotation.quartz_handler.QuartzJobService;
import com.my.timertask.controller.EmailToolController;
import com.my.timertask.util.bean.BeanFactoryUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.*;

public class QuartzJobFactory {
    private QuartzJobFactory(){}
    private static Logger logger = LoggerFactory.getLogger(QuartzJobFactory.class);
    public static final String JOB_DATA_MAP_KET = "scheduleJob";
    public static final String JOB_GROUP_NAME = "ATAO_JOBGROUP";                    //任务组
    public static final String TRIGGER_GROUP_NAME = "ATAO_TRIGGERGROUP";            //触发器组
    private static ApplicationContext applicationContext;
    public static Scheduler scheduler;
    static 
    {
        scheduler = BeanFactoryUtils.getBean(Scheduler.class);
        // 获取ApplicationContext
        try {
            SchedulerContext schedulerContext = scheduler.getContext();
            Object o = schedulerContext.get("applicationContext");
            ApplicationContext applicationContext = o != null && o instanceof ApplicationContext? (ApplicationContext)o : null;
        }catch (Exception e) {
            logger.error("无法获取ApplicationContext", e);
        }
    }

    /**
     * 合并QuartzJobHelper 当传入的Helper中的属性有为空的时候就使用默认的配置
     */
    public static QuartzJobHelper combineJobHelper(final QuartzJobHelper h) {
        QuartzJobHelper defHelper = new QuartzJobHelper();
        if(h != null) {
            // 复制一份
            BeanUtils.copyProperties(h, defHelper);
            if(defHelper.getName() == null) throw new NullPointerException("传入的调度名称不能为NULL");
            if(defHelper.getGroup() == null) defHelper.setGroup(JOB_GROUP_NAME);
            if(defHelper.getTriggerGroupName() == null) defHelper.setTriggerGroupName(TRIGGER_GROUP_NAME);
            return defHelper;
        }else {
            return defHelper;
        }
    }
    /**
     * 获取所有计划中的任务
     * @throws Exception
     */
    public static List<QuartzJobHelper> getPlanJobs() {
        List<QuartzJobHelper> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<QuartzJobHelper>();
            for(JobKey jobKey  : jobKeys){
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers){
                    QuartzJobHelper job = (QuartzJobHelper) trigger.getJobDataMap().get(JOB_DATA_MAP_KET);
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setJobStatus(triggerState);
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCron(cronExpression);
                    }
                    jobList.add(job);
                }
            }
        }catch (Exception e){
            
        }
        return jobList;
    }
    /**
     * 获取所有运行中的任务
     */
    public static List<QuartzJobHelper> getRunningJobs() {
        List<QuartzJobHelper> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<QuartzJobHelper>();
            for (JobExecutionContext executingJob : executingJobs) {
                Trigger trigger = executingJob.getTrigger();
                QuartzJobHelper job = (QuartzJobHelper) trigger.getJobDataMap().get(JOB_DATA_MAP_KET);
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState);
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCron(cronExpression);
                }
                jobList.add(job);
            }
        }catch (Exception e){
            
        }
        return jobList;
    }

    /**
     * 暂停任务
     */
    public static boolean pauseJob(QuartzJobHelper scheduleJob) {
        boolean retBool = false;
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
            if(jobKey != null) {
                scheduler.pauseJob(jobKey);
                retBool = true;
            }
        }catch (Exception e){
            retBool = false;
        }
        return retBool;
    }

    /**
     * 恢复任务
     */
    public static boolean resumeJob(QuartzJobHelper scheduleJob) {
        boolean retBool = false;
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
            if(jobKey != null) {
                scheduler.resumeJob(jobKey);
                retBool = true;
            }
        }catch (Exception e) {
            retBool = false;
        }
        return retBool;
    }
    /**
     * 重新执行任务
     */
    public static boolean rescheduleJob(QuartzJobHelper scheduleJob) {
        boolean retBool = false;
        try {
            TriggerKey key = TriggerKey.triggerKey(scheduleJob.getName(),scheduleJob.getGroup());
            Trigger trigger = scheduler.getTrigger(key);
            if(trigger != null){
                //重新执行
                scheduler.rescheduleJob(key,trigger);
                retBool = true;
            }
        }catch (Exception e){
            retBool = false;
        }
        return retBool;
    }
    /**
     * 删除任务
     */
    public static boolean deleteJob(QuartzJobHelper scheduleJob) {
        boolean retBool = false;
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
            retBool = scheduler.deleteJob(jobKey);
        }catch (Exception e){
            retBool = false;
        }
        return retBool;
    }

    /**
     * 立即运行任务 ,只会运行一次
     */
    public static boolean runOnce(QuartzJobHelper scheduleJob) {
        boolean retBool = false;
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
            if(jobKey != null) {
                scheduler.triggerJob(jobKey);
                retBool = true;
            }
        }catch (Exception e){
            retBool = false;
        }
        return retBool;
        
    }
    /**
     * 更新任务的时间表达式
     * @param scheduleJob 要更新的任务实体类
     * @param expression 新的表达式
     */
    public static boolean updateExpression(QuartzJobHelper scheduleJob, String expression) {
        boolean retBool = false;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(),
                    scheduleJob.getGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(expression);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
            retBool = true;
        }catch (Exception e){
            retBool = false;
        }
        return retBool;
        
    }

    /**
     * 立即运行任务 ,会一直运行 
     */
    public static boolean runJob(QuartzJobHelper scheduleJob) {
        boolean retBool = false;
        try {
            TriggerKey key = TriggerKey.triggerKey(scheduleJob.getName(),scheduleJob.getGroup());
            Trigger trigger = scheduler.getTrigger(key);
            if(trigger == null){
                //在创建任务时如果不存在新建一个
                scheduleJob.setJobStatus(Trigger.TriggerState.NORMAL);
                addJobsToScheduler(scheduleJob);
            }else {
                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
                trigger = TriggerBuilder.newTrigger().withIdentity(key).withSchedule(scheduleBuilder).build();
                trigger.getJobDataMap().put(JOB_DATA_MAP_KET, scheduleJob);
                //重新执行
                scheduler.rescheduleJob(key,trigger);
            }
            retBool = true;
        }catch (Exception e){
            retBool = false;
        }
        return retBool;
    }

    /**
     * 将一个定时任务添加到定时任务执行库中
     */
    public static boolean addJobsToScheduler(QuartzJobHelper scheduleJobs) throws Exception {
        return addJobsToScheduler(new QuartzJobHelper[]{scheduleJobs})[0];
    }
    /**
     * 将一批定时任务添加到定时任务执行库中
     * @param scheduleJobs 定时任务组
     * @return 同等数量的是否添加成功
     * @throws Exception
     */
    public static boolean[] addJobsToScheduler(QuartzJobHelper[] scheduleJobs) throws Exception {
        if(scheduleJobs == null || scheduleJobs.length == 0) return null;
        boolean[] retBools = null;
        retBools = new boolean[scheduleJobs.length];
        for(int i = 0,len=scheduleJobs.length; i < len; i++) {
            QuartzJobHelper helper = combineJobHelper(scheduleJobs[i]);
            scheduler.scheduleJob(getMethodInvokingJobDetailFactoryBean(helper).getObject(), getCronTriggerFactoryBean(helper).getObject());
            JobKey jobKey = new JobKey(helper.getName(),helper.getGroup());
            if(Trigger.TriggerState.PAUSED.equals(helper.getJobStatus())) {// 判断是否需要暂停
                scheduler.pauseJob(jobKey);
            }else if(Trigger.TriggerState.ERROR.equals(helper.getJobStatus())) {

            }else {
                scheduler.resumeJob(jobKey);
            }
            retBools[i] = true;
        }
        return retBools;
    }

    /**
     * 获取Cron触发器工厂
     */
    private static CronTriggerFactoryBean getCronTriggerFactoryBean(final QuartzJobHelper helper) throws Exception{
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setName(helper.getName());
        trigger.setCronExpression(helper.getCron());
        trigger.setDescription(helper.getDesc());
        trigger.setGroup(helper.getGroup());
        trigger.getJobDataMap().put(JOB_DATA_MAP_KET, helper);
        trigger.setJobDetail(getMethodInvokingJobDetailFactoryBean(helper).getObject());
        trigger.afterPropertiesSet();
        return trigger;
    }
    private static MethodInvokingJobDetailFactoryBean getMethodInvokingJobDetailFactoryBean(final QuartzJobHelper helper) throws Exception{
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        if(applicationContext == null) throw new NullPointerException("无法执行定时调度。因为无法获取ApplicationContext");
        jobDetail.setBeanFactory(applicationContext.getAutowireCapableBeanFactory());
        jobDetail.setGroup(helper.getGroup());
        jobDetail.setName(helper.getName());
        jobDetail.setTargetBeanName(helper.getBeanName());
        jobDetail.setTargetObject(applicationContext.getBean(helper.getBeanName()));
        jobDetail.setTargetMethod(helper.getMethodName());
        jobDetail.afterPropertiesSet();
        return jobDetail;
    }
    /**
     * 运行任务表全部的暂停任务
     */
    public static boolean[] runAllJobSuspend() {
        List<QuartzJobHelper> poList = getPlanJobs();
        if(poList == null || poList.size() == 0) return new boolean[]{false};
        List<Boolean> retBools = new LinkedList<>();
        for (QuartzJobHelper po : poList) {
            if(po != null && Trigger.TriggerState.PAUSED.equals(po.getJobStatus())) {
                retBools.add(runJob(po));
            }
        }
        return ArrayUtils.toPrimitive(retBools.toArray(new Boolean[retBools.size()]));
    }

    /**
     * 判断job是否存在
     */
    public static boolean isContainsJob(QuartzJobHelper helper) {
        List<QuartzJobHelper> planJobs = getPlanJobs();
        if(planJobs == null || planJobs.size() == 0) return false;
        boolean retBool = false;
        for(QuartzJobHelper h : planJobs) {
            if(h.getName().equals(helper.getName()) && h.getGroup().equals(helper.getGroup())) {
                return true;
            }
        }
        return retBool;
    }
    public static Scheduler getScheduler() {
        return scheduler;
    }
}
