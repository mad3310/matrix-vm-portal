/*
 * @Title: LogBean.java
 * @Package com.letv.log.bean
 * @Description: TODO
 * @author xufei1 <xufei1@letv.com>
 * @date 2012-12-5 下午11:28:00
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-5                          
 */
package com.letv.log.bean;

import java.util.Date;

import com.letv.log.util.StringUtil;

/**
 * <p>
 * 该类主要描述日志消息的字段内容
 * </p>
 * 
 * @author xufei <xufei1@letv.com> Create at:2012-12-5 下午23:28:00
 */
public class LogBean {
	private String account;// 账号
	private String businessCode;// 业务编码
	private String destinationIp;// 目的方ip
	private String destinationPort;// 目的方端口
	private String errorCode;// 错误编码
	private String errorLineNum;// 错误行号
	private String errorResult;// 错误结果
	private String happpenTime;// 发生时间
	private String keywords;// 关键字
	private String loglevel;// 日志级别
	private String moduleCode;// 本模块编码
	private String originalArgumentList;// 原参数列表
	private String requestBusinessCode;// 请求端业务代码
	private String requestIp;// 请求方ip
	private String requestModuleCode;// 请求模块编码
	private String requestPort;// 请求方端口
	private String sequenceNum;// 顺序号
	private String usedMethodName;// 调用方法名
	private String userInfo;// 用户信息
	private String useTime;// 耗时

	public LogBean() {
		this.happpenTime = StringUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss ");
		this.sequenceNum = String.valueOf(System.currentTimeMillis());
	}

	public String getAccount() {
		return account;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public String getDestinationIp() {
		return destinationIp;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorLineNum() {
		return errorLineNum;
	}

	public String getErrorResult() {
		return errorResult;
	}

	public String getHapppenTime() {
		return happpenTime;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getLoglevel() {
		return loglevel;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public String getOriginalArgumentList() {
		return originalArgumentList;
	}

	public String getRequestBusinessCode() {
		return requestBusinessCode;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public String getRequestModuleCode() {
		return requestModuleCode;
	}

	public String getRequestPort() {
		return requestPort;
	}

	public String getSequenceNum() {
		return sequenceNum;
	}

	public String getUsedMethodName() {
		return usedMethodName;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorLineNum(String errorLineNum) {
		this.errorLineNum = errorLineNum;
	}

	public void setErrorResult(String errorResult) {
		this.errorResult = errorResult;
	}

	public void setHapppenTime(String happpenTime) {
		this.happpenTime = happpenTime;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public void setOriginalArgumentList(String originalArgumentList) {
		this.originalArgumentList = originalArgumentList;
	}

	public void setRequestBusinessCode(String requestBusinessCode) {
		this.requestBusinessCode = requestBusinessCode;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public void setRequestModuleCode(String requestModuleCode) {
		this.requestModuleCode = requestModuleCode;
	}

	public void setRequestPort(String requestPort) {
		this.requestPort = requestPort;
	}
	/**
	 * 
	 * 
	 * <p>默认值为当前系统时间的long</p>
	 * @param sequenceNum
	 */
	public void setSequenceNum(String sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public void setUsedMethodName(String usedMethodName) {
		this.usedMethodName = usedMethodName;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Message:[account=");
		result.append(this.account);
		result.append(", businessCode=");
		result.append(this.businessCode);
		result.append(", destinationIp=");
		result.append(this.destinationIp);
		result.append(", destinationPort=");
		result.append(this.destinationPort);
		result.append(", errorCode=");
		result.append(this.errorCode);
		result.append(", errorLineNum=");
		result.append(this.errorLineNum);
		result.append(", errorResult=");
		result.append(this.errorResult);
		result.append(", happpenTime=");
		result.append(this.happpenTime);
		result.append(", keywords=");
		result.append(this.keywords);
		result.append(", loglevel=");
		result.append(this.loglevel);
		result.append(", moduleCode=");
		result.append(this.moduleCode);
		result.append(", originalArgumentList=");
		result.append(this.originalArgumentList);
		result.append(", requestBusinessCode=");
		result.append(this.requestBusinessCode);
		result.append(", requestIp=");
		result.append(this.requestIp);
		result.append(", requestModuleCode=");
		result.append(this.requestModuleCode);
		result.append(", requestPort=");
		result.append(this.requestPort);
		result.append(", sequenceNum=");
		result.append(this.sequenceNum);
		result.append(", usedMethodName=");
		result.append(this.usedMethodName);
		result.append(", userInfo=");
		result.append(this.userInfo);
		result.append(", useTime=");
		result.append(this.useTime);
		result.append("]");
		return result.toString();
	}
}