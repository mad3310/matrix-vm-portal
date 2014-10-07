package com.letv.portal.proxy;

import java.util.List;

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
	Boolean isExistByName(String mclusterName);
	
}