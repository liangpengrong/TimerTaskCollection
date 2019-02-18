package com.my.timertask.entity.dto;

public class SendEmailDTO {
    public SendEmailDTO () {}
    private String host;
    private String username;
    private String toUser;
    private String title;
    private String content;
    private String warrantCode;
    private String encoding;
    private String port;
    private String protocol;
    private String emailType;
    private String annex;
    private String rscPath;
    private String rscId;

    public SendEmailDTO(String host, String username, String toUser, String title, String content, String warrantCode,
            String encoding, String port, String protocol, String emailType, String annex, String rscPath, String rscId)
    {
        super();
        this.host = host;
        this.username = username;
        this.toUser = toUser;
        this.title = title;
        this.content = content;
        this.warrantCode = warrantCode;
        this.encoding = encoding;
        this.port = port;
        this.protocol = protocol;
        this.emailType = emailType;
        this.annex = annex;
        this.rscPath = rscPath;
        this.rscId = rscId;
    }
    /**  */
    public String getHost() {
        return host;
    }
    /**  */
    public String getUsername() {
        return username;
    }
    /**  */
    public String getToUser() {
        return toUser;
    }
    /**  */
    public String getTitle() {
        return title;
    }
    /**  */
    public String getContent() {
        return content;
    }
    /**  */
    public String getEmailType() {
        return emailType;
    }
    /**  */
    public String getAnnex() {
        return annex;
    }
    /**  */
    public String getWarrantCode() {
        return warrantCode;
    }
    /**  */
    public String getEncoding() {
        return encoding;
    }
    /**  */
    public String getPort() {
        return port;
    }
    /**  */
    public String getProtocol() {
        return protocol;
    }
    /**  */
    public void setHost(String host) {
        this.host = host;
    }
    /**  */
    public void setUsername(String username) {
        this.username = username;
    }
    /**  */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
    /**  */
    public void setTitle(String title) {
        this.title = title;
    }
    /**  */
    public void setContent(String content) {
        this.content = content;
    }
    /**  */
    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }
    /**  */
    public void setAnnex(String annex) {
        this.annex = annex;
    }
    /**  */
    public void setWarrantCode(String warrantCode) {
        this.warrantCode = warrantCode;
    }
    /**  */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    /**  */
    public void setPort(String port) {
        this.port = port;
    }
    /**  */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    /**  */
    public String getRscPath() {
        return rscPath;
    }
    /**  */
    public String getRscId() {
        return rscId;
    }
    /**  */
    public void setRscPath(String rscPath) {
        this.rscPath = rscPath;
    }
    /**  */
    public void setRscId(String rscId) {
        this.rscId = rscId;
    }
   
}
