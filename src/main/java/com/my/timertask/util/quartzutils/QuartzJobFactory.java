package com.my.timertask.util.quartzutils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.my.timertask.entity.po.TimedTaskPO;

/** <blockquote>
* 定时任务调度类
* @author [liangpr]
* @date [2019-01-18 09:16:11]
*/
public class QuartzJobFactory implements Job{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private ApplicationContext jobSpringApp = null;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String key = QuartzJobServiceUtils.JOB_DATA_MAP_KET;
        // 获取JobExecutionContext中的service对象    
        try {
            // 获取ApplicationContext
            jobSpringApp = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            // 获取当前定时任务执行的job
            if(jobDataMap != null && jobDataMap.containsKey(key)) {
                // 获取job对象中保存对应表的实体类
                Object obj = jobDataMap.get(key);
                if(obj != null && obj instanceof TimedTaskPO) {
                    TimedTaskPO po = (TimedTaskPO) obj;
                    doIsCheck(po);
                }
            }
        } catch (Exception e) {
            logger.error("定时任务执行失败",e);
        }
    }
    
    /** <blockquote>
    * 判断是否执行定时任务并执行相应的否操作 
    * @param po
    * @throws Exception
    */  
    private void doIsCheck(TimedTaskPO po) throws Exception {
        Date time = new Date();
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 判断是否可用
        if(StringUtils.isNumeric(po.getJobStatus()) && QuartzJobStatusEnum.可用.getKey() == Integer.parseInt(po.getJobStatus())) {
            // 判断是否不为暂停和停用
            if(StringUtils.isNumeric(po.getOperaStatus()) && QuartzOperatingStatusEnum.暂停运行.getKey() == Integer.parseInt(po.getOperaStatus())) {
                // 暂停
                QuartzJobServiceUtils.pauseJob(po);
            }else if (StringUtils.isNumeric(po.getOperaStatus()) && QuartzOperatingStatusEnum.停止运行.getKey() == Integer.parseInt(po.getOperaStatus())) {
                // 移除定时任务
                QuartzJobServiceUtils.deleteJob(po);
            }else if (time.after(dateFormat.parse(po.getStartDate())) && time.before(dateFormat.parse(po.getStopDate()))) {
                // 暂停
                QuartzJobServiceUtils.pauseJob(po);
            }else {
                // 运行
                runMethod(po);
            }
        }else {
            // 移除定时任务
            QuartzJobServiceUtils.deleteJob(po);
        }
    }
    /** <blockquote>
    * 根据task实体类执行方法 
    * @param po
    * @throws Exception
    */  
    private void runMethod(TimedTaskPO po) throws Exception {
      //获取Spring中的上下文
        if(jobSpringApp != null) {
            if(po.getName() != null && po.getMethodName() != null) {
                long time1 = new Date().getTime() ,time2 = -1;
                Object bean = jobSpringApp.getBean(po.getBeanName());
                Class<?>[] metClass = strToClassArr(po.getMethodDataType());
                Method method = bean.getClass().getMethod(po.getMethodName(), metClass);
                Object[] metData = po.getMethodData() != null? po.getMethodData().split(",") : null;
                method.invoke(bean, metData);
                time2 = new Date().getTime();
                // 追加运行次数
                //QuartzJobServiceUtils.addJobRunCount(po.getId());
                // 追加运行时间
                //QuartzJobServiceUtils.addJobRunCountDateTime(po.getId(), time2-time1);
            }else {
                logger.error("无法获取JobExecutionContext对象中保存的Spring上下文环境，无法执行定时任务");
            }
        }else {
            logger.error("无法获取ApplicationContext, 无法通过nean名称和方法名称执行定时任务");
        }
        
    }
    /** <blockquote>
    * 将字符串按照逗号分割为CLass类型 
    * @param str
    * @return
     * @throws ClassNotFoundException 
    */  
    private Class<?>[] strToClassArr(String str) throws ClassNotFoundException{
        Class<?>[] retClass = null;
        if(str != null) {
            String[] strs = str.split(",");
            retClass = new Class<?>[strs.length];
            for(int i=0,len =strs.length; i<len; i++) {
                String tempStr = strs[i];
                retClass[i] = Class.forName(tempStr);
            }
        }
        return retClass;
    }
}
