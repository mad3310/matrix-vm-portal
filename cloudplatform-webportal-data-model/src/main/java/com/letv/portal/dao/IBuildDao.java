package com.letv.portal.dao;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.BuildModel;

/**Program Name: IBuildDao <br>
 * Description:  python api 创建mcluster及db等创建过程记录<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IBuildDao extends IBaseDao<BuildModel> {
	  
	public List<BuildModel> selectByMclusterId(Long mclusterId);
	
	public void updateByStep(BuildModel buildModel);
	
	public void updateByStatus(BuildModel buildModel);

	public void deleteByMclusterId(Long mclusterId);

	public List<BuildModel> selectByDbId(Long dbId);
}
