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
	 * @param isCreateAdmin 是否默认创建管理员用户
	 */
	public void saveAndBuild(DbModel dbModel,boolean isCreateAdmin);
	/**
	  * @Title: save
	  * @Description: 只保存db信息,不进行服务创建
	  * @param dbModel
	  * @param isCreateAdmin
	  * @return Long   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月10日 上午10:13:36
	  */
	public Long save(DbModel dbModel,boolean isCreateAdmin);
	/**
	  * @Title: build
	  * @Description: 创建服务
	  * @param dbId
	  * @return Long   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月10日 下午12:00:16
	  */
	public void build(Long dbId);
}
