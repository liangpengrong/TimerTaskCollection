package com.my.timertask.util.rabbitmq;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SocketMessage implements Serializable {
    private static final long serialVersionUID = -8221467966772683998L;
    private String id;
    private String senderUser;
    private String receiverUser;
    private String content;
    private Date sendTime;
    private Date readTime;
    /** */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    /** */
    public String getId() {
        return id;
    }
    /** */
    public String getSenderUser() {
        return senderUser;
    }
    /** */
    public String getReceiverUser() {
        return receiverUser;
    }
    /** */
    public String getContent() {
        return content;
    }
    /** */
    public Date getSendTime() {
        return sendTime;
    }
    /** */
    public Date getReadTime() {
        return readTime;
    }
    /** */
    public void setId(String id) {
        this.id = id;
    }
    /** */
    public void setSenderUser(String senderUser) {
        this.senderUser = senderUser;
    }
    /** */
    public void setReceiverUser(String receiverUser) {
        this.receiverUser = receiverUser;
    }
    /** */
    public void setContent(String content) {
        this.content = content;
    }
    /** */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    /** */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }
    @Override
    public String toString() {
        return "SocketMessage [id=" + id + ", senderUser=" + senderUser + ", receiverUser=" + receiverUser
                + ", content=" + content + ", sendTime=" + sendTime + ", readTime=" + readTime + "]";
    }
}