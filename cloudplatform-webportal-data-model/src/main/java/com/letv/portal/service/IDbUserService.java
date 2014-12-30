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
	public List<DbUserModel> selectByDbId(Long dbId);
	
	/**Methods Name: findPagebyParams <br>
	 * Description: dbUser列表<br>
	 * @author name: liuhao1
	 * @param params
	 * @param page
	 * @return
	 */
	public Page findPagebyParams(Map<String,Object> params,Page page);
	
	/**Methods Name: selectCreateParams <br>
	 * Description: 查询创建用户相关参数<br>
	 * @author name: liuhao1
	 * @param id
	 * @return
	 */
	public Map<String,Object> selectCreateParams(Long id);

	/**Methods Name: updateStatus <br>
	 * Description: 根据数据库用户名和ip查询数据：用于验证数据库用户名和ip是否重复<br>
	 * @author name: yaokuo
	 * @param dbUserModel
	 */
	public List<DbUserModel> selectByIpAndUsername(DbUserModel dbUserModel);
	/**
	 * Methods Name: insertAndAcceptIp <br>
	 * Description: 插入dbUser对应Ip
	 * @author name: wujun
	 * @param dbUserModel
	 */
	public void insertDbUserAndAcceptIp(DbUserModel dbUserModel);
    /**
     * Methods Name: updateStatus <br>
     * Description: 更新状态
     * @author name: wujun
     * @param dbUserModel
     */
	public void updateStatus(DbUserModel dbUserModel);
	/**
	 * Methods Name: updateDbUser <br>
	 * Description: 修改dbUser信息<br>
	 * @author name: wujun
	 * @param dbUserModel
	 */
	public void updateDbUser(DbUserModel dbUserModel);
	/**
	 * Methods Name: deleteDbUser <br>
	 * Description: 删除dbUser信息<br>
	 * @author name: wujun
	 * @param dbUserModel
	 * @param dbUserId 
	 */
	 public void deleteDbUser(String dbUserId);

	/**Methods Name: deleteByDbId <br>
	 * Description: 根据db删除用户<br>
	 * @author name: liuhao1
	 */
	public void deleteByDbId(Long dbId);
	/**
	 * Methods Name: buildDbUser <br>
	 * Description: 审批DbUser<br>
	 * @author name: wujun
	 * @param dbUserId
	 */
	public void buildDbUser(String dbUserId);

	/**Methods Name: selectGroupByName <br>
	 * Description: 按照参数查出用户，并按照用户名分组<br>
	 * @author name: liuhao1
	 * @param params
	 * @return
	 */
	public List<DbUserModel> selectGroupByName(Map<String, Object> params);
}
