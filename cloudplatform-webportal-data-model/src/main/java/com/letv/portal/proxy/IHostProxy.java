package com.letv.portal.proxy;

import com.letv.portal.model.HostModel;

/**Program Name: IHostProxy <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月7日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IHostProxy extends IBaseProxy<HostModel> {

	public void insertAndPhyhonApi(HostModel hostModel);
}
