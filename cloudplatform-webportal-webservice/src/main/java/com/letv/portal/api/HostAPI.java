
package com.letv.portal.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.service.IHostService;

/**Program Name: DbAPI <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月20日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/host")
public class HostAPI {
	@Resource
	private IHostService hostService;
	
	private final static Logger logger = LoggerFactory.getLogger(HostAPI.class);
	
	/**Methods Name: list <br>
	 * Description: 根据查询条件及分页信息获取分页数据<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/list",method=RequestMethod.POST)   //http://localhost:8080/api/db/list
	@ResponseBody
	public ResultObject list(@RequestBody Map<String,Object> params,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		obj.setData(this.hostService.selectByMap(params));
		return obj;
	}
	
}
