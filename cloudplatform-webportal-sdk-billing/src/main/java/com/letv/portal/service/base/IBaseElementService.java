package com.letv.portal.service.base;

import com.letv.portal.model.base.BaseElement;
import com.letv.portal.service.IBaseService;

/**Program Name: IBaseElementService <br>
 * Description:  基本元素<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月30日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IBaseElementService extends IBaseService<BaseElement> {

	/**Methods Name: isUnique <br>
	 * Description: 根据名称验证唯一性<br>
	 * @author name: liuhao1
	 * @param name
	 * @return
	 */
	public boolean isUnique(String name);
}
