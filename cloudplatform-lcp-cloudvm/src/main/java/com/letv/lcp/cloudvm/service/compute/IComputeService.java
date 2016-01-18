package com.letv.lcp.cloudvm.service.compute;

import java.util.Map;

import com.letv.lcp.cloudvm.listener.VmCreateListener;
import com.letv.lcp.cloudvm.model.compute.ComputeModel;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.service.resource.IResourceService;


public interface IComputeService extends IResourceService<ComputeModel>  {
	/**
	  * @Title: createVm
	  * @Description: 创建云主机
	  * @param userId
	  * @param vmCreateConf
	  * @param vmCreateListener
	  * @param listenerUserData
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:53:14
	  */
	String createVm(Long userId, VMCreateConf2 vmCreateConf, VmCreateListener vmCreateListener, Object listenerUserData, Map<String, Object> params);

	/**
	  * @Title: getVmCreatePrepare
	  * @Description: 云主机创建前准备工作
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:53:26
	  */
	String getVmCreatePrepare(Map<String, Object> params);
	
	/**
	  * @Title: checkVmCreateConf
	  * @Description: 检查云主机创建参数
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:54:02
	  */
	String checkVmCreateConf(Map<String, Object> params);
	
	/**
	  * @Title: checkQuota
	  * @Description: 检查配额
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:54:20
	  */
	String checkQuota(Map<String, Object> params);
	
	/**
	  * @Title: waitingVmsCreated
	  * @Description: 等待云主机创建完成
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:54:32
	  */
	String waitingVmsCreated(Map<String, Object> params);
	
	/**
	  * @Title: bindFloatingIp
	  * @Description: 云主机绑定公网Ip
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:54:45
	  */
	String bindFloatingIp(Map<String, Object> params);
	
	/**
	  * @Title: emailVmsCreated
	  * @Description: 云主机创建完成后发送通知
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:55:06
	  */
	String emailVmsCreated(Map<String, Object> params);
}
