package com.my.timertask.entity.po;

import java.util.HashMap;
import java.util.Map;

public class TimedTaskPO2 {
    private Integer id;
    private String name;
    private String group;
    private String sysGroup;
    private String cron;
    private String startDate;
    private String stopDate;
    private String runCount;
    private String runCountDate;
    private String jobStatus;
    private String operaStatus;
    private String methodData;
    private String methodDataType;
    private String methodName;
    private String beanName;
    private String beanPath;
    private String selfStart;
    private String desc;
    private String createDate;
    private String modifyDate;
    private Map<String, String> aa = new HashMap<>();
    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getGroup() {
        return this.group;
    }
    public String getSysGroup() {
        return this.sysGroup;
    }
    public String getCron() {
        return this.cron;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public String getStopDate() {
        return this.stopDate;
    }
    public String getRunCount() {
        return this.runCount;
    }
    public String getRunCountDate() {
        return this.runCountDate;
    }
    public String getJobStatus() {
        return this.jobStatus;
    }
    public String getOperaStatus() {
        return this.operaStatus;
    }
    public String getMethodData() {
        return this.methodData;
    }
    public String getMethodDataType() {
        return this.methodDataType;
    }
    public String getMethodName() {
        return this.methodName;
    }
    public String getBeanName() {
        return this.beanName;
    }
    public String getBeanPath() {
        return this.beanPath;
    }
    public String getSelfStart() {
        return this.selfStart;
    }
    public String getDesc() {
        return this.desc;
    }
    public String getCreateDate() {
        return this.createDate;
    }
    public String getModifyDate() {
        return this.modifyDate;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setSysGroup(String sysGroup) {
        this.sysGroup = sysGroup;
    }
    public void setCron(String cron) {
        this.cron = cron;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }
    public void setRunCount(String runCount) {
        this.runCount = runCount;
    }
    public void setRunCountDate(String runCountDate) {
        this.runCountDate = runCountDate;
    }
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    public void setOperaStatus(String operaStatus) {
        this.operaStatus = operaStatus;
    }
    public void setMethodData(String methodData) {
        this.methodData = methodData;
    }
    public void setMethodDataType(String methodDataType) {
        this.methodDataType = methodDataType;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    public void setBeanPath(String beanPath) {
        this.beanPath = beanPath;
    }
    public void setSelfStart(String selfStart) {
        this.selfStart = selfStart;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
    
}
