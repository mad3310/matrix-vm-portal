package com.letv.portal.model;

import java.util.Date;

import com.letv.common.model.BaseModel;


/**Program Name: BuildModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class BuildModel extends BaseModel {
	
	private static final long serialVersionUID = 3290439855942720161L;
	
	private Long mclusterId;
	private Long dbId;
	private Integer step;
	private String stepMsg;
	private Integer status;
	private String code;
	private String msg;

	private Date startTime;
	private Date endTime;
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
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public String getStepMsg() {
		return stepMsg;
	}
	public void setStepMsg(String stepMsg) {
		this.stepMsg = stepMsg;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	@Override
	public String toString() {
		return "BuildModel [mclusterId=" + mclusterId + ", dbId=" + dbId
				+ ", step=" + step + ", stepMsg=" + stepMsg + ", status="
				+ status + ", code=" + code + ", msg=" + msg + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

}
