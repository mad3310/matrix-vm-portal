package com.letv.portal.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
	public List<MonitorViewYModel> getMonitorViewData(Long mclusterId,Long chartId,Integer strategy) {
		List<MonitorViewYModel> ydatas = new ArrayList<MonitorViewYModel>();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("mclusterId", mclusterId);
	    map.put("type", "mclusternode");
	    List<ContainerModel> containers = this.containerService.selectByMap(map);	  
	    
	    MonitorIndexModel monitorIndexModel  = this.monitorIndexService.selectById(chartId);	   
	    String dataTable = monitorIndexModel.getDetailTable();
	    
	    Map<String, Object> indexParams = new HashMap<String, Object>();
	    indexParams.put("dbName",monitorIndexModel.getDetailTable());
	    
	    Date startDate = new Date();
	    Calendar now = Calendar.getInstance();  
        now.setTime(startDate);
        now.add(Calendar.MINUTE, -60);
        Date endDate = now.getTime();
	    
	    List<String> detailNames =  this.monitorDao.selectDistinct(indexParams);
	    
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("dbName", monitorIndexModel.getDetailTable());
		params.put("start", "2014-11-19 12:00:00");
		params.put("end", "2014-11-19 13:00:00");
		
		for (ContainerModel c : containers) {
			for (String s : detailNames) {
				MonitorViewYModel ydata = new MonitorViewYModel();
				params.put("ip", c.getIpAddr());
				params.put("detailName", s);

				List<MonitorDetailModel> list = this.monitorDao.selectDateTime(params);
				List<List<Object>> datas = new ArrayList<List<Object>>();
				for (MonitorDetailModel monitorDetail : list) {
					List<Object> point = new ArrayList<Object>();
					point.add(monitorDetail.getMonitorDate());
					point.add(monitorDetail.getDetailValue());
					datas.add(point);
				}
				ydata.setName(c.getIpAddr() +":"+s);
				ydata.setType("spline");
				ydata.setData(datas);
				ydatas.add(ydata);
			}
		}

		return ydatas;
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
