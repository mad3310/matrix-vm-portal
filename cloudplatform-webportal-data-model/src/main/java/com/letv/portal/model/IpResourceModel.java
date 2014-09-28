package com.letv.portal.model;



/**Program Name: IpResourceModel <br>
 * Description:  可使用IP<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class IpResourceModel extends BaseModel {
	
	private static final long serialVersionUID = 2955890158317393639L;

	private String ip;
	private String gateWay;
	private String mask;
	
	private String status; //状态 0：未使用 1：已使用
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGateWay() {
		return gateWay;
	}
	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
