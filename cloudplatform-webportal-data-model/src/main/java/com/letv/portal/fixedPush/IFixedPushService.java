package com.letv.portal.fixedPush;

import java.util.List;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.fixed.FixedPushModel;


/**Program Name: IFixedPushService <br>
 * Description:  与固资系统交互实现<br>
 * @author name: wujun <br>
 * Written Date: 2014年10月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */

public interface IFixedPushService {
	/**
	 * Methods Name: createMutilContainerPushFixedInfo <br>
	 * Description: 备案多个container<br>
	 * @author name: wujun
	 * @param fixedPushModel
	 */
	public Boolean createMutilContainerPushFixedInfo(List<ContainerModel> containers);
	public Boolean deleteMutilContainerPushFixedInfo(List<ContainerModel> containers);
	/**
	 * Methods Name: createContainerPushFixedInfo <br>
	 * Description: 创建container的相关系统<br>
	 * @author name: wujun
	 */
	public void createContainerPushFixedInfo(FixedPushModel fixedPushModel)throws Exception;
	/**
	 * Methods Name: deleteContainerPushFixedInfo <br>
	 * Description: 删除container的相关信息<br>
	 * @author name: wujun
	 */
	public Boolean deleteContainerPushFixedInfo(FixedPushModel fixedPushModel)throws Exception;
	/**
	 * Methods Name: sendFixedInfo <br>
	 * Description: 向固资系统发送固资信息<br>
	 * @author name: wujun
	 * @throws Exception 
	 */
	public String sendFixedInfo(FixedPushModel fixedPushModel) throws Exception;
	/**
	 * Methods Name: receviceFixedInfo <br>
	 * Description: 接受固资系统的固资信息<br>
	 * @author name: wujun
	 */
	public String receviceFixedInfo(FixedPushModel fixedPushModel)throws Exception;
}
