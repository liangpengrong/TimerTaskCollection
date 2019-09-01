package com.my.timertask.annotation.quartz_handler;

import com.my.timertask.annotation.QuartzMethod;
import com.my.timertask.util.quartz.QuartzJobFactory;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Configuration
public class QuartzMethodHandler implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    // 存放每个Java Bean中的的具有QuartzMethod注解的方法
    private Map<String, Method[]> quartzMethods = new HashMap<>();
    public QuartzMethodHandler(){}
    private static Logger logger = LoggerFactory.getLogger(QuartzMethodHandler.class);
    
    private void init() throws BeansException {
        // 拿到全部具有@QuartzMethod注解的方法
        getQuartzMethod();
        addMethodToQuartzjob();
    }
    // 获取全部的被@QuartzMethod标注的方法
    private void getQuartzMethod() {
        if(applicationContext != null) {
            // 获取全部的被QuartzMethod标注的javabean
            String[] beans = applicationContext.getBeanDefinitionNames();// 获取全部的bean 的名称
            Class<?> beanType = null;
            Object bean = null;
            Method[] methods = null;
            for (String beanName : beans) {
                bean = applicationContext.getBean(beanName);
                beanType = applicationContext.getType(beanName);
                // 判断bean中是否具有QuartzMethod注解的方法
                if(bean != null) {
                    methods = beanType.getMethods();
                    for(Method m : methods) {
                        if(m.getAnnotation(QuartzMethod.class) != null) {
                            if(quartzMethods.containsKey(bean)) {
                                List<Method> list = new ArrayList(Arrays.asList(quartzMethods.get(beanType)));
                                list.add(m);
                                quartzMethods.put(beanName, list.toArray(new Method[list.size()]));
                            }else {
                                quartzMethods.put(beanName, new Method[]{m});
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 将待加入到定时任务的方法加入到Quartz中
     */
    private boolean addMethodToQuartzjob() {
        if(quartzMethods != null && quartzMethods.size() > 0) {
            Object bean = null;
            String jobName = "";
            for(Map.Entry<String, Method[]> ent : quartzMethods.entrySet()) {
                for(Method m : ent.getValue()) {
                    QuartzMethod[] annotations = m.getAnnotationsByType(QuartzMethod.class);
                    QuartzMethod annotation = annotations != null && annotations.length >0? annotations[0] : null;
                    if(annotation != null) {
                        QuartzJobHelper helper = new QuartzJobHelper();
                        bean = applicationContext.getBean(ent.getKey());
                        // 判断jobName
                        if(StringUtils.isNoneEmpty(annotation.name())) {
                            jobName = annotation.name();
                        }else {
                            if(bean != null) {
                                jobName = bean.getClass().getName()+"_"+m.getName();
                            }else {
                                jobName = m.getName()+"_"+System.currentTimeMillis()+"()";
                            }
                        }
                        helper.setName(jobName)
                            .setCron(StringUtils.isNotEmpty(annotation.cron())? annotation.cron() : null)
                            .setGroup(StringUtils.isNotBlank(annotation.group())? annotation.group() : null)
                            .setTriggerName(StringUtils.isNotBlank(annotation.triggerName())? annotation.triggerName() : null)
                            .setTriggerGroupName(StringUtils.isNotBlank(annotation.triggerGroupName())? annotation.triggerGroupName() : null)
                            .setJobStatus(annotation.jobStatus())
                            .setStartDate(StringUtils.isNotEmpty(annotation.startDate())? annotation.startDate() : null)
                            .setStopDate(StringUtils.isNotEmpty(annotation.stopDate())? annotation.stopDate() : null)
                            .setDesc(StringUtils.isNotEmpty(annotation.desc())? annotation.desc() : null)
                            .setBeanName(ent.getKey())
                            .setBeanPath(bean.getClass().getName())
                            .setMethodName(m.getName());
                        try {
                            QuartzJobFactory.addJobsToScheduler(helper);
                        }catch (Exception e){
                            System.out.println("定时任务添加失败");
                        }
                    }
                }
            }
            
        }
        return false; 
    }

    public Map<String, Method[]> getQuartzMethods() {
        return quartzMethods;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        // 拿到applicationContext后执行init方法
        init();
    }
}
