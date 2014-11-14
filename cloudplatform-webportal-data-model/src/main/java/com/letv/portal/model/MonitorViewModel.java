package com.letv.portal.model;

import java.util.ArrayList;
import java.util.List;

public class MonitorViewModel {
	private String title;
	private String ytitle;
	private String unit;
	private List<String> xdata =new ArrayList<String>();
	private List<MonitorViewYModel> ydata;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYtitle() {
		return ytitle;
	}
	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
   
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
