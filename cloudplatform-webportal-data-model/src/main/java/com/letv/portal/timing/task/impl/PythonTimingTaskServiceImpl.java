package com.letv.portal.timing.task.impl;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.exception.CommonException;
import com.letv.common.util.HttpClient;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.timing.task.BaseTimingTask;
import com.letv.portal.timing.task.IBaseTimingTaskService;
import com.mysql.jdbc.StringUtils;

@Service("pythonTimingTaskService")
public class PythonTimingTaskServiceImpl extends BaseTimingTaskServiceImpl implements IBaseTimingTaskService{
	@Value("${timing.task.python.url}")
	private String TIMING_TASK_PYTHON_URL;
	@Value("${timing.task.python.user}")
	private String TIMING_TASK_PYTHON_USER;
	@Value("${timing.task.python.pwd}")
	private String TIMING_TASK_PYTHON_PWD;
	
	@Override
	public void insert(BaseTimingTask t) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("url", t.getUrl());
		map.put("http_method", t.getHttpMethod().toLowerCase());
		
		String timingTaskType = t.getType().toString().toLowerCase();
		if("interval".equals(timingTaskType)){
			map.put(timingTaskType, t.getTimeInterval());
		}else if("cron".equals(timingTaskType)){
			map.put(timingTaskType, t.getTimePoint());
		}
		
		String result = HttpClient.post(TIMING_TASK_PYTHON_URL, map,TIMING_TASK_PYTHON_USER, TIMING_TASK_PYTHON_PWD); //参数化
		
		Map<String, Object> transResult = transResult(result);
		if(!analysisResult(transResult))
			throw new CommonException("add task to python system error:" + ((Map)transResult.get("meta")).get("errorDetail"));
		t.setUuid((String)((Map)transResult.get("response")).get("task id"));
		super.insert(t);
	}
	
	@Override
	public void delete(BaseTimingTask t) {
		StringBuilder url = new StringBuilder();
		url.append(TIMING_TASK_PYTHON_URL).append("/").append(t.getUuid());
		
		String result = HttpClient.detele(url.toString(), TIMING_TASK_PYTHON_USER, TIMING_TASK_PYTHON_PWD); //参数化
		
		Map<String, Object> transResult = transResult(result);
		if(!analysisResult(transResult)) 
			throw new CommonException("delete task to python system error:" + ((Map)transResult.get("meta")).get("errorDetail"));
		super.delete(t);
	}
	
	
	private Map<String,Object> transResult(String result){
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		if(StringUtils.isNullOrEmpty(result))
			return jsonResult;
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	private boolean analysisResult(Map result){
		boolean flag = false;
		Map meta = (Map) result.get("meta");
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			flag = true;
		} 
		return flag;
	}
	
}
