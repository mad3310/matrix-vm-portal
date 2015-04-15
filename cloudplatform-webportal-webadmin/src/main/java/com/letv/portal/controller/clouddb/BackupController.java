package com.letv.portal.controller.clouddb;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.common.util.StringUtil;
import com.letv.portal.service.IBackupService;
@Controller
@RequestMapping("/backup")
public class BackupController {
	
	@Autowired
	private IBackupService backupService;
	
	private final static Logger logger = LoggerFactory.getLogger(BackupController.class);
		
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(HttpServletRequest request,Page page,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("dbName", StringUtil.transSqlCharacter(request.getParameter("dbName")));
		params.put("mclusterName", StringUtil.transSqlCharacter(request.getParameter("mclusterName")));
		params.put("orderBy", "START_TIME");
		params.put("isAsc", true);
		obj.setData(this.backupService.selectPageByParams(page, params));
		return obj;
	}
	
}
