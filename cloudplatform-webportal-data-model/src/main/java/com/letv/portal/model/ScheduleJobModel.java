package com.letv.portal.model;

import com.letv.common.model.BaseModel;

/**Program Name: ScheduleJob <br>
 * Description:  计划任务<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月16日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class ScheduleJobModel extends BaseModel {
	
	private static final long serialVersionUID = 5442709506073155459L;
	
	private String jobName;
	private String jobGroup;
	private String jobStatus;
	private String cronExpression;
	private String descn;
	
	private String jobClass;
	private String jobMethod;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getJobMethod() {
		return jobMethod;
	}
	public void setJobMethod(String jobMethod) {
		this.jobMethod = jobMethod;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

}
