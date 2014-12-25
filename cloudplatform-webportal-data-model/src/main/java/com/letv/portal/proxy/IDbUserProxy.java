package com.letv.portal.proxy;

import java.util.List;

import com.letv.portal.model.DbUserModel;

/**Program Name: IDbUserProxy <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月7日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IDbUserProxy extends IBaseProxy<DbUserModel> {
	/**Methods Name: saveAndBuild <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param dbUserModel
	 */
	public void saveAndBuild(DbUserModel dbUserModel);
	/**Methods Name: deleteDbUser <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param dbUserId
	 */
	public void deleteDbUser(String dbUserId);
	/**Methods Name: updateDbUser <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param dbUserModel
	 */
	public void updateDbUser(DbUserModel dbUserModel);
	/**
	 * Methods Name: buildDbUser <br>
	 * Description: 审批创建DbUser<br>
	 * @author name: wujun
	 * @param DbUserId
	 */
	public void buildDbUser(String DbUserId);
	
	/**Methods Name: selectIpsFromUser <br>
	 * Description: 根据dbId和默认只读用户名获取ip列表<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @return
	 */
	public List<String> selectIpsFromUser(Long dbId);

	/**Methods Name: saveOrUpdateIps <br>
	 * Description: ip维护：虚拟为特殊用户的存储<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param ips
	 */
	public void saveOrUpdateIps(Long dbId, String ips);
}
