package com.my.timertask.annotation.quartz_handler;

import lombok.NonNull;
import org.quartz.Trigger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.Map;

/**
 * @author liangpr
 * @date 2019/06/19
 */
public class QuartzJobHelper implements java.io.Serializable {
    private static final long serialVersionUID = -636866859781227084L;
    /** 任务名 */
    private String name;
    /** 所属的组 */
    private String group;
    /** 所属的系统组 */
    private String sysGroup;
    /** 触发器名 */
    private String triggerName;
    /** 触发器组名 */
    private String triggerGroupName;
    /** coron表达式 */
    private String cron;
    /** 开始时间 */
    private String startDate;
    /** 结束时间 */
    private String stopDate;
    /** 运行次数 */
    private Integer runCount;
    /** 最大运行次数 */
    private Long maxRunCount;
    /** 初始化时延迟几毫秒后执行 */
    private Long initialDelay;
    /** 发布状态 */
    private Trigger.TriggerState jobStatus;
    /** 运行方法参数 */
    private Object[] methodData;
    /** 运行方法参数类型 */
    private Class[] methodDataType;
    /** 运行方法名 */
    private String methodName;
    /** 运行方法名所属于的bean */
    private String beanName;
    /** 运行方法名所属于的bean的路径 */
    private String beanPath;
    /** 任务描述 */
    private String desc;
    /** 自定义参数 */
    private Map<String, Object> parameter;
    
    /** 任务名 */
    public String getName() {
        return this.name;
    }
    /** 任务名 */
    public QuartzJobHelper setName(String name) {
        this.name = name;
        return this;
    }
    /** 所属的组 */
    public String getGroup() {
        return this.group;
    }
    /** 所属的组 */
    public QuartzJobHelper setGroup(String group) {
        this.group = group;
        return this;
    }
    /** 所属的系统组 */
    public String getSysGroup() {
        return this.sysGroup;
    }
    /** 所属的系统组 */
    public QuartzJobHelper setSysGroup(String sysGroup) {
        this.sysGroup = sysGroup;
        return this;
    }
    /** 触发器名 */
    public String getTriggerName() {
        return triggerName;
    }
    /** 触发器名 */
    public QuartzJobHelper setTriggerName(String triggerName) {
        this.triggerName = triggerName;
        return this;
    }
    /** 触发器组名 */
    public String getTriggerGroupName() {
        return triggerGroupName;
    }
    /** 触发器组名 */
    public QuartzJobHelper setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
        return this;
    }
    /** coron表达式 */
    public String getCron() {
        return this.cron;
    }
    /** coron表达式 */
    public QuartzJobHelper setCron(String cron) {
        this.cron = cron;
        return this;
    }
    /** 开始时间 */
    public String getStartDate() {
        return this.startDate;
    }
    /** 开始时间 */
    public QuartzJobHelper setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }
    /** 结束时间 */
    public String getStopDate() {
        return this.stopDate;
    }
    /** 结束时间 */
    public QuartzJobHelper setStopDate(String stopDate) {
        this.stopDate = stopDate;
        return this;
    }
    /** 运行次数 */
    public Integer getRunCount() {
        return this.runCount;
    }
    /** 运行次数 */
    public QuartzJobHelper setRunCount(Integer runCount) {
        this.runCount = runCount;
        return this;
    }
    /** 最大运行次数 */
    public Long getMaxRunCount() {
        return maxRunCount;
    }
    /** 最大运行次数 */
    public QuartzJobHelper setMaxRunCount(Long maxRunCount) {
        this.maxRunCount = maxRunCount;
        return this;
    }
    /** 初始化时延迟几毫秒秒后执行 */
    public Long getInitialDelay() {
        return initialDelay;
    }
    /** 初始化时延迟几毫秒秒后执行 */
    public QuartzJobHelper setInitialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
        return this;
    }
    /** 发布状态 */
    public Trigger.TriggerState getJobStatus() {
        return this.jobStatus;
    }
    /** 发布状态 */
    public QuartzJobHelper setJobStatus(Trigger.TriggerState jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }
    /** 方法参数 */
    public Object[] getMethodData() {
        return this.methodData;
    }
    /** 方法参数 */
    public QuartzJobHelper setMethodData(Object[] methodData) {
        this.methodData = methodData;
        return this;
    }
    /** 方法参数类型 */
    public Class[] getMethodDataType() {
        return this.methodDataType;
    }
    /** 方法参数类型 */
    public QuartzJobHelper setMethodDataType(Class[] methodDataType) {
        this.methodDataType = methodDataType;
        return this;
    }
    /** 运行方法名 */
    public String getMethodName() {
        return this.methodName;
    }
    /** 运行方法名 */
    public QuartzJobHelper setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }
    /** 运行方法名所属于的bean */
    public String getBeanName() {
        return this.beanName;
    }
    /** 运行方法名所属于的bean */
    public QuartzJobHelper setBeanName(String beanName) {
        this.beanName = beanName;
        return this;
    }
    /** 运行方法名所属于的bean的路径 */
    public String getBeanPath() {
        return this.beanPath;
    }
    /** 运行方法名所属于的bean的路径 */
    public QuartzJobHelper setBeanPath(String beanPath) {
        this.beanPath = beanPath;
        return this;
    }
    /** 任务描述 */
    public String getDesc() {
        return this.desc;
    }
    /** 任务描述 */
    public QuartzJobHelper setDesc(String desc) {
        this.desc = desc;
        return this;
    }
    /** 获取自定义参数 */
    public Object getParameterBykey(String key) {
        if(!this.parameter.containsKey(key)) return null;
        return this.parameter.get(key);
    }
    /** 获取自定义参数 */
    public Map<String, Object> getParameter() {
        return parameter;
    }
    /** 追加自定义参数 */
    public QuartzJobHelper putParameter(String key, Object val) {
        this.parameter.put(key, val);
        return this;
    }

    @Override
    public String toString() {
        return "QuartzJobHelper{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", sysGroup='" + sysGroup + '\'' +
                ", cron='" + cron + '\'' +
                ", startDate='" + startDate + '\'' +
                ", stopDate='" + stopDate + '\'' +
                ", runCount='" + runCount + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", methodData='" + methodData + '\'' +
                ", methodDataType=" + Arrays.toString(methodDataType) +
                ", methodName='" + methodName + '\'' +
                ", beanName='" + beanName + '\'' +
                ", beanPath='" + beanPath + '\'' +
                ", desc='" + desc + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
