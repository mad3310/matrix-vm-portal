package com.letv.portal.model.timing.task;

import java.util.List;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.OssServerVisibility;
import com.letv.portal.enumeration.TimingTaskExecType;
import com.letv.portal.enumeration.TimingTaskType;

/**Program Name: BaseTimingTask <br>
 * Description:  定时任务<br>
 * @author name: howie <br>
 * Written Date: 2015年5月5日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class BaseTimingTask extends BaseModel {

	private static final long serialVersionUID = -6001625682102702983L;

	private String name;
	private String uuid;
	private String httpMethod;
	private String url;
	private String timingRule;
	private TimingTaskExecType execType;
	private String descn;
	private TimingTaskType type;
	private String timePoint;
	private String timeInterval;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTimingRule() {
		return timingRule;
	}
	public void setTimingRule(String timingRule) {
		this.timingRule = timingRule;
	}
	public TimingTaskExecType getExecType() {
		return execType;
	}
	public void setExecType(TimingTaskExecType execType) {
		this.execType = execType;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public TimingTaskType getType() {
		return type;
	}
	public void setType(TimingTaskType type) {
		this.type = type;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	
}
