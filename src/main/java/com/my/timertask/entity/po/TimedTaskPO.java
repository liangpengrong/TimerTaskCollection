package com.my.timertask.entity.po;

import java.io.Serializable;

public class TimedTaskPO implements Serializable {
    /** <blockquote> 序列化唯一的ID */
    private static final long serialVersionUID = -636866859781227084L;
    /** 主键自增id */
    private Integer id;
    /** 任务名 */
    private String name;
    /** 所属的组 */
    private String group;
    /** 所属的系统组 */
    private String sysGroup;
    /** coron表达式 */
    private String cron;
    /** 开始时间 */
    private String startDate;
    /** 结束时间 */
    private String stopDate;
    /** 运行次数 */
    private String runCount;
    /** 运行总时间单位秒 */
    private String runCountDate;
    /** 发布状态(0-停用 1-可用) */
    private String jobStatus;
    /** 运行状态(0-停止运行 1-正在运行 2-暂停运行) */
    private String operaStatus;
    /** 方法参数 */
    private String methodData;
    /** 方法参数类型 */
    private String methodDataType;
    /** 运行方法名 */
    private String methodName;
    /** 运行方法名所属于的bean */
    private String beanName;
    /** 运行方法名所属于的bean的路径 */
    private String beanPath;
    /** 是否在项目运行时启动一次，一般用于项目调试时候用(0-否 1-是) */
    private String selfStart;
    /** 任务描述 */
    private String desc;
    /** 创建时间 */
    private String createDate;
    /** 修改时间 */
    private String modifyDate;

