package com.letv.portal.model;

import java.util.Date;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.BackupStatus;

/**Program Name: BackUpResultModel <br>
 * Description:  数据库定时任务：备份结果记录<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年1月2日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class BackupResultModel extends BaseModel {

	private static final long serialVersionUID = -1054236468505005030L;

	private Long mclusterId;
	private Long dbId;
	private String backupIp;
	private Date startTime;
	private Date endTime;
	private BackupStatus status;
	private String resultDetail;
	
	private MclusterModel mcluster;
	private DbModel db;

	public Long getMclusterId() {
		return mclusterId;
	}
	public void setMclusterId(Long mclusterId) {
		this.mclusterId = mclusterId;
	}
	public Long getDbId() {
		return dbId;
	}
	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}
	public String getBackupIp() {
		return backupIp;
	}
	public void setBackupIp(String backupIp) {
		this.backupIp = backupIp;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public BackupStatus getStatus() {
		return status;
	}
	public void setStatus(BackupStatus status) {
		this.status = status;
	}
	public MclusterModel getMcluster() {
		return mcluster;
	}
	public void setMcluster(MclusterModel mcluster) {
		this.mcluster = mcluster;
	}
	public String getResultDetail() {
		return resultDetail;
	}
	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}
	public DbModel getDb() {
		return db;
	}
	public void setDb(DbModel db) {
		this.db = db;
	}
}
