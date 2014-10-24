package com.letv.portal.zabbixPush.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.letv.common.util.ConfigUtil;
import com.letv.common.util.HttpClient;
import com.letv.portal.fixedPush.impl.FixedPushServiceImpl;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.InterfacesModel;
import com.letv.portal.model.ZabbixParam;
import com.letv.portal.model.ZabbixPushDeleteModel;
import com.letv.portal.model.ZabbixPushModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.zabbixPush.IZabbixPushService;

@Service("zabbixPushService")
public class ZabbixPushServiceImpl implements IZabbixPushService{
	private final static Logger logger = LoggerFactory.getLogger(FixedPushServiceImpl.class);	
	private final static String FIXEDPUSH_GET=ConfigUtil.getString("fixedpush.url");
	private final static String FIXEDPUSH_SOCKET_IP=ConfigUtil.getString("fixedpush.url.ip");
	private final static int FIXEDPUSH_SOCKET_PORT=ConfigUtil.getint("fixedpush.url.port");
	private final static String FIXEDPUSH_POST="http://10.200.90.51/zabbix_test/api_jsonrpc.php";
	
	@Autowired
	private IContainerService containerService;
	@Override
	public Boolean createMultiContainerPushZabbixInfo(List<ContainerModel> containerModels) {
		Boolean rflag = false;
		try {
			if(containerModels!=null&&containerModels.size()>0){
				int count =0;
			for(ContainerModel c:containerModels){
				ZabbixPushModel zabbixPushModel = new ZabbixPushModel();
							
				ZabbixParam params = new ZabbixParam();
				params.setHost(c.getContainerName());
				
				InterfacesModel interfacesModel = new InterfacesModel();
				interfacesModel.setIp(c.getIpAddr());
				
				List<InterfacesModel> list = new ArrayList<InterfacesModel>();
				list.add(interfacesModel);
				params.setInterfaces(list);
				
				zabbixPushModel.setParams(params);  
				Boolean flag =	pushZabbixInfo(zabbixPushModel,c.getId());
				
				if(flag==true)
				count++;		
			}		
			    rflag=true;
			    logger.debug("增加了"+count+"个container");
			}
			} catch (Exception e) {
				logger.debug("zabbix推送失败"+" "+e.getMessage());
			}
		   return rflag;
	}
	@Override
	public Boolean deleteSingleContainerPushZabbixInfo(
			ContainerModel containerModel) {
		Boolean flag = false;
		try {  
			if(containerModel!=null){
				ZabbixPushDeleteModel zabbixPushDeleteModel = new ZabbixPushDeleteModel();
				List<String> list = new ArrayList<String>();
				list.add(containerModel.getZabbixHosts());
				zabbixPushDeleteModel.setParams(list);
				flag  = pushDeleteZabbixInfo(zabbixPushDeleteModel);					
			}		
			} catch (Exception e) {
				logger.debug("zabbix删除失败");
			}
		return flag;
			}

	/**
	 * Methods Name: createContainerPushZabbixInfo <br>
	 * Description: 创建container时向zabbix系统推送信息<br>
	 * @author name: wujun
	 * @param zabbixPushModel
	 * @return
	 */
	public Boolean pushZabbixInfo(ZabbixPushModel zabbixPushModel,Long containerId){
		Boolean flag = false;
	    String result=loginZabbix();
	    if(result!=null){
			if(result.contains("_succeess")){
				String[] auths = result.split("_");
				String auth = auths[0];
				logger.debug("登陆zabbix系统成功");
				try {
					zabbixPushModel.setAuth(auth);
					result = analysisResultMap(transResult(sendZabbixInfo(zabbixPushModel)));				
					if(result.contains("_succeess")){
						String[] rs = result.split("_");
						result = rs[0];
						ContainerModel containerModel = new ContainerModel();
						containerModel.setId(containerId);
						containerModel.setZabbixHosts(result);
						containerService.updateBySelective(containerModel);
						flag = true;
						logger.debug("推送zabbix系统成功"+result);
					}else {			
						String[] rs = result.split("_");
						result = rs[0];
						logger.debug("推送zabbix系统失败"+result);
					}					
					
				} catch (Exception e) {
				  logger.debug("推送zabbix系统失败"+e.getMessage());
				}
			}else {
				logger.debug("登陆zabbix系统失败");
			}	    	
	    }

		//loginZabbix(name,password);//login
		//sendZabbixInfo(object)//发送消息
		return flag;
	}; 
	
