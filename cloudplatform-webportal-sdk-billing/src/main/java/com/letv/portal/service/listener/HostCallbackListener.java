package com.letv.portal.service.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.product.IProductInfoRecordDao;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.service.openstack.billing.VmCreateListener;

/**
 * @Title: HostCallbackListener.java
 * @Package com.letv.portal.service.listener
 * @Description: 云主机创建完成后回调
 * @author lisuxiao
 * @date 2015年9月23日 上午11:06:40
 */
public class HostCallbackListener implements VmCreateListener {
	
	@Autowired
	private IProductInfoRecordDao productInfoRecordDao;

	@SuppressWarnings("unchecked")
	@Override
	public void vmCreated(String region, String vmId, int vmIndex,
			Object userData) {
		List<ProductInfoRecord> records = (List<ProductInfoRecord>) userData;
		if(vmIndex>records.size()) {
			throw new ValidateException("云主机回调vmIndex超出List范围！");
		}
		ProductInfoRecord record = records.get(vmIndex);
		record.setInstanceId(region+"_"+vmId);
		this.productInfoRecordDao.updateBySelective(record);
	}
	
}
