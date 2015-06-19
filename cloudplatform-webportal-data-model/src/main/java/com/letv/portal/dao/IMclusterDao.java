package com.letv.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.MclusterModel;


/**Program Name: IMclusterDao <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IMclusterDao extends IBaseDao<MclusterModel> {

	/**Methods Name: audit <br>
	 * Description: 改变status状态<br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 */
	void audit(HashMap mclusterHashMap);

	/**Methods Name: selectByName <br>
	 * Description: 根据mcluster名查询数据：用于验证mcluster名是否重复<br>
	 * @author name: liuhao1
	 * @param mclusterName
	 * @return
	 */
	List<MclusterModel> selectByName(String mclusterName);

	List<MclusterModel> select4Run();

	/**Methods Name: selectValidMclusters <br>
	 * Description: 选择有效的mcluster集群: RUNNING(1),STARTING(7),STOPPING(8),STOPED(9),DANGER(13),CRISIS(14).<br>
	 * @author name: liuhao1
	 * @return
	 */
	List<MclusterModel> selectValidMclusters();
	Integer selectValidMclusterCount();

	List<MclusterModel> selectValidMclustersByPage(QueryParam<String, Object> param);
	
	List<MclusterModel> selectValidMclustersByMap(Map<String, Object> params);

	List<MclusterModel> selectNextValidMclusterById(Map<String, Object> map);

}