    /** <blockquote> 无参构造器 */
    public TimedTaskPO() {}
    /** <blockquote>
    * 
    * @param id - 主键自增id
    * @param name - 任务名
    * @param group - 所属的组
    * @param sysGroup - 所属的系统组
    * @param cron - coron表达式
    * @param startDate - 开始时间
    * @param stopDate - 结束时间
    * @param runCount - 运行次数
    * @param runCountDate - 运行总时间单位秒
    * @param jobStatus - 发布状态(0-停用 1-可用)
    * @param operaStatus - 运行状态(0-停止运行 1-正在运行 2-暂停运行)
    * @param methodData - 方法参数
    * @param methodDataType - 方法参数类型
    * @param methodName - 运行方法名
    * @param beanName - 运行方法名所属于的bean
    * @param beanPath - 运行方法名所属于的bean的路径
    * @param selfStart - 是否在项目运行时启动一次，一般用于项目调试时候用(0-否 1-是)
    * @param desc - 任务描述
    * @param createDate - 创建时间
    * @param modifyDate - 修改时间
    */
    public TimedTaskPO (Integer id,String name,String group,String sysGroup,String cron,String startDate,
        String stopDate,String runCount,String runCountDate,String jobStatus,String operaStatus,String methodData,String methodDataType,
        String methodName,String beanName,String beanPath,String selfStart,String desc,String createDate,String modifyDate) {
        super();
        this.id = id;
        this.name = name;
        this.group = group;
        this.sysGroup = sysGroup;
        this.cron = cron;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.runCount = runCount;
        this.runCountDate = runCountDate;
        this.jobStatus = jobStatus;
        this.operaStatus = operaStatus;
        this.methodData = methodData;
        this.methodDataType = methodDataType;
        this.methodName = methodName;
        this.beanName = beanName;
        this.beanPath = beanPath;
        this.selfStart = selfStart;
        this.desc = desc;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    /** 主键自增id */
    public Integer getId() {
        return this.id;
    }
    /** 主键自增id */
    public void setId(Integer id) {
        this.id = id;
    }
    /** 任务名 */
    public String getName() {
        return this.name;
    }
    /** 任务名 */
    public void setName(String name) {
        this.name = name;
    }
    /** 所属的组 */
    public String getGroup() {
        return this.group;
    }
    /** 所属的组 */
    public void setGroup(String group) {
        this.group = group;
    }
    /** 所属的系统组 */
    public String getSysGroup() {
        return this.sysGroup;
    }
    /** 所属的系统组 */
    public void setSysGroup(String sysGroup) {
        this.sysGroup = sysGroup;
    }
    /** coron表达式 */
    public String getCron() {
        return this.cron;
    }
    /** coron表达式 */
    public void setCron(String cron) {
        this.cron = cron;
    }
    /** 开始时间 */
    public String getStartDate() {
        return this.startDate;
    }
    /** 开始时间 */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /** 结束时间 */
    public String getStopDate() {
        return this.stopDate;
    }
    /** 结束时间 */
    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }
    /** 运行次数 */
    public String getRunCount() {
        return this.runCount;
    }
    /** 运行次数 */
    public void setRunCount(String runCount) {
        this.runCount = runCount;
    }
    /** 运行总时间单位秒 */
    public String getRunCountDate() {
        return this.runCountDate;
    }
    /** 运行总时间单位秒 */
    public void setRunCountDate(String runCountDate) {
        this.runCountDate = runCountDate;
    }
    /** 发布状态(0-停用 1-可用) */
    public String getJobStatus() {
        return this.jobStatus;
    }
    /** 发布状态(0-停用 1-可用) */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    /** 运行状态(0-停止运行 1-正在运行 2-暂停运行) */
    public String getOperaStatus() {
        return this.operaStatus;
    }
    /** 运行状态(0-停止运行 1-正在运行 2-暂停运行) */
    public void setOperaStatus(String operaStatus) {
        this.operaStatus = operaStatus;
    }
    /** 方法参数 */
    public String getMethodData() {
        return this.methodData;
    }
    /** 方法参数 */
    public void setMethodData(String methodData) {
        this.methodData = methodData;
    }
    /** 方法参数类型 */
    public String getMethodDataType() {
        return this.methodDataType;
    }
    /** 方法参数类型 */
    public void setMethodDataType(String methodDataType) {
        this.methodDataType = methodDataType;
    }
    /** 运行方法名 */
    public String getMethodName() {
        return this.methodName;
    }
    /** 运行方法名 */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    /** 运行方法名所属于的bean */
    public String getBeanName() {
        return this.beanName;
    }
    /** 运行方法名所属于的bean */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    /** 运行方法名所属于的bean的路径 */
    public String getBeanPath() {
        return this.beanPath;
    }
    /** 运行方法名所属于的bean的路径 */
    public void setBeanPath(String beanPath) {
        this.beanPath = beanPath;
    }
    /** 是否在项目运行时启动一次，一般用于项目调试时候用(0-否 1-是) */
    public String getSelfStart() {
        return this.selfStart;
    }
    /** 是否在项目运行时启动一次，一般用于项目调试时候用(0-否 1-是) */
    public void setSelfStart(String selfStart) {
        this.selfStart = selfStart;
    }
    /** 任务描述 */
    public String getDesc() {
        return this.desc;
    }
    /** 任务描述 */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /** 创建时间 */
    public String getCreateDate() {
        return this.createDate;
    }
    /** 创建时间 */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    /** 修改时间 */
    public String getModifyDate() {
        return this.modifyDate;
    }
    /** 修改时间 */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    /** 重写后的toString方法 */
    @Override
    public String toString() {
        return "TimedTaskPO：[id=" + id + ", name=" + name + ", group=" + group + ", sysGroup=" + sysGroup
                 + "cron=" + cron + ", startDate=" + startDate + ", stopDate=" + stopDate + ", runCount=" + runCount + ", runCountDate=" + runCountDate
                 + "jobStatus=" + jobStatus + ", operaStatus=" + operaStatus + ", methodData=" + methodData + ", methodDataType=" + methodDataType + ", methodName=" + methodName
                 + "beanName=" + beanName + ", beanPath=" + beanPath + ", selfStart=" + selfStart + ", desc=" + desc + ", createDate=" + createDate
                 + "modifyDate=" + modifyDate + "]";
    }

}