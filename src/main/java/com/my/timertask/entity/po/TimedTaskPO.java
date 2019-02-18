package com.my.timertask.entity.po;

public class TimedTaskPO {
    public TimedTaskPO() {}
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
    * @param runCountDate - 运行时间
    * @param jobStatus - 发布状态(0-停用 1-可用)
    * @param operaStatus - 运行状态(0-停止运行 1-正在运行 2-暂停运行)
    * @param methodData - 方法参数
    * @param methodDataType - 方法参数类型
    * @param methodName - 运行方法名
    * @param beanName - 运行方法名所属于的bean
    * @param beanPath - 运行方法名所属于的bean的路径
    * @param selfStart - 是否项目启动时启动(0-否 1-是)
    * @param desc - 任务描述
    * @param createDate - 创建时间
    * @param modifyDate - 修改时间
    */
    public TimedTaskPO(Integer id, String name, String group, String sysGroup, String cron, String startDate,
            String stopDate, String runCount, String runCountDate, String jobStatus, String operaStatus,
            String methodData, String methodDataType, String methodName, String beanName, String beanPath,
            String selfStart, String desc, String createDate, String modifyDate)
    {
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
    /** */
    public Integer getId() {
        return id;
    }
    /** */
    public String getName() {
        return name;
    }
    /** */
    public String getGroup() {
        return group;
    }
    /** */
    public String getSysGroup() {
        return sysGroup;
    }
    /** */
    public String getCron() {
        return cron;
    }
    /** */
    public String getStartDate() {
        return startDate;
    }
    /** */
    public String getStopDate() {
        return stopDate;
    }
    /** */
    public String getRunCount() {
        return runCount;
    }
    /** */
    public String getRunCountDate() {
        return runCountDate;
    }
    /** */
    public String getJobStatus() {
        return jobStatus;
    }
    /** */
    public String getOperaStatus() {
        return operaStatus;
    }
    /** */
    public String getMethodData() {
        return methodData;
    }
    /** */
    public String getMethodDataType() {
        return methodDataType;
    }
    /** */
    public String getMethodName() {
        return methodName;
    }
    /** */
    public String getBeanName() {
        return beanName;
    }
    /** */
    public String getBeanPath() {
        return beanPath;
    }
    /** */
    public String getSelfStart() {
        return selfStart;
    }
    /** */
    public String getDesc() {
        return desc;
    }
    /** */
    public String getCreateDate() {
        return createDate;
    }
    /** */
    public String getModifyDate() {
        return modifyDate;
    }
    /** */
    public void setId(Integer id) {
        this.id = id;
    }
    /** */
    public void setName(String name) {
        this.name = name;
    }
    /** */
    public void setGroup(String group) {
        this.group = group;
    }
    /** */
    public void setSysGroup(String sysGroup) {
        this.sysGroup = sysGroup;
    }
    /** */
    public void setCron(String cron) {
        this.cron = cron;
    }
    /** */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /** */
    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }
    /** */
    public void setRunCount(String runCount) {
        this.runCount = runCount;
    }
    /** */
    public void setRunCountDate(String runCountDate) {
        this.runCountDate = runCountDate;
    }
    /** */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    /** */
    public void setOperaStatus(String operaStatus) {
        this.operaStatus = operaStatus;
    }
    /** */
    public void setMethodData(String methodData) {
        this.methodData = methodData;
    }
    /** */
    public void setMethodDataType(String methodDataType) {
        this.methodDataType = methodDataType;
    }
    /** */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    /** */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    /** */
    public void setBeanPath(String beanPath) {
        this.beanPath = beanPath;
    }
    /** */
    public void setSelfStart(String selfStart) {
        this.selfStart = selfStart;
    }
    /** */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /** */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    /** */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
    /** <blockquote>
    *
    * @return 
    */
    @Override
    public String toString() {
        return "TimedTaskPO [id=" + id + ", name=" + name + ", group=" + group + ", sysGroup=" + sysGroup + ", cron="
                + cron + ", startDate=" + startDate + ", stopDate=" + stopDate + ", runCount=" + runCount
                + ", runCountDate=" + runCountDate + ", jobStatus=" + jobStatus + ", operaStatus=" + operaStatus
                + ", methodData=" + methodData + ", methodDataType=" + methodDataType + ", methodName=" + methodName
                + ", beanName=" + beanName + ", beanPath=" + beanPath + ", selfStart=" + selfStart + ", desc=" + desc
                + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
    }

    

    
}
