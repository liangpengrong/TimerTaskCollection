package com.my.timertask.util.quartzutils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.my.timertask.dao.TimedTaskDaoI;
import com.my.timertask.po.TimedTaskPo;

@Component
public class QuartzJobServiceUtils {
    public static final String JOB_DATA_MAP_KET = "scheduleJob";
    private static Scheduler scheduler;
    private static TimedTaskDaoI timedTaskDao;
    private static ApplicationContext applicationContext;
    @Autowired
    public QuartzJobServiceUtils(Scheduler scheduler, TimedTaskDaoI timedTaskDao, ApplicationContext applicationContext) {
        QuartzJobServiceUtils.scheduler = scheduler;
        QuartzJobServiceUtils.timedTaskDao = timedTaskDao;
        QuartzJobServiceUtils.applicationContext = applicationContext;
    }
    
    /** <blockquote>
    * 创建一个默认的定时任务实体类 
    * @param class1 - 要执行的class
    * @param methodName - class中的那个方法
    * @param cron - 表达式
    * @param desc - 定时任务描述
    * @param createUserId - 创建者id
    * @param modifyUserId - 修改者id
    * @return
    */  
    public static TimedTaskPo getDefaultTimedTask(Class<?> class1, String methodName, String cron, String desc, String createUserId, String modifyUserId) {
        TimedTaskPo scheduleJob = null;
        if(class1 != null && methodName != null && cron != null) {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String[] beanNames = applicationContext.getBeanNamesForType(class1);
            String beanName = beanNames != null && beanNames.length > 0? beanNames[0] : null;
            scheduleJob = new TimedTaskPo();
            String jobName = class1.getName()+"_"+methodName;
            scheduleJob.setName(jobName);
            scheduleJob.setBeanName(beanName);
            scheduleJob.setGroupName(class1.getName());
            scheduleJob.setMethodName(methodName);
            scheduleJob.setCron(cron);
            if(StringUtils.isNotEmpty(createUserId)) {
                scheduleJob.setCreateUserId(createUserId);
                scheduleJob.setCreateDate(time);
            }
            scheduleJob.setJobStatus(QuartzJobStatusEnum.可用.getKey().toString());
            scheduleJob.setOperaStatus(QuartzOperatingStatusEnum.立即运行.getKey().toString());
            scheduleJob.setDescription(desc);
            if(StringUtils.isNotEmpty(modifyUserId)) {
                scheduleJob.setModifyUserId(modifyUserId);
                scheduleJob.setModifyDate(time);
            }else if (StringUtils.isNotEmpty(createUserId)) {
                scheduleJob.setModifyUserId(createUserId);
                scheduleJob.setModifyDate(time);
            }
        }
        return scheduleJob;
    }
    /** <blockquote>
    * 获取所有计划中的任务
    * @return
    * @throws Exception 
    */
    public static List<TimedTaskPo> getPlanJobs() throws Exception {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<TimedTaskPo> jobList = new ArrayList<TimedTaskPo>();
        for(JobKey jobKey  : jobKeys){
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers){
                TimedTaskPo job = (TimedTaskPo) trigger.getJobDataMap().get(JOB_DATA_MAP_KET);
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCron(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /** <blockquote>
    * 获取所有运行中的任务
    * @return
    * @throws Exception 
    */
    public static List<TimedTaskPo> getRunningJobs() throws Exception {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<TimedTaskPo> jobList = new ArrayList<TimedTaskPo>();
        for (JobExecutionContext executingJob : executingJobs){
            Trigger trigger = executingJob.getTrigger();
            TimedTaskPo job = (TimedTaskPo) trigger.getJobDataMap().get(JOB_DATA_MAP_KET);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCron(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /** <blockquote>
    * 暂停任务
    * @param scheduleJob
    * @throws Exception 
    */
    public static void pauseJob(final TimedTaskPo scheduleJob) throws Exception {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
        if(jobKey != null) {
            scheduler.pauseJob(jobKey);
            // 更新结束时间
            updateEndTime(scheduleJob);
        }

    }
    
    /** <blockquote>
    * 恢复任务
    * @param scheduleJob
    * @throws Exception 
    */
    public static void resumeJob(final TimedTaskPo scheduleJob) throws Exception {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
        if(jobKey != null) {
            scheduler.resumeJob(jobKey);
        }

    }
    /** <blockquote>
     * 重新执行任务任务
     * @param scheduleJob
     * @throws Exception 
     */
     public static void rescheduleJob(final TimedTaskPo scheduleJob) throws Exception {
         TriggerKey key = TriggerKey.triggerKey(scheduleJob.getName(),scheduleJob.getGroupName());
         Trigger trigger = scheduler.getTrigger(key);
         if(trigger != null){
             //重新执行
             scheduler.rescheduleJob(key,trigger);
         }
     }
    /** <blockquote>
    * 删除本地定时任务表的任务并立即停止该任务 
    * @param scheduleJob - 定时任务实体类
    * @return - boolean数组 删除本地任务是否成功
    * @throws Exception
    */  
    public static boolean deleteJob(TimedTaskPo scheduleJob) throws Exception {
        boolean retBool = false;
        int keyid = isJobPersistence(scheduleJob, true);
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        if(keyid >= 0) {
            TimedTaskPo tempPo = new TimedTaskPo();
            tempPo.setId(keyid);
            tempPo.setJobStatus(QuartzJobStatusEnum.不可用.getKey().toString());
            tempPo.setEndTime(time);
            retBool = timedTaskDao.updateOneTimedTask(tempPo) >= 0;
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
            scheduler.deleteJob(jobKey);
        }
        return retBool;
    }

    /** <blockquote>
    * 立即运行任务 ,只会运行一次
    * @param scheduleJob
    * @throws Exception 
    */
    public static void runOnce(TimedTaskPo scheduleJob) throws Exception {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroupName());
        if(jobKey != null) {
            scheduler.triggerJob(jobKey);
        }else {
            
        }
        // 更新启动时间
        updateStartTime(scheduleJob);
    }
    
    /** <blockquote>
    * 更新任务的启动时间 
    * @param scheduleJob
    * @throws Exception
    */  
    public static void updateStartTime(final TimedTaskPo scheduleJob) throws Exception {
        int keyid = isJobPersistence(scheduleJob, true);
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        if(keyid >= 0) {
            TimedTaskPo tempPo = new TimedTaskPo();
            tempPo.setId(keyid);
            tempPo.setStartTime(time);
            timedTaskDao.updateOneTimedTask(tempPo);
        }
    }
    /** <blockquote>
     * 更新任务的结束时间 
     * @param scheduleJob
     * @throws Exception
     */  
     public static void updateEndTime(final TimedTaskPo scheduleJob) throws Exception {
         int keyid = isJobPersistence(scheduleJob, true);
         String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
         if(keyid >= 0) {
             TimedTaskPo tempPo = new TimedTaskPo();
             tempPo.setId(keyid);
             tempPo.setEndTime(time);
             timedTaskDao.updateOneTimedTask(tempPo);
         }
     }
    /** <blockquote>
    * 更新任务的时间表达式
    * @param scheduleJob
    * @param expression
    * @throws Exception 
    */
    public static void updateExpression(TimedTaskPo scheduleJob, String expression) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(),
                scheduleJob.getGroupName());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(expression);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }
    
    /** <blockquote>
    * 立即运行任务 ,会一直运行 
    * @param scheduleJob - 定时任务实体类
    * @param updateStartTime - 更新启动时间，已经运行的任务则不会更新
    * @return
    * @throws Exception
    */  
    public static boolean runJob(TimedTaskPo scheduleJob, boolean updateStartTime) throws Exception {
        boolean retBool = false;
        TriggerKey key = TriggerKey.triggerKey(scheduleJob.getName(),scheduleJob.getGroupName());
        Trigger trigger = scheduler.getTrigger(key);
        if(trigger == null){
            //在创建任务时如果不存在新建一个
            addJobsToScheduler(new TimedTaskPo[] {scheduleJob},new boolean[] {true}, updateStartTime);
        }else{
            // Trigger已存在，那么更新相应的定时设置
            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
            trigger = TriggerBuilder.newTrigger().withIdentity(key).withSchedule(scheduleBuilder).build();
            trigger.getJobDataMap().put(JOB_DATA_MAP_KET, scheduleJob);
            //重新执行
            scheduler.rescheduleJob(key,trigger);
        }
        retBool = true;
        return retBool;
    }
    /** <blockquote>
    * 将一个定时任务添加到定时任务执行库中
    * @param scheduleJobs - 定时任务组
    * @param run - 是否启动
    * @param updateStartTime - 是否更新定时任务组的启动时间
    * @return - 同等数量的是否添加成功
    * @throws Exception
    */  
    public static boolean[] addJobsToScheduler(TimedTaskPo[] scheduleJobs, boolean[] run, boolean updateStartTime) throws Exception {
        boolean[] retBools = null;
        if(scheduleJobs != null) {
            retBools = new boolean[scheduleJobs.length];
            for(int i = 0,len=scheduleJobs.length; i < len; i++) {
                TimedTaskPo scheduleJob = scheduleJobs[i];
                Trigger trigger = null;
                // 再创建任务时如果不存在新建一个
                JobBuilder jobBuilder = JobBuilder.newJob(QuartzJobFactory.class);
                JobDetail jobDetail = jobBuilder.withIdentity(scheduleJob.getName(),scheduleJob.getGroupName()).build();
                jobDetail.getJobDataMap().put(JOB_DATA_MAP_KET, scheduleJob);
                String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getName(),scheduleJob.getGroupName())
                        .withSchedule(scheduleBuilder).build();
                trigger.getJobDataMap().put(JOB_DATA_MAP_KET,scheduleJob);
                scheduler.scheduleJob(jobDetail, trigger);
                JobKey jobKey = new JobKey(scheduleJob.getName(),scheduleJob.getGroupName());
                scheduler.pauseJob(jobKey);
                if(i < run.length && run[i]) {
                    scheduler.resumeJob(jobKey);
                }
                // 更新启动时间
                if(updateStartTime) {
                    // 更新传入实体类的启动时间
                    scheduleJob.setStartTime(time);
                }
            }
        }
        return retBools;
    }
    /** <blockquote>
    * 添加任务到定时任务表中并返回同等数量的是否添加成功
    * @param scheduleJobs - 任务数组
    * @param run - 是否立即运行
    * @param updateStartTime - 更新启动时间，已经运行的任务则不会更新
    * @return - 同等数量的是否添加成功
    * @throws Exception
    */  
    public static boolean[] addJobsToTable(TimedTaskPo[] scheduleJobs, boolean[] run, boolean updateStartTime) throws Exception {
        boolean[] retBools = null;
        if(scheduleJobs != null) {
            retBools = new boolean[scheduleJobs.length];
            boolean isPersistence = false;
            for (int i = 0,len=scheduleJobs.length; i < len; i++) {
                TimedTaskPo po = scheduleJobs[i];
                isPersistence = isJobPersistence(po, true)>=0;
                // 该任务不存在任务表中
                if(!isPersistence) {
                    // 添加任务
                   int key = timedTaskDao.addOneTimedTask(po);
                   if(key >= 0) {
                       if(i<run.length && run[i]) runJob(po, updateStartTime);
                       retBools[i] = true;
                   }else {
                       retBools[i] = false;
                   }
                }else {
                    if(i<run.length && run[i]) runJob(po, updateStartTime);
                    retBools[i] = true;
                }
            }
        }
        return retBools;
    }
    /** <blockquote>
    * 运行任务表全部的可运行的任务
    * @return
    * @throws Exception 
    */  
    public static boolean[] runAllJobPersistence() throws Exception {
        boolean[] retBools = null;
        List<TimedTaskPo> poList = timedTaskDao.listTimedTask(null);
        for (TimedTaskPo po : poList) {
            if(po != null && StringUtils.isNumeric(po.getOperaStatus()) && QuartzOperatingStatusEnum.停止运行.getKey() != Integer.parseInt(po.getOperaStatus())
                    && StringUtils.isNumeric(po.getJobStatus()) && QuartzJobStatusEnum.可用.getKey() == Integer.parseInt(po.getJobStatus())) {
                runJob(po,true);
            }
        }
        return retBools;
    }
    
    /** <blockquote>
    * 获取所有的持久化计划任务 
    * @return
    * @throws Exception
    */  
    public static List<TimedTaskPo> getJobPersistences() throws Exception{
        List<TimedTaskPo> retList = timedTaskDao.listTimedTask(null);
        return retList;
    }
    /** <blockquote>
    * 判断计划任务是否存在于任务表中
    * @param scheduleJob - 计划任务实体类
    * @param loose - 是否宽松的对比(只对比任务名 任务组名 否则将对比全部的字段)
    * @return 任务表对应的id
    * @throws Exception
    */  
    public static int isJobPersistence(final TimedTaskPo scheduleJob, boolean loose)  throws Exception{
        int retKeyId = -1;
        List<TimedTaskPo> poList = null;
        if (loose) {
            TimedTaskPo tempPo = new TimedTaskPo();
            tempPo.setName(scheduleJob.getName());
            tempPo.setGroupName(scheduleJob.getGroupName());
            poList = timedTaskDao.listTimedTask(tempPo);
        }else {
            poList = timedTaskDao.listTimedTask(scheduleJob);
        }
        retKeyId = poList != null && poList.size() > 0? poList.get(0).getId():-1;
        return retKeyId;
    }
}
