package com.letv.portal.controller.adminoplog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.adminoplog.AdminOpLogQueryCondition;
import com.letv.portal.service.adminoplog.AoLog;
import com.letv.portal.service.adminoplog.ClassAoLog;
import com.letv.portal.service.adminoplog.IAdminOpLogService;

@ClassAoLog(module="管理员日志")
@Controller
@RequestMapping("/AdminOpLog")
public class AdminOpLogController {

	@Autowired
	private IAdminOpLogService adminOpLogService;

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory
			.getLogger(AdminOpLogController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject list(
			@RequestParam(required = false) Long beginTime,
			@RequestParam(required = false) Long endTime,
			@RequestParam(required = false) String userNameKeyword,
			@RequestParam(required = false) String eventKeyword,
			@RequestParam(required = false) String moduleKeyword,
			@RequestParam(required = false) String descriptionKeyword,
			@RequestParam(required = false) String logTypeNameKeyword,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		AdminOpLogQueryCondition condition = new AdminOpLogQueryCondition();
		condition.setBeginTime(beginTime);
		condition.setEndTime(endTime);
		condition.setUserNameKeyword(userNameKeyword);
		condition.setEventKeyword(eventKeyword);
		condition.setModuleKeyword(moduleKeyword);
		condition.setDescriptionKeyword(descriptionKeyword);
		condition.setLogTypeNameKeyword(logTypeNameKeyword);
		result.setData(adminOpLogService.select(condition, currentPage,
				recordsPerPage));
		return result;
	}

	@RequestMapping(value = "/deleteRecent", method = RequestMethod.GET)
	public @ResponseBody ResultObject deleteRecent(
			@RequestParam(required = false, defaultValue = "30") Integer days) {
		ResultObject result = new ResultObject();
		adminOpLogService.deleteRecent(days);
		return result;
	}
}
