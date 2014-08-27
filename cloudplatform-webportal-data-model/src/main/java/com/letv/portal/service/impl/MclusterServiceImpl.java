package com.letv.portal.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.IContainerDao;
import com.letv.portal.dao.IMclusterDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.Result;
import com.letv.portal.service.IMclusterService;

/**Program Name: MclusterServiceImpl <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Service("mclusterService")
public class MclusterServiceImpl extends BaseServiceImpl<MclusterModel> implements
		IMclusterService{
	
	@Resource
	private IMclusterDao mclusterDao;
	
	@Resource
	private IContainerDao containerDao;
	
	private static final String PYTHON_URL = "";
	private static final String SUCCESS_CODE = "";

	public MclusterServiceImpl() {
		super(MclusterModel.class);
	}

	@Override
	public IBaseDao<MclusterModel> getDao() {
		return this.mclusterDao;
	}

	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		QueryParam param = new QueryParam(params,page);
		page.setData(this.mclusterDao.selectPageByMap(param));
		page.setTotalRecords(this.mclusterDao.selectByMapCount(params));
		return page;
		
	}

	@Override
	public String build(String mclusterId) {
		
		MclusterModel mcluster = this.mclusterDao.selectById(mclusterId);
		//判断是否已创建
		if("0".equals(mcluster.getStatus())) {
			
			List<ContainerModel> containers = this.containerDao.selectByClusterId(mclusterId);
			String json=null;
			ObjectMapper mapper = null;
			RestTemplate  rest = new RestTemplate();
			try {
				mapper = new ObjectMapper();
				json = mapper.writeValueAsString(containers);// 把map或者是list转换成
				//创建
				String result = rest.postForObject("", null, String.class);
			
				Result r = mapper.readValue(result, Result.class);
				
				if(SUCCESS_CODE.equals(r.getCode())){
					//初始化
					result = rest.postForObject("", null, String.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
