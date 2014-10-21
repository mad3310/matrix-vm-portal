package com.letv.portal.dao;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;

/**Program Name: IHostDao <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IHclusterDao extends IBaseDao<HclusterModel> {
	public List<HclusterModel> selectByHclusterId(Long hclusterId);
}

