package com.letv.portal.model.timing.task;

import java.util.List;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.OssServerVisibility;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;

/**Program Name: SwiftServer <br>
 * Description:  swift服务<br>
 * @author name: howie <br>
 * Written Date: 2015年5月5日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class BaseTimingTask extends BaseModel {

	private static final long serialVersionUID = -6001625682102702983L;

	private String name;
	private Long storeSize;
	private Integer status;
	private OssServerVisibility visibilityLevel;
	private String descn;
	
	private Long hclusterId;
	private HclusterModel hcluster;
	
	private List <HostModel> hosts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStoreSize() {
		return storeSize;
	}

	public void setStoreSize(Long storeSize) {
		this.storeSize = storeSize;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Long getHclusterId() {
		return hclusterId;
	}

	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}

	public HclusterModel getHcluster() {
		return hcluster;
	}

	public void setHcluster(HclusterModel hcluster) {
		this.hcluster = hcluster;
	}

	public OssServerVisibility getVisibilityLevel() {
		return visibilityLevel;
	}

	public void setVisibilityLevel(OssServerVisibility visibilityLevel) {
		this.visibilityLevel = visibilityLevel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<HostModel> getHosts() {
		return hosts;
	}

	public void setHosts(List<HostModel> hosts) {
		this.hosts = hosts;
	}
}
