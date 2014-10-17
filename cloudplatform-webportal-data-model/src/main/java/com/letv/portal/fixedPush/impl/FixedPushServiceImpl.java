package com.letv.portal.fixedPush.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.letv.common.util.HttpClient;
import com.letv.portal.fixedPush.IFixedPushService;



/**
 * Program Name: FixedPush <br>
 * Description:  与固资系统交互实现接口
 * @author name: wujun <br>
 * Written Date: 2014年10月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Service("FixedPushService")
public class FixedPushServiceImpl implements IFixedPushService{
	
	
	private final static Logger logger = LoggerFactory.getLogger(FixedPushServiceImpl.class);
	
	private final static String FIXEDPUSH_URL = "MmsContain/operator";//"http://localhost:8080/webTest2/";
	private final static String FIXEDPUSH_CODE_CREATE = "100";//100代表创建
	private final static String FIXEDPUSH_CODE_DELETE = "200";//200代表删除
	private final static String FIXEDPUSH_RESCODE_SUCCESS = "200";//200代表录入成功
	private final static String FIXEDPUSH_RESCODE_FAILURE= "400";//400代表录入失败
	private final static String FIXEDPUSH_RESMSG = "webportalAPI";
	private final static String URL_HEAD = "http://";	
	private final static String URL_IP = "t.oss.letv.cn";			
	private final static String URL_PORT = ":29380";		
	
	/**
	 * Methods Name: sendFixedInfo <br>
	 * Description: 创建container的相关系统<br>
	 * @author name: wujun
	 */
	public Boolean createContainerPushFixedInfo(Map<String, String> map){
		Boolean flag = false;
		map.put("code", FIXEDPUSH_CODE_CREATE);
		String result = sendFixedInfo(map);
		flag = analysisResult(transResult(result));
		return flag;
	}
	
	/**
	 * Methods Name: sendFixedInfo <br>
	 * Description: 删除container的相关信息<br>
	 * @author name: wujun
	 */
	public Boolean deleteContainerPushFixedInfo(Map<String, String> map){
		Boolean flag = false;
		map.put("code", FIXEDPUSH_CODE_DELETE);
		String result = sendFixedInfo(map);
		flag = analysisResult(transResult(result));
		return flag;
	}	
	
	@Override
	public String sendFixedInfo(Map<String, String> map) {	
		String url=URL_HEAD+URL_IP+URL_PORT+"/"+FIXEDPUSH_URL;
		map.put("rescode", FIXEDPUSH_RESCODE_SUCCESS);
		map.put("resmsg", FIXEDPUSH_RESMSG);	
		String fixedPushString =  JSON.toJSON(map).toString();
		String result = HttpClient.post(url, fixedPushString,null,null);		
		return result;
	}

	@Override
	public String receviceFixedInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<String,Object> transResult(String result){
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			jsonResult = JSON.parseObject(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	
	private boolean analysisResult(Map result){
		boolean flag = false;
		String rescode = (String)result.get("rescode");
		if(FIXEDPUSH_RESCODE_SUCCESS.equals(rescode)){
			flag = true;
			logger.debug("固资系统信息推送成功");
			return flag;
		}else{
			logger.debug("固资系统信息推送失败");
			return flag;
		}
		
	}
}
