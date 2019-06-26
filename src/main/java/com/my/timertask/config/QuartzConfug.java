package com.my.timertask.config;

import java.util.Properties;

import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/** <blockquote>
* 定时任务服务配置
* @author [liangpr]
* @date [2019-01-18 09:17:31]
*/
@Configuration
public class QuartzConfug {
    private static Logger logger = LoggerFactory.getLogger(QuartzConfug.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = null;
        try {
            factoryBean = new SchedulerFactoryBean();
            Properties quartzProperties = new Properties();
            // 注入spring上下文环境
            factoryBean.setApplicationContext(applicationContext);
            factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
            // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
            factoryBean.setOverwriteExistingJobs(true);
            factoryBean.setQuartzProperties(quartzProperties);
            //factoryBean.setTaskExecutor(threadPool);
            logger.info("=========================================SchedulerFactoryBean初始化完成=========================================");
        }catch (Exception e){
            logger.error("=========================================SchedulerFactoryBean初始化失败", e);
        }
        
        return factoryBean;
    }
}
