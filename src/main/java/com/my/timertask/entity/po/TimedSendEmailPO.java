package com.my.timertask.entity.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimedSendEmailPO implements Serializable{
    /**  */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String host;
    private String username;
    private String warrantCode;
    private String port;
    private String protocol;
    private String toUser;
    private String eTitle;
    private String eContent;
    private String eType;
    private String eAnnex;
    private String createDate;
    private String createUserId;
    private String name;
    private String group;
    private String sysGroup;
    private String cron;
    private String startDate;
    private String stopDate;
    private String modifyDate;
    private String modifyUserId;
    public TimedSendEmailPO(){}
    /** <blockquote>
    * 
    * @param id
    * @param host
    * @param username
    * @param warrantCode
    * @param port
    * @param protocol
    * @param toUser
    * @param eTitle
    * @param eContent
    * @param eType
    * @param eAnnex
    * @param createDate
    * @param createUserId
    * @param name
    * @param group
    * @param sysGroup
    * @param cron
    * @param startDate
    * @param stopDate
    * @param modifyDate
    * @param modifyUserId
    */
    public TimedSendEmailPO(Integer id, String host, String username, String warrantCode, String port, String protocol,
            String toUser, String eTitle, String eContent, String eType, String eAnnex, String createDate,
            String createUserId, String name, String group, String sysGroup, String cron, String startDate,
            String stopDate, String modifyDate, String modifyUserId)
    {
        super();
        this.id = id;
        this.host = host;
        this.username = username;
        this.warrantCode = warrantCode;
        this.port = port;
        this.protocol = protocol;
        this.toUser = toUser;
        this.eTitle = eTitle;
        this.eContent = eContent;
        this.eType = eType;
        this.eAnnex = eAnnex;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.name = name;
        this.group = group;
        this.sysGroup = sysGroup;
        this.cron = cron;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.modifyDate = modifyDate;
        this.modifyUserId = modifyUserId;
    }
    /** */
    public Integer getId() {
        return id;
    }
    /** */
    public String getHost() {
        return host;
    }
    /** */
    public String getUsername() {
        return username;
    }
    /** */
    public String getWarrantCode() {
        return warrantCode;
    }
    /** */
    public String getPort() {
        return port;
    }
    /** */
    public String getProtocol() {
        return protocol;
    }
    /** */
    public String getToUser() {
        return toUser;
    }
    /** */
    public String geteTitle() {
        return eTitle;
    }
    /** */
    public String geteContent() {
        return eContent;
    }
    /** */
    public String geteType() {
        return eType;
    }
    /** */
    public String geteAnnex() {
        return eAnnex;
    }
    /** */
    public String getCreateDate() {
        return createDate;
    }
    /** */
    public String getCreateUserId() {
        return createUserId;
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
    public String getModifyDate() {
        return modifyDate;
    }
    /** */
    public String getModifyUserId() {
        return modifyUserId;
    }
    /** */
    public void setId(Integer id) {
        this.id = id;
    }
    /** */
    public void setHost(String host) {
        this.host = host;
    }
    /** */
    public void setUsername(String username) {
        this.username = username;
    }
    /** */
    public void setWarrantCode(String warrantCode) {
        this.warrantCode = warrantCode;
    }
    /** */
    public void setPort(String port) {
        this.port = port;
    }
    /** */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    /** */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
    /** */
    public void seteTitle(String eTitle) {
        this.eTitle = eTitle;
    }
    /** */
    public void seteContent(String eContent) {
        this.eContent = eContent;
    }
    /** */
    public void seteType(String eType) {
        this.eType = eType;
    }
    /** */
    public void seteAnnex(String eAnnex) {
        this.eAnnex = eAnnex;
    }
    /** */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    /** */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
    /** */
    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
    /** <blockquote>
    *
    * @return 
    */
    @Override
    public String toString() {
        return "TimedSendEmailPO [id=" + id + ", host=" + host + ", username=" + username + ", warrantCode="
                + warrantCode + ", port=" + port + ", protocol=" + protocol + ", toUser=" + toUser + ", eTitle="
                + eTitle + ", eContent=" + eContent + ", eType=" + eType + ", eAnnex=" + eAnnex + ", createDate="
                + createDate + ", createUserId=" + createUserId + ", name=" + name + ", group=" + group + ", sysGroup="
                + sysGroup + ", cron=" + cron + ", startDate=" + startDate + ", stopDate=" + stopDate + ", modifyDate="
                + modifyDate + ", modifyUserId=" + modifyUserId + "]";
    }
    
}
