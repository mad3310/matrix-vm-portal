package com.letv.portal.fixedPush;

import java.util.Map;


/**Program Name: IFixedPushService <br>
 * Description:  与固资系统交互实现<br>
 * @author name: wujun <br>
 * Written Date: 2014年10月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */

public interface IFixedPushService {
	
	/**
	 * Methods Name: createContainerPushFixedInfo <br>
	 * Description: 创建container的相关系统<br>
	 * @author name: wujun
	 */
	public Boolean createContainerPushFixedInfo(Map<String, String> map);
	/**
	 * Methods Name: deleteContainerPushFixedInfo <br>
	 * Description: 删除container的相关信息<br>
	 * @author name: wujun
	 */
	public Boolean deleteContainerPushFixedInfo(Map<String, String> map);
	/**
	 * Methods Name: sendFixedInfo <br>
	 * Description: 向固资系统发送固资信息<br>
	 * @author name: wujun
	 */
	public String sendFixedInfo(Map<String, String> map);
	/**
	 * Methods Name: receviceFixedInfo <br>
	 * Description: 接受固资系统的固资信息<br>
	 * @author name: wujun
	 */
	public String receviceFixedInfo();
}
