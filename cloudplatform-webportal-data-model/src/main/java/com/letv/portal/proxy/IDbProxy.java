package com.letv.portal.proxy;

import java.util.List;
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
	public DbModel dbList(Long dbId);
	
}
