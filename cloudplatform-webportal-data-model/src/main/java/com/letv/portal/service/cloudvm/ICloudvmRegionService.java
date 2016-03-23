package com.letv.portal.service.cloudvm;

import java.util.List;

import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.common.IBaseService;

public interface ICloudvmRegionService extends IBaseService<CloudvmRegion> {
	void add(String code, String displayName);

	void edit(Long id, String code, String displayName);

	void remove(Long id);

	CloudvmRegion selectByCode(String code);
	
	/**
	  * @Title: selectByType
	  * @Description: 根据类型获取region信息，1-私有云，2-公有云
	  * @param type
	  * @return List<CloudvmRegion>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月15日 下午4:59:28
	  */
	List<CloudvmRegion> selectByType(String type);

	List<CloudvmRegion> selectAll();
}
