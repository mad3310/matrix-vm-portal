package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IMclusterProxy;

@Controller
@RequestMapping("/mcluster")
public class MclusterController {
	
	@Autowired
	private IMclusterProxy mclusterProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(MclusterController.class);
	
	
	/**Methods Name: toList <br>
	 * Description: page to mcluster list<br>
	 * @author name: liuhao1
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView toList(ModelAndView mav) {
		mav.setViewName("/clouddb/mcluster_list");
		return mav;
	}
	@RequestMapping(value="/{mclusterId}", method=RequestMethod.GET)   
	public ModelAndView detail(@PathVariable Long mclusterId,ModelAndView mav) {
		mav.setViewName("/clouddb/mcluster_detail");
		return mav;
	}
	
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

	/**Methods Name: save <br>
	 * Description:  保存并创建mcluster<br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 * @param request
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public void save(MclusterModel mclusterModel,HttpServletRequest request) {
//		mclusterModel.setCreateUser(Long.parseLong(request.getSession().getAttribute("userId").toString()));
		this.mclusterProxy.insert(mclusterModel);
	}
	
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> validate(String mclusterName,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		Boolean isExist= this.mclusterProxy.isExistByName(mclusterName);
		map.put("valid", isExist);
		return map;
	}
}
