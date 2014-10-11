package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.python.service.IBuildTaskService;

@Controller
@RequestMapping("/mcluster")
public class MclusterController {
	
	@Autowired
	private IMclusterProxy mclusterProxy;
	
	@Autowired
	private IBuildTaskService buildTaskService;
	
	private final static Logger logger = LoggerFactory.getLogger(MclusterController.class);
	
	/**Methods Name: list <br>
	 * Description: get mcluster list by page and params<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{mclusterName}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String mclusterName,ResultObject result) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mclusterName", mclusterName);
		result.setData(this.mclusterProxy.selectPageByParams(currentPage,recordsPerPage,map));
		return result;
	}	
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(ResultObject result) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", MclusterStatus.RUNNING.getValue());
		result.setData(this.mclusterProxy.selectByMap(map));
		return result;
	}	

	/**Methods Name: save <br>
	 * Description:  保存并创建mcluster<br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 * @param request
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public void save(MclusterModel mclusterModel) {
		this.mclusterProxy.insert(mclusterModel);
	}
	
	/**Methods Name: validate <br>
	 * Description: 根据mclusterName验证重复性<br>
	 * @author name: liuhao1
	 * @param mclusterName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> validate(String mclusterName) {
		Map<String,Object> map = new HashMap<String,Object>();
		Boolean isExist= this.mclusterProxy.isExistByName(mclusterName);
		map.put("valid", isExist);
		return map;
	}
}
