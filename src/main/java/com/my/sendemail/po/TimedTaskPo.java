package com.my.sendemail.po;

public class TimedTaskPo {
    public TimedTaskPo() {}
    /** 主键自增id */
    private Integer id;
    /** 任务名 */
    private String name;
    /** 任务组名 */
    private String groupName;
    /** 开始时间 yyyy-MM-dd HH:mm:ss*/
    private String startTime;
    /** 结束时间 yyyy-MM-dd HH:mm:ss*/
    private String endTime;
    /** coron表达式 */
    private String cron;
    /** 发布状态 */
    private String jobStatus;
    /** 计划状态 */
    private String planStatus;
    /** 运行状态(0-立即运行 1-正在运行 2-暂停运行 3-停止运行) */
    private String operaStatus;
    /** 参数 */
    private String jobData;
    /** 运行方法名 */
    private String methodName;
    /** 运行方法名所属于的bean */
    private String beanName;
    /** 任务描述 */
    private String description;
    /** 创建者id */
    private String createUserId;
    /** 创建时间 */
    private String createDate;
    /** 修改者id */
    private String modifyUserId;
    /** 修改时间 */
    private String modifyDate;
    
    /** <blockquote>
    * 
    * @param id - 主键自增id
    * @param name - 任务名
    * @param groupName - 任务组名
    * @param startTime - 开始时间
    * @param endTime - 结束时间
    * @param cron - 启动时间
    * @param jobStatus - coron表达式
    * @param planStatus - 发布状态
    * @param isConcurrent - 计划状态
    * @param jobData - 运行状态(0-立即运行 1-正在运行 2-暂停运行 3-停止运行)
    * @param methodName - 参数
    * @param beanName - 运行方法名
    * @param description - 运行方法名所属于的bean
    * @param createUserId - 任务描述
    * @param createDate - 创建者id
    * @param modifyUserId - 创建时间
    * @param modifyDate - 修改者id
    * @param  - 修改时间
    */
    public TimedTaskPo(Integer id,String name,String groupName,String startTime,String endTime,String cron,String jobStatus,String planStatus,String operaStatus,String jobData,String methodName,String beanName,String description,String createUserId,String createDate,String modifyUserId,String modifyDate) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cron = cron;
        this.jobStatus = jobStatus;
        this.planStatus = planStatus;
        this.operaStatus = operaStatus;
        this.jobData = jobData;
        this.methodName = methodName;
        this.beanName = beanName;
        this.description = description;
        this.createUserId = createUserId;
        this.createDate = createDate;
        this.modifyUserId = modifyUserId;
        this.modifyDate = modifyDate;
    }
    /**  */
    public Integer getId() {
        return id;
    }
    /**  */
    public String getName() {
        return name;
    }
    /**  */
    public String getGroupName() {
        return groupName;
    }
    /**  */
    public String getStartTime() {
        return startTime;
    }
    /**  */
    public String getEndTime() {
        return endTime;
    }
    /**  */
    public String getCron() {
        return cron;
    }
    /**  */
    public String getJobStatus() {
        return jobStatus;
    }
    /**  */
    public String getPlanStatus() {
        return planStatus;
    }
    /**  */
    public String getOperaStatus() {
        return operaStatus;
    }
    /**  */
    public String getJobData() {
        return jobData;
    }
    /**  */
    public String getMethodName() {
        return methodName;
    }
    /**  */
    public String getBeanName() {
        return beanName;
    }
    /**  */
    public String getDescription() {
        return description;
    }
    /**  */
    public String getCreateUserId() {
        return createUserId;
    }
    /**  */
    public String getCreateDate() {
        return createDate;
    }
    /**  */
    public String getModifyUserId() {
        return modifyUserId;
    }
    /**  */
    public String getModifyDate() {
        return modifyDate;
    }
    /**  */
    public void setId(Integer id) {
        this.id = id;
    }
    /**  */
    public void setName(String name) {
        this.name = name;
    }
    /**  */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**  */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    /**  */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    /**  */
    public void setCron(String cron) {
        this.cron = cron;
    }
    /**  */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
    /**  */
    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }
    /**  */
    public void setOperaStatus(String operaStatus) {
        this.operaStatus = operaStatus;
    }
    /**  */
    public void setJobData(String jobData) {
        this.jobData = jobData;
    }
    /**  */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    /**  */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    /**  */
    public void setDescription(String description) {
        this.description = description;
    }
    /**  */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    /**  */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    /**  */
    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
    /**  */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }



}
