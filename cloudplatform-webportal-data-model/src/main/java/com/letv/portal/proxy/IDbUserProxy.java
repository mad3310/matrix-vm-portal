package com.letv.portal.proxy;

import java.util.List;
import java.util.Map;

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
	@Deprecated
	public void saveAndBuild(DbUserModel dbUserModel);
	
	/**Methods Name: saveAndBuild <br>
	 * Description: webapp改版后保存方法<br>
	 * @author name: liuhao1
	 * @param users
	 */
	public void saveAndBuild(List<DbUserModel> users);
	
	/**Methods Name: deleteAndBuild <br>
	 * Description: 删除并删除用户<br>
	 * @author name: liuhao1
	 * @param users
	 */
	public void deleteAndBuild(List<DbUserModel> users);
	
	/**Methods Name: deleteDbUser <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param dbUserId
	 */
	public void deleteDbUser(String dbUserId);
	
	/**Methods Name: updateDbUser <br>
	 * Description: webapp改版后更新方法<br>
	 * @author name: liuhao1
	 * @param users
	 */
	public void updateDbUser(List<DbUserModel> users);
	/**
	 * Methods Name: buildDbUser <br>
	 * Description: 审批创建DbUser<br>
	 * @author name: wujun
	 * @param DbUserId
	 */
	public void buildDbUser(String DbUserId);
	
	/**Methods Name: updateUserAuthority <br>
	 * Description: 修改用户权限<br>
	 * @author name: liuhao1
	 * @param dbUserModel
	 * @param ips
	 * @param types
	 */
	public void updateUserAuthority(DbUserModel dbUserModel, String ips,String types);

	public void saveAndBuild(DbUserModel dbUserModel, String ips, String types);

	/**Methods Name: saveOrUpdateIps <br>
	 * Description: ip维护：虚拟为特殊用户的存储<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param ips
	 */
	public void saveOrUpdateIps(Long dbId, String ips);

	public void updateSecurity(Long dbId, String username, String password);

	public void deleteAndBuild(Long dbId, String username);
}
