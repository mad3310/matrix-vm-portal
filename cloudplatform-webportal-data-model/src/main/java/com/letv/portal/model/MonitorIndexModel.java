package com.letv.portal.model;

import com.letv.common.model.BaseModel;
/**
 * Program Name: MonitorIndexModel <br>
 * Description: 监控索引表 <br>
 * @author name: wujun <br>
 * Written Date: 2014年11月11日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class MonitorIndexModel extends BaseModel{

	private static final long serialVersionUID = 1L;
	private String titleText;
	private String subTitleText;
	private String yAxisText;
	private String tooltipSuffix;
	private int  flushTime;
	private String  detailTable;
	private int  status;
	public String getTitleText() {
		return titleText;
	}
	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}
	public String getSubTitleText() {
		return subTitleText;
	}
	public void setSubTitleText(String subTitleText) {
		this.subTitleText = subTitleText;
	}
	public String getyAxisText() {
		return yAxisText;
	}
	public void setyAxisText(String yAxisText) {
		this.yAxisText = yAxisText;
	}
	public String getTooltipSuffix() {
		return tooltipSuffix;
	}
	public void setTooltipSuffix(String tooltipSuffix) {
		this.tooltipSuffix = tooltipSuffix;
	}
	public int getFlushTime() {
		return flushTime;
	}
	public void setFlushTime(int flushTime) {
		this.flushTime = flushTime;
	}
	public String getDetailTable() {
		return detailTable;
	}
	public void setDetailTable(String detailTable) {
		this.detailTable = detailTable;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
