package com.letv.portal.service.adminoplog.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.adminoplog.IAdminOpLogDao;
import com.letv.portal.model.adminoplog.AdminOpLog;
import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.service.adminoplog.AdminOpLogQueryCondition;
import com.letv.portal.service.adminoplog.IAdminOpLogService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("adminOpLogService")
public class AdminOpLogServiceImpl extends BaseServiceImpl<AdminOpLog>
		implements IAdminOpLogService {

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory
			.getLogger(AdminOpLogServiceImpl.class);

	@Resource
	private IAdminOpLogDao adminOpLogDao;

	@Autowired
	private SessionServiceImpl sessionService;

	public AdminOpLogServiceImpl() {
		super(AdminOpLog.class);
	}

	@Override
	public IBaseDao<AdminOpLog> getDao() {
		return adminOpLogDao;
	}

	@Override
	public void add(String event, AoLogType logType, String module,
			String description) {
		if (event == null) {
			throw new ValidateException("参数不合法");
		}
		if (module == null) {
			throw new ValidateException("参数不合法");
		}
		if (description == null) {
			throw new ValidateException("参数不合法");
		}
		AdminOpLog log = new AdminOpLog(event, sessionService.getSession()
				.getUserId(), logType, module, description);
		insert(log);
	}

	@Override
	public void delete(Long id) {
		AdminOpLog log = this.selectById(id);
		if (log == null) {
			throw new ValidateException("日志不存在");
		}
		delete(log);
	}

	private List<AdminOpLog> select(AdminOpLogQueryCondition condition,
			Page page) {
		validateCondition(condition);
		Map<String, Object> params = new HashMap<String, Object>();
		addConditionToMap(condition, params);
		if (page != null) {
			params.put("page", page);
		}
		return adminOpLogDao.selectByMap(params);
	}

	private Integer selectCount(AdminOpLogQueryCondition condition) {
		validateCondition(condition);
		Map<String, Object> params = new HashMap<String, Object>();
		addConditionToMap(condition, params);
		return adminOpLogDao.selectByMapCount(params);
	}

	private void addConditionToMap(AdminOpLogQueryCondition condition,
			Map<String, Object> params) {
		if (condition.getUserNameKeyword() != null) {
			params.put("userNameKeyword", condition.getUserNameKeyword());
		}
		if (condition.getBeginTime() != null) {
			params.put("beginTime", new Timestamp(condition.getBeginTime()));
		}
		if (condition.getEndTime() != null) {
			params.put("endTime", new Timestamp(condition.getEndTime()));
		}
		if (condition.getEventKeyword() != null) {
			params.put("eventKeyword", condition.getEventKeyword());
		}
		if (condition.getLogTypeNameKeyword() != null) {
			List<Integer> logTypeCodes = new LinkedList<Integer>();
			for (AoLogType type : AoLogType.values()) {
				if (type.getName().contains(condition.getLogTypeNameKeyword())) {
					logTypeCodes.add(type.getCode());
				}
			}
			if (!logTypeCodes.isEmpty()) {
				params.put("logTypes", logTypeCodes);
			}
		}
		if (condition.getDescriptionKeyword() != null) {
			params.put("descriptionKeyword", condition.getDescriptionKeyword());
		}
		if (condition.getModuleKeyword() != null) {
			params.put("moduleKeyword", condition.getModuleKeyword());
		}
	}

	private void validateCondition(AdminOpLogQueryCondition condition) {
		if ("".equals(condition.getEventKeyword())) {
			condition.setEventKeyword(null);
		}
		if ("".equals(condition.getUserNameKeyword())) {
			condition.setUserNameKeyword(null);
		}
		if ("".equals(condition.getLogTypeNameKeyword())) {
			condition.setLogTypeNameKeyword(null);
		}
		if ("".equals(condition.getDescriptionKeyword())) {
			condition.setDescriptionKeyword(null);
		}
		if ("".equals(condition.getModuleKeyword())) {
			condition.setModuleKeyword(null);
		}
	}

	@Override
	public Page select(AdminOpLogQueryCondition condition, Integer currentPage,
			Integer recordsPerPage) {
		Page page = new Page();

		if (currentPage != null && recordsPerPage != null) {
			page.setCurrentPage(currentPage);
			page.setRecordsPerPage(recordsPerPage);
			page.setTotalRecords(selectCount(condition));
			page.setData(select(condition,
					new Page(currentPage, recordsPerPage)));
		} else {
			page.setCurrentPage(currentPage != null ? currentPage : 1);
			page.setRecordsPerPage(recordsPerPage != null ? recordsPerPage : 10);
			page.setTotalRecords(selectCount(condition));
			page.setData(select(condition, null));
		}

		return page;
	}

	@Override
	public void deleteRecent(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		java.util.Date endTime = calendar.getTime();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("endTime", endTime);
		adminOpLogDao.deleteByEndTime(params);
	}

}
