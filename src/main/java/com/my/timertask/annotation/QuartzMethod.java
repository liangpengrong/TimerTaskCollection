package com.my.timertask.annotation;

import org.quartz.Trigger;

import java.lang.annotation.*;

/**
 * 被该注解标注的方法将被注解扫描器扫描并加入到quartz定时方法中 注意该方法所属的类必须交给spring来管理 或在类上标注@Component
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzMethod {
    /** 任务名 */
    String name() default "";
    /** 所属的组 */
    String group() default "";
    /** 触发器名 */
    String triggerName() default "";
    /** 触发器组名 */
    String triggerGroupName() default "";
    /** coron表达式 */
    String cron();
    /** 开始时间 */
    String startDate() default "";
    /** 结束时间 */
    String stopDate() default "";
    /** 发布状态 */
    Trigger.TriggerState jobStatus() default Trigger.TriggerState.NORMAL;
    /** 任务描述 */
    String desc() default "";
    /** 初始化时延迟多少毫秒后执行 */
    //long initialDelay() default -1;
}