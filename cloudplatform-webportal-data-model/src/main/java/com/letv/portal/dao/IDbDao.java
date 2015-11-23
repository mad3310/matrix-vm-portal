package com.letv.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.DbModel;


/**Program Name: IDbDao <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IDbDao extends IBaseDao<DbModel> {
	
	/**Methods Name: audit <br>
	 * Description: 审核<br>
	 * @author name: liuhao1
	 * @param dbModel
	 */
	public void audit(DbModel dbModel);
	
	/**Methods Name: selectCreateParams <br>
	 * Description: 查询创建db相关参数<br>
	 * @author name: liuhao1
	 * @param params
	 * @return
	 */
	public Map<String,Object> selectCreateParams(Map<String, Object> params);

	/**Methods Name: selectByDbNameForValidate <br>
	 * Description: 根据数据库名查询数据：用于验证数据库名是否重复<br>
	 * @author name: liuhao1
	 * @param params
	 */
	public List<DbModel> selectByDbNameForValidate(HashMap<String, Object> params);

	public Integer selectCountByStatus(Integer _parameter);
}
