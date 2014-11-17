package com.letv.portal.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IMonitorDao;
import com.letv.portal.dao.IMonitorViewDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.MonitorTimeModel;
import com.letv.portal.model.MonitorViewModel;
import com.letv.portal.model.MonitorViewYModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;

@Service("monitorService")
public class MonitorServiceImpl extends BaseServiceImpl<MonitorDetailModel> implements IMonitorService {

	@Autowired
	private IMonitorDao monitorDao;
	
	@Autowired
	private IMonitorViewDao monitorViewDao;
	
	@Autowired
	private IMonitorIndexService monitorIndexService;
	
	@Autowired
	private IContainerService containerService;
	
	public MonitorServiceImpl() {
		super(MonitorDetailModel.class);
	}

	
	@Override
	public IBaseDao<MonitorDetailModel> getDao() {
		// TODO Auto-generated method stub
		return this.monitorDao;
	}
	public List<String> selectDistinct(Map map){
		return this.monitorDao.selectDistinct(map);
	}
	
	

	@Override
	public MonitorViewModel getMonitorViewData(Long mclusterId,Long chartId,MonitorTimeModel monitorTimeModel) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("mclusterId", mclusterId);
	    map.put("type", "mclusternode");
	    List<ContainerModel> containers = this.containerService.selectByMap(map);	  
	    
	    MonitorIndexModel monitorIndexModel  = this.monitorIndexService.selectById(chartId);	   
	    String dataTable = monitorIndexModel.getDetailTable();

	    
	    
	    /*
	     * 1、获取初始化数据 ，传给前台
	     */
	    
	    Map<String, Object> indexParams = new HashMap<String, Object>();
	    indexParams.put("dbName",monitorIndexModel.getDetailTable());
	    
	    
	    List<String> detailNames =  this.monitorDao.selectDistinct(indexParams);
	    
	    
	    
	    
	    Map<String, Object> dateMap = anaysiDate(monitorTimeModel.getStrategy());
	    List<String> dateXList =(List<String>) dateMap.get("x");
	    List<String> dateYList =(List<String>) dateMap.get("y");
		Collections.reverse(dateXList);
		Collections.reverse(dateYList);
		List<String> xdateList = new ArrayList<String>(dateXList);
		xdateList.remove(xdateList.size()-1);
		List<MonitorViewYModel> monitorViewYModel = new ArrayList<MonitorViewYModel>();
		MonitorViewModel monitorViewModel =  new MonitorViewModel();
		Map<String, Object> mapMonitor = new HashMap<String, Object>();
		
	    for(ContainerModel c:containers){	    		
	    	 for(String s:detailNames){		    	
	    	    	mapMonitor.put("containerIp", c.getIpAddr());
	    	    	mapMonitor.put("detailName", s);
	    	    	mapMonitor.put("dbName", monitorIndexModel.getDetailTable());
	    	    	
	    	    	List<MonitorDetailModel> listMonitorDetailModels =  selectMonitorDetailData(dateYList,mapMonitor);
	    	    	
	    	    	List<Float> listStrings = new ArrayList<Float>();
	    	    	for(MonitorDetailModel m:listMonitorDetailModels){	
	    	    		if(m==null){
	    	    			listStrings.add(null);
	    	    		}else {
		    	    		listStrings.add(m.getDetailValue());	
						}
	    	    	}	    	    
    	    	  	MonitorViewYModel Y = new MonitorViewYModel();
    	    	 	Y.setData(listStrings);  
	    	       	Y.setName(c.getIpAddr()+"-"+s);
	    	       	Y.setType("spline");
	 	    	   
	    	     	monitorViewYModel.add(Y);
	    	 }
 	    	
 	    	monitorViewModel.setXdata(xdateList);//x
	    }
	    monitorViewModel.setTitle(monitorIndexModel.getTitleText());
	    monitorViewModel.setYtitle(monitorIndexModel.getyAxisText());
	    monitorViewModel.setUnit(monitorIndexModel.getTooltipSuffix());
	    monitorViewModel.setYdata(monitorViewYModel);//y

		return monitorViewModel;
	}
	public List<MonitorDetailModel> selectDateTime(Map map){
		return  this.monitorDao.selectDateTime(map);
	}
    /**
     * Methods Name: selectData <br>
     * Description: 查询时间段的数据<br>
     * @author name: wujun
     * @return
     */
    public Map anaysiDate(Integer strategy){
           SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");  
           SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	   Date d = new Date();       	   
           Calendar now = Calendar.getInstance();  
           now.setTime(d); 
           HashMap<String, Object> map = new HashMap<String, Object>();
           List<String> listx = new ArrayList<String>();
           List<String> listy = new ArrayList<String>();
        	   switch (strategy) {
			    case 1:
			           for(int i=0;i<31;i++){
				          now.add(Calendar.MINUTE, -2);
				          listx.add(sdf.format(now.getTime()));
				          listy.add(sdf1.format(now.getTime()));
			           }
				break;
			    case 2:
			           for(int i=0;i<31;i++){
			        	   now.add(Calendar.MINUTE, -6);
			        	   listx.add(sdf.format(now.getTime()));
			        	   listy.add(sdf1.format(now.getTime()));
			           }				  
				break;
			    case 3:
			           for(int i=0;i<31;i++){
			        	   now.add(Calendar.MINUTE, -48);
			        	   listx.add(sdf.format(now.getTime()));
			        	   listy.add(sdf1.format(now.getTime()));
			           }				   
				break;
			    case 4:
			           for(int i=0;i<31;i++){
			        	   now.add(Calendar.MINUTE, -336);
			        	   listx.add(sdf.format(now.getTime()));
			        	   listy.add(sdf1.format(now.getTime()));
			           }				   
				break;
			    default:
			           for(int i=0;i<31;i++){
				          now.add(Calendar.MINUTE, -2);
				          listx.add(sdf.format(now.getTime()));
				          listy.add(sdf1.format(now.getTime()));
			           }
				break;
			   }
        	   map.put("x", listx);
        	   map.put("y", listy);
                       
           return map; 
    } 
   
    
    
    public List<MonitorDetailModel> selectMonitorDetailData(List<String> list,Map map){
    	List<MonitorDetailModel> listMDetailModels = new ArrayList<MonitorDetailModel>(); 	
     		for(int i=0;i<list.size();i++){             	
             	if(i!=list.size()-1){
             		map.put("end", list.get(i+1));
             		map.put("start", list.get(i)); 
             	    List<MonitorDetailModel> listMDetailModel = this.monitorDao.selectDateTime(map);
             	   if(listMDetailModel!=null&&listMDetailModel.size()!=0){
             	    listMDetailModels.add(listMDetailModel.get(0));
             	   }else {
             		listMDetailModels.add(null);
				}
             	}
             
         	 
         	}
    	return listMDetailModels;
    }

    public List<MonitorIndexModel> selectMonitorCount(){
    	return this.monitorIndexService.selectMonitorCount();
    }


	@Override
	public List<String> getMonitorXData(Integer strategy) {
		Map<String, Object> dateMap = anaysiDate(strategy);
	    List<String> xData =(List<String>) dateMap.get("x");
		Collections.reverse(xData);
		return xData;
	}


}
