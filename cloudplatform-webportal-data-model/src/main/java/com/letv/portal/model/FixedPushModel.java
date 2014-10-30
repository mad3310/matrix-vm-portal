package com.letv.portal.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Program Name: FixedPushModel <br>
 * Description:  <br>
 * @author name: wujun <br>
 * Written Date: 2014年10月20日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class FixedPushModel {
  private String servertag;
  private List<ContainerPush> ipaddress = new ArrayList<ContainerPush>();
public String getServertag() {
	return servertag;
}
public void setServertag(String servertag) {
	this.servertag = servertag;
}

 
  public List<ContainerPush> getIpaddress() {
	return ipaddress;
}
public void setIpaddress(List<ContainerPush> ipaddress) {
	this.ipaddress = ipaddress;
}




  
}
