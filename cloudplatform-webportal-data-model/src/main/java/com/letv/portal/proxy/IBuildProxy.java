package com.letv.portal.proxy;

import java.util.List;

import com.letv.portal.model.BuildModel;

/**Program Name: IBuildProxy <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月7日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IBuildProxy extends IBaseProxy<BuildModel> {

	public List<BuildModel> selectByMclusterId(Long mclusterId);

	public void initStatus(Long mclusterId);
	
	public List<BuildModel> selectByDbId(Long dbId);
}
