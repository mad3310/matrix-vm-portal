package com.letv.portal.proxy;

import com.letv.portal.model.MclusterModel;


/**Program Name: IMclusterProxy <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月7日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IMclusterProxy extends IBaseProxy<MclusterModel> {
	
	/**Methods Name: isUniqueByName <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param mclusterName
	 * @return
	 */
	public Boolean isExistByName(String mclusterName);
	
	/**Methods Name: inertAndBuild <br>
	 * Description: 保存mcluster 并创建container集群 <br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 */
	public void inertAndBuild(MclusterModel mclusterModel);
	
}