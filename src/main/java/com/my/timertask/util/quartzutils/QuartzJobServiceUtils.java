package com.my.timertask.util.quartzutils;

import java.util.ArrayList;
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
import org.springframework.stereotype.Component;

import com.my.timertask.dao.TimedTaskDaoI;
import com.my.timertask.entity.po.TimedTaskPO;
import com.my.timertask.util.bean.BeanFactoryUtils;

@Component
public class QuartzJobServiceUtils {
    public static final String JOB_DATA_MAP_KET = "scheduleJob";
    private static Scheduler getScheduler() {
        return BeanFactoryUtils.getBean(Scheduler.class);
    }
    private static TimedTaskDaoI getTimedTaskDao() {
        return BeanFactoryUtils.getBean(TimedTaskDaoI.class);
    }
    
    /** <blockquote>
    * 获取所有计划中的任务
    * @return
    * @throws Exception 
    */
    public static List<TimedTaskPO> getPlanJobs() throws Exception {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = getScheduler().getJobKeys(matcher);
        List<TimedTaskPO> jobList = new ArrayList<TimedTaskPO>();
        for(JobKey jobKey  : jobKeys){
            List<? extends Trigger> triggers = getScheduler().getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers){
                TimedTaskPO job = (TimedTaskPO) trigger.getJobDataMap().get(JOB_DATA_MAP_KET);
                Trigger.TriggerState triggerState = getScheduler().getTriggerState(trigger.getKey());
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
    public static List<TimedTaskPO> getRunningJobs() throws Exception {
        List<JobExecutionContext> executingJobs = getScheduler().getCurrentlyExecutingJobs();
        List<TimedTaskPO> jobList = new ArrayList<TimedTaskPO>();
        for (JobExecutionContext executingJob : executingJobs){
            Trigger trigger = executingJob.getTrigger();
            TimedTaskPO job = (TimedTaskPO) trigger.getJobDataMap().get(JOB_DATA_MAP_KET);
            Trigger.TriggerState triggerState = getScheduler().getTriggerState(trigger.getKey());
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
    public static boolean pauseJob(TimedTaskPO scheduleJob) throws Exception {
        boolean retBool1 = false;
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
        if(jobKey != null && StringUtils.isNumeric(scheduleJob.getOperaStatus())) {
            Integer start = Integer.parseInt(scheduleJob.getOperaStatus());
            if(QuartzOperatingStatusEnum.暂停运行.getKey() != start) {
                getScheduler().pauseJob(jobKey);
                scheduleJob.setOperaStatus(QuartzOperatingStatusEnum.暂停运行.getKey().toString());
                retBool1 = updateJobAndOperaStatus(scheduleJob.getId(), null, QuartzOperatingStatusEnum.暂停运行);
            }
        }
        return retBool1;
    }
    
    /** <blockquote>
    * 恢复任务
    * @param scheduleJob
    * @throws Exception 
    */
    public static boolean resumeJob(TimedTaskPO scheduleJob) throws Exception {
        boolean retBool1 = false;
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
        if(jobKey != null && StringUtils.isNumeric(scheduleJob.getOperaStatus())) {
            Integer start = Integer.parseInt(scheduleJob.getOperaStatus());
            if(QuartzOperatingStatusEnum.正在运行.getKey() != start) {
                getScheduler().resumeJob(jobKey);
                scheduleJob.setOperaStatus(QuartzOperatingStatusEnum.正在运行.getKey().toString());
                retBool1 = updateJobAndOperaStatus(scheduleJob.getId(), null, QuartzOperatingStatusEnum.正在运行);
            }
        }
        return retBool1;
    }
    /** <blockquote>
     * 重新执行任务
     * @param scheduleJob
     * @throws Exception 
     */
     public static boolean rescheduleJob(TimedTaskPO scheduleJob) throws Exception {
         boolean retBool1 = false;
         TriggerKey key = TriggerKey.triggerKey(scheduleJob.getName(),scheduleJob.getGroup());
         Trigger trigger = getScheduler().getTrigger(key);
         if(trigger != null && StringUtils.isNumeric(scheduleJob.getOperaStatus())){
             Integer start = Integer.parseInt(scheduleJob.getOperaStatus());
             if(QuartzOperatingStatusEnum.正在运行.getKey() != start) {
                 //重新执行
                 getScheduler().rescheduleJob(key,trigger);
                 scheduleJob.setOperaStatus(QuartzOperatingStatusEnum.正在运行.getKey().toString());
                 retBool1 = updateJobAndOperaStatus(scheduleJob.getId(), null, QuartzOperatingStatusEnum.正在运行);
             }
         }
         return retBool1;
     }
    /** <blockquote>
    * 删除任务 
    * @param scheduleJob - 定时任务实体类
    * @return - boolean 删除本地任务是否成功
    * @throws Exception
    */  
    public static boolean deleteJob(TimedTaskPO scheduleJob) throws Exception {
        boolean retBool1 = false, retBool2 = false;
        int keyid = isJobPersistence(scheduleJob, true);
        if(keyid >= 0) {
            retBool1 = updateJobAndOperaStatus(scheduleJob.getId(), QuartzJobStatusEnum.不可用, QuartzOperatingStatusEnum.停止运行);
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
            retBool2 = getScheduler().deleteJob(jobKey);
            if(StringUtils.isNumeric(scheduleJob.getJobStatus()) && StringUtils.isNumeric(scheduleJob.getOperaStatus())){
                Integer start1 = Integer.parseInt(scheduleJob.getJobStatus());
                Integer start2 = Integer.parseInt(scheduleJob.getOperaStatus());
                if(QuartzJobStatusEnum.不可用.getKey() != start1) {
                    scheduleJob.setJobStatus(QuartzJobStatusEnum.不可用.getKey().toString());
                }
                if(QuartzOperatingStatusEnum.停止运行.getKey() != start2) {
                    scheduleJob.setOperaStatus(QuartzOperatingStatusEnum.停止运行.getKey().toString());
                }
            }
        }
        return retBool1 && retBool2;
    }
    
    /** <blockquote>
    * 更新对应id的job状态和运行状态
    * @param id
    * @param job
    * @param operating
    * @return
    */  
    public static boolean updateJobAndOperaStatus(Integer id, QuartzJobStatusEnum job, QuartzOperatingStatusEnum operating) {
        boolean retBool = false;
        TimedTaskPO tempPo = new TimedTaskPO();
        tempPo.setId(id);
        if(job != null) tempPo.setJobStatus(job.getKey().toString());
        if(operating != null) tempPo.setOperaStatus(operating.getKey().toString());
        retBool = getTimedTaskDao().updateOneTimedTask(tempPo) >= 0;
        return retBool;
    }
    
    /** <blockquote>
    * 追加对应id的运行次数
    * @param id
    * @return
    */  
    public static boolean addJobRunCount(Integer id) {
        boolean retBool = false;
        TimedTaskPO tempPo = new TimedTaskPO();
        tempPo.setId(id);
        List<TimedTaskPO> listTimedTask = getTimedTaskDao().listTimedTask(tempPo);
        if(listTimedTask != null && listTimedTask.size() > 0) {
            tempPo = listTimedTask.get(0);
            if(StringUtils.isNumeric(tempPo.getRunCount())) {
                tempPo.setRunCount((Integer.parseInt(tempPo.getRunCount())+ 1)+"");
            }else {
                tempPo.setRunCount("1");
            }
            retBool = getTimedTaskDao().updateOneTimedTask(tempPo) >= 0;
        }else {
            retBool = false;
        }
        return retBool;
    }
    
    /** <blockquote>
    * 追加对应id的运行总时间
    * @param id - id
    * @param ss - 毫秒
    * @return
    */  
    public static boolean addJobRunCountDateTime(Integer id, Long ss) {
        boolean retBool = false;
        TimedTaskPO tempPo = new TimedTaskPO();
        tempPo.setId(id);
        List<TimedTaskPO> listTimedTask = getTimedTaskDao().listTimedTask(tempPo);
        if(listTimedTask != null && listTimedTask.size() > 0) {
            tempPo = listTimedTask.get(0);
            String date = tempPo.getRunCountDate();
            if(StringUtils.isNumeric(date)) {
                tempPo.setRunCountDate((Long.parseLong(date)+ss)+"");
            }else {
                tempPo.setRunCountDate(ss != null? ss.toString() : null);
            }
            retBool = getTimedTaskDao().updateOneTimedTask(tempPo) >= 0;
        }else {
            retBool = false;
        }
        return retBool;
    }
    /** <blockquote>
    * 立即运行任务 ,只会运行一次
    * @param scheduleJob
    * @throws Exception 
    */
    public static void runOnce(TimedTaskPO scheduleJob) throws Exception {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
        if(jobKey != null) {
            getScheduler().triggerJob(jobKey);
        }
        
    }
    /** <blockquote>
    * 更新任务的时间表达式
    * @param scheduleJob
    * @param expression
    * @throws Exception 
    */
    public static void updateExpression(TimedTaskPO scheduleJob, String expression) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(),
                scheduleJob.getGroup());
        CronTrigger trigger = (CronTrigger) getScheduler().getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(expression);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();
        getScheduler().rescheduleJob(triggerKey, trigger);
    }
    
    /** <blockquote>
    * 立即运行任务 ,会一直运行 
    * @param scheduleJob - 定时任务实体类
    * @param updateStartTime - 更新启动时间，已经运行的任务则不会更新
    * @return
    * @throws Exception
    */  
    public static boolean runJob(TimedTaskPO scheduleJob) throws Exception {
        boolean retBool = false;
        TriggerKey key = TriggerKey.triggerKey(scheduleJob.getName(),scheduleJob.getGroup());
        Trigger trigger = getScheduler().getTrigger(key);
        if(trigger == null){
            //在创建任务时如果不存在新建一个
            addJobsToScheduler(new TimedTaskPO[] {scheduleJob},new boolean[] {true});
        }else{
            // Trigger已存在，那么更新相应的定时设置
            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
            trigger = TriggerBuilder.newTrigger().withIdentity(key).withSchedule(scheduleBuilder).build();
            trigger.getJobDataMap().put(JOB_DATA_MAP_KET, scheduleJob);
            //重新执行
            getScheduler().rescheduleJob(key,trigger);
        }
        retBool = true;
        return retBool;
    }
    /** <blockquote>
    * 将一批定时任务添加到定时任务执行库中
    * @param scheduleJobs - 定时任务组
    * @param run - 是否启动
    * @param updateStartTime - 是否更新定时任务组的启动时间
    * @return - 同等数量的是否添加成功
    * @throws Exception
    */  
    public static boolean[] addJobsToScheduler(TimedTaskPO[] scheduleJobs, boolean[] run) throws Exception {
        boolean[] retBools = null;
        if(scheduleJobs != null) {
            retBools = new boolean[scheduleJobs.length];
            for(int i = 0,len=scheduleJobs.length; i < len; i++) {
                TimedTaskPO scheduleJob = scheduleJobs[i];
                Trigger trigger = null;
                // 再创建任务时如果不存在新建一个
                JobBuilder jobBuilder = JobBuilder.newJob(QuartzJobFactory.class);
                JobDetail jobDetail = jobBuilder.withIdentity(scheduleJob.getName(),scheduleJob.getGroup()).build();
                jobDetail.getJobDataMap().put(JOB_DATA_MAP_KET, scheduleJob);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getName(),scheduleJob.getGroup())
                        .withSchedule(scheduleBuilder).build();
                trigger.getJobDataMap().put(JOB_DATA_MAP_KET,scheduleJob);
                getScheduler().scheduleJob(jobDetail, trigger);
                JobKey jobKey = new JobKey(scheduleJob.getName(),scheduleJob.getGroup());
                getScheduler().pauseJob(jobKey);
                scheduleJob.setOperaStatus(QuartzOperatingStatusEnum.暂停运行.getKey().toString());
                // 判断是否需要启动
                if(i < run.length && run[i]) {
                    // 更新传入实体类的启动时间
                    getScheduler().resumeJob(jobKey);
                    scheduleJob.setOperaStatus(QuartzOperatingStatusEnum.正在运行.getKey().toString());
                }
                TimedTaskPO tempTaskPo = new TimedTaskPO();
                tempTaskPo.setName(scheduleJob.getName());
                tempTaskPo.setGroup(scheduleJob.getGroup());
                tempTaskPo.setSysGroup(scheduleJob.getSysGroup());
                List<TimedTaskPO> listTimedTask = getTimedTaskDao().listTimedTask(tempTaskPo);
                // 判断是否已存在表中
                if(listTimedTask != null && listTimedTask.size() > 0) {
                    scheduleJob.setId(listTimedTask.get(0).getId());
                    // 更新
                    getTimedTaskDao().updateOneTimedTask(scheduleJob);
                }else {
                    // 添加
                    getTimedTaskDao().addOneTimedTask(scheduleJob);
                }
                
                retBools[i] = true;
            }
        }
        return retBools;
    }
    /** <blockquote>
    * 运行任务表全部的暂停任务
    * @return
    * @throws Exception 
    */  
    public static boolean[] runAllJobSuspend() throws Exception {
        boolean[] retBools = null;
        List<TimedTaskPO> poList = getTimedTaskDao().listTimedTask(null);
        for (TimedTaskPO po : poList) {
            if(po != null && StringUtils.isNumeric(po.getOperaStatus()) 
                    && QuartzOperatingStatusEnum.暂停运行.getKey() == Integer.parseInt(po.getOperaStatus())
                    && StringUtils.isNumeric(po.getJobStatus()) 
                    && QuartzJobStatusEnum.可用.getKey() == Integer.parseInt(po.getJobStatus())) {
                runJob(po);
            }
        }
        return retBools;
    }
    /** <blockquote>
     * 运行任务表全部的开机启动任务
     * @return
     * @throws Exception 
     */  
     public static boolean[] runAllJobSelfStart() throws Exception {
         boolean[] retBools = null;
         List<TimedTaskPO> poList = getTimedTaskDao().listTimedTask(null);
         for (TimedTaskPO po : poList) {
             if(po != null && StringUtils.isNumeric(po.getOperaStatus()) 
                     && "1".equals(po.getSelfStart()) 
                     && StringUtils.isNumeric(po.getJobStatus()) 
                     && QuartzJobStatusEnum.可用.getKey() == Integer.parseInt(po.getJobStatus())) {
                 runJob(po);
             }
         }
         return retBools;
     }
    /** <blockquote>
    * 获取所有的持久化计划任务 
    * @return
    * @throws Exception
    */  
    public static List<TimedTaskPO> getJobPersistences() throws Exception{
        List<TimedTaskPO> retList = getTimedTaskDao().listTimedTask(null);
        return retList;
    }
    /** <blockquote>
    * 判断计划任务是否存在于任务表中
    * @param scheduleJob - 计划任务实体类
    * @param loose - 是否宽松的对比(只对比任务名 任务组名 否则将对比全部的字段)
    * @return 任务表对应的id
    * @throws Exception
    */  
    public static int isJobPersistence(final TimedTaskPO scheduleJob, boolean loose)  throws Exception{
        int retKeyId = -1;
        List<TimedTaskPO> poList = null;
        if (loose) {
            TimedTaskPO tempPo = new TimedTaskPO();
            tempPo.setName(scheduleJob.getName());
            tempPo.setSysGroup(scheduleJob.getGroup());
            poList = getTimedTaskDao().listTimedTask(tempPo);
        }else {
            poList = getTimedTaskDao().listTimedTask(scheduleJob);
        }
        retKeyId = poList != null && poList.size() > 0? poList.get(0).getId():-1;
        return retKeyId;
    }
}
