package com.letv.portal.model.monitor;

import java.util.ArrayList;
import java.util.List;

public class MonitorViewModel {
	private List<String> xdata =new ArrayList<String>();
	private List<MonitorViewYModel> ydata;
   
	public List<String> getXdata() {
		return xdata;
	}
	public void setXdata(List<String> xdata) {
		this.xdata = xdata;
	}
	public List<MonitorViewYModel> getYdata() {
		return ydata;
	}
	public void setYdata(List<MonitorViewYModel> ydata) {
		this.ydata = ydata;
	}

	
	
}
