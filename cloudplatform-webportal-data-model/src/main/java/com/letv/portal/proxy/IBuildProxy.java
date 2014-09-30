package com.letv.portal.proxy;

import java.util.List;

import com.letv.portal.model.BuildModel;




/**Program Name: IBuildService <br>
 * Description:  python api 创建mcluster及db等创建过程记录<br>
 * @author name: wujun <br>
 * Written Date: 2014年9月30日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IBuildProxy extends IBaseProxy<BuildModel> {

	public List<BuildModel> selectByMclusterId(String mclusterId);

	public void initStatus(String mclusterId);
	
	public void updateStatusFail(BuildModel buildModel);
}
