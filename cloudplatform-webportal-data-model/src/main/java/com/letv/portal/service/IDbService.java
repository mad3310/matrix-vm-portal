package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;


/**Program Name: IDbService <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月20日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IDbService extends IBaseService<DbModel> {
	
	/**Methods Name: findPagebyParams <br>
	 * Description: 根据查询条件查出分页数据<br>
	 * @author name: liuhao1
	 * @param params
	 * @param page
	 * @return
	 */
	public Page findPagebyParams(Map<String,Object> params,Page page);
	
	
	/**Methods Name: build <br>
	 * Description: 创建db<br>
	 * @author name: liuhao1
	 * @param dbModel
	 * @return
	 */
	public String build(DbModel dbModel);
	
	/**Methods Name: audit <br>
	 * Description: 审核db<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param dbApplyStandardId
	 * @param status
	 * @param mclusterId
	 * @param auditInfo
	 */
	public void audit(String dbId,String dbApplyStandardId,String status,String mclusterId,String auditInfo);
	
}
