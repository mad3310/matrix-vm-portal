
package com.letv.portal.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IMclusterService;

/**Program Name: MclusterAPI <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/mcluster")
public class MclusterAPI {
	@Resource
	private IMclusterService mclusterService;
	
	@RequestMapping("/list")   //http://localhost:8080/api/mcluster/list
	public ResultObject list(HttpServletRequest request) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		Page page = new Page();
//		page.setCurrentPage(Integer.parseInt((String) params.get("currentPage")));
		page.setCurrentPage(1);
//		page.setRecordsPerPage(Integer.parseInt((String) params.get("recordsPerPage")));
		page.setRecordsPerPage(5);
		ResultObject obj = new ResultObject();
		obj.setData(this.mclusterService.findPagebyParams(params, page));
		return obj;
	}
	
	@RequestMapping("/save")   //http://localhost:8080/api/mcluster/save
	public ResultObject save(MclusterModel mclusterModel, String cipher, HttpServletRequest request) {
		System.out.println("111");
		ResultObject obj = new ResultObject();
		this.mclusterService.insert(mclusterModel);
		return obj;
	}
	
	
	
	
	
}
