package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.DbUserModel;

/**Program Name: IDbUserService <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月1日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IDbUserService extends IBaseService<DbUserModel> {
	
	/**Methods Name: selectByDbId <br>
	 * Description: 根据dbId查询dbUser列表<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @return
	 */
	public List<DbUserModel> selectByDbId(String dbId);
	
	/**Methods Name: findPagebyParams <br>
	 * Description: dbUser列表<br>
	 * @author name: liuhao1
	 * @param params
	 * @param page
	 * @return
	 */
	public Page findPagebyParams(Map<String,Object> params,Page page);

}
