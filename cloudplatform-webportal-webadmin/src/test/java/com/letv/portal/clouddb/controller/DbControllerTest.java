package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import com.letv.common.result.ResultObject;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.proxy.IMclusterProxy;
 
public class DbControllerTest extends AbstractTest{

	@Autowired
	private IDbProxy dbProxy;
	@Autowired
	private IMclusterProxy mclusterProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(DbController.class);

	
	/**Methods Name: list <br>
	 * Description: 查询db列表   /{currentPage}/{recordsPerPage}/{dbName}
	 * @author name: wujun
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 */
    @Test
	public void list() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", null);
		params.put("createUser", "11");	
	    System.out.println(this.dbProxy.selectPageByParams(1,10,params));
	}
	
	/**Methods Name: detail <br>
	 * Description: 查看Db详情 /{dbId}
	 * @author name: wujun
	 * @param dbId
	 * @param request
	 * @return
	 */
    @Test
	public void detail(){
    	Long dbId = 1L;
		System.out.println(this.dbProxy.selectById(dbId));
	}
	
	/**Methods Name: save <br>
	 * Description:审批db /audit
	 * @author name: wujun
	 * @param request
	 * @return
	 */
    @Test  
	public void save() {
    	Map<String,Object> params = new HashMap<String, Object>();
		this.dbProxy.auditAndBuild(params);		
		
	}
}
