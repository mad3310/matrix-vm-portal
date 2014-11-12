package com.letv.portal.model;

import java.sql.Timestamp;

import com.letv.common.model.BaseModel;
/**
 * Program Name: MonitorDetailModel <br>
 * Description: 监控服务数据表 <br>
 * @author name: wujun <br>
 * Written Date: 2014年11月11日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class MonitorDetailModel extends BaseModel {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	private String detailName;
	private String detailValue;
	private String dbName;
	private Timestamp monitorDate;
	private String containerIp;

	
		
	public String getContainerIp() {
		return containerIp;
	}

	public void setContainerIp(String containerIp) {
		this.containerIp = containerIp;
	}

	public String getDetailName() {
		return detailName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getDetailValue() {
		return detailValue;
	}

	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}

	public Timestamp getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(Timestamp monitorDate) {
		this.monitorDate = monitorDate;
	}
    
}
