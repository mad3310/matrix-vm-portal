package com.letv.portal.model.timing.task;

import java.util.List;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.OssServerVisibility;

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
	private String execType;
	private String descn;
	
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
	public String getExecType() {
		return execType;
	}
	public void setExecType(String execType) {
		this.execType = execType;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
}
