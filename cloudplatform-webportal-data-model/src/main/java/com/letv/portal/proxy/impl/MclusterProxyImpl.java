package com.letv.portal.proxy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IMclusterService;

/**Program Name: MclusterServiceImpl <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Component
public class MclusterProxyImpl extends BaseProxyImpl<MclusterModel> implements
		IMclusterProxy{
	
	@Autowired
	private IMclusterService mclusterService;
	
	@Override
	public IBaseService<MclusterModel> getService() {
		return mclusterService;
	}	
}
