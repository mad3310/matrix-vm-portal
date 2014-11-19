package com.letv.portal.proxy;

import java.util.Map;

import com.letv.portal.model.DbModel;


/**Program Name: IDbProxy <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月7日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IDbProxy extends IBaseProxy<DbModel> {
	
	public void auditAndBuild(Map<String,Object> params);
	/**Methods Name: saveAndBuild <br>
	 * Description: 保存db，并创建container集群及db<br>
	 * @author name: liuhao1
	 * @param dbModel
	 */
	public void saveAndBuild(DbModel dbModel);
	public DbModel dbList(Long dbId);
	public DbModel monitor4conn(Long dbId,Long chartId,Long strategy);
	
}
