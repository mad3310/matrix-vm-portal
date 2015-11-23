package com.letv.portal.model.message;

import java.util.Date;

/**
 * 消息通知类
 *
 */
public class Message {

	//消息ID
    private Long id;
    //消息标题
    private String msgTitle;
    //消息内容
    private String msgContent;
    //创建日期
    private Date createdTime;
	//修改日期
    private Date lastUpdateTime;
    //消息类型(0:系统消息，1：个人消息，2：批量发送)
    private String msgType;
    //消息状态
    private String msgStatus;
    
    public Date getCreatedTime() {
		return createdTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public String getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