	/**
	 * Methods Name: createContainerPushZabbixInfo <br>
	 * Description: 创建container时向zabbix系统推送信息<br>
	 * @author name: wujun
	 * @param zabbixPushModel
	 * @return
	 */
	public Boolean pushDeleteZabbixInfo(ZabbixPushDeleteModel zabbixPushDeleteModel){
		Boolean flag = false;
	    String result=loginZabbix();
	    if(result!=null){
			if(result.contains("_succeess")){
				String[] auths = result.split("_");
				String auth = auths[0];
				logger.debug("登陆zabbix系统成功");
				try {
					zabbixPushDeleteModel.setAuth(auth);
					result = analysisResultMap(transResult(sendZabbixInfo(zabbixPushDeleteModel)));				
					if(result.contains("_succeess")){
						String[] rs = result.split("_");
						result = rs[0];
						flag = true;
						logger.debug("推送zabbix系统成功"+result);
					}else {			
						String[] rs = result.split("_");
						result = rs[0];
						logger.debug("推送zabbix系统失败"+result);
					}					
					
				} catch (Exception e) {
				  logger.debug("推送zabbix系统失败"+e.getMessage());
				}
			}else {
				logger.debug("登陆zabbix系统失败");
			}	    	
	    }

		//loginZabbix(name,password);//login
		//sendZabbixInfo(object)//发送消息
		return flag;
	}; 
	/**
	 * Methods Name: loginZabbix <br>
	 * Description:登陆zabbix系统<br>
	 * @author name: wujun
	 * @param zabbixPushModel
	 * @return
	 */
	public String loginZabbix(){
		String name = "cloude_api";
		String password = "zabbix";
		String loginResult = null;
		String url=FIXEDPUSH_POST;	
		String jsonString ="{\"jsonrpc\":\"2.0\",\"method\":\"user.login\",\"params\":{\"user\":\""+name+"\",\"password\":\""+password+"\"},\"id\":1}"; 
		String result = HttpClient.post(url, jsonString,null,null);
		try {
			loginResult = analysisResult(transResult(result));
		} catch (Exception e) {
		logger.debug("登陆zabbix系统失败"+e.getMessage());
		}
		return loginResult;
	}; 
	/**
	 * Methods Name: sendFixedInfo <br>
	 * Description: 向zabbix系统发送信息<br>
	 * @author name: wujun
	 * @throws Exception 
	 */ 
	public String sendZabbixInfo(Object object) throws Exception{
		String url=FIXEDPUSH_POST;			
		String fixedPushString =  JSON.toJSON(object).toString();
		String result = HttpClient.post(url, fixedPushString,null,null);		
		return result;
	};
	/**
	 * Methods Name: receviceFixedInfo <br>
	 * Description: 接受zabbix系统的信息<br>
	 * @author name: wujun
	 */
	public String receviceZabbixInfo(ZabbixPushModel zabbixPushModel)throws Exception{
		return null;
	};
	
	
	private Map<Object, Object> transResult(String result)throws Exception{
		Map<Object, Object> jsonResult = new HashMap<Object, Object>();
		try {
			jsonResult = JSON.parseObject(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	
	private String analysisResult(Map<Object, Object> map)throws Exception{
		String result = null;
		if(map!=null){
			result = (String) map.get("result");
			if("".equals(result)||null==result){
			    result = (String)map.get("error");
			    result+="_error";
			}else{
				result+="_succeess";
			}
		}
		return result;
	}
	
	private String analysisResultMap(Map<Object, Object> map)throws Exception{
		Map<Object, Object> resulteMap = new HashMap<Object, Object>();
		String result = null; 
		if(map!=null){
			resulteMap = (Map<Object, Object>) map.get("result");
			if("".equals(resulteMap)||null==resulteMap){
			    result = ((Map<Object, Object>)map.get("error")).get("data").toString();
			    result+="_error";
			}else{
				if(resulteMap.get("hostids")!=null){
					String[] arg =	resulteMap.get("hostids").toString().split("\"");
					result = arg[1];
					result+="_succeess";
				}		
			}
		}
		return result;
	}

	
}

