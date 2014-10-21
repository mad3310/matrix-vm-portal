package com.letv.portal.dao;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.DbUserModel;

/**Program Name: IDbUserDao <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IDbUserDao extends IBaseDao<DbUserModel> {
	
	/**Methods Name: selectByDbId <br>
	 * Description: 根据dbId查询相关用户<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @return
	 */
	public List<DbUserModel> selectByDbId(Long dbId);
	
	/**Methods Name: selectCreateParams <br>
	 * Description: 查询创建dbUser相关参数<br>
	 * @author name: liuhao1
	 * @param id
	 * @return
	 */
	public Map<String,Object> selectCreateParams(Long id);
	
	/**Methods Name: updateStatus <br>
	 * Description: 更新数据库用户状态<br>
	 * @author name: liuhao1
	 * @param dbUserModel
	 */
	public void updateStatus(DbUserModel dbUserModel);
    /**
     * Methods Name: selectByIpAndUsername <br>
     * Description: 通过Ip和UserName判断是否存在该用户
     * @author name: wujun
     * @param dbUserModel
     * @return
     */
	public List<DbUserModel> selectByIpAndUsername(DbUserModel dbUserModel);

	public void deleteByDbId(Long dbId);
}
