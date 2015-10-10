package com.letv.portal.service.operate.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.operate.IRecentOperateDao;
import com.letv.portal.model.operate.RecentOperate;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.operate.IRecentOperateService;

/**
 * 
 * @author lisuxiao
 *
 */
@Service("recentOperateService")
public class RecentOperateServiceImpl extends BaseServiceImpl<RecentOperate>  implements IRecentOperateService {

    private static final Logger logger = LoggerFactory.getLogger(RecentOperateServiceImpl.class);

    @Resource
	private IRecentOperateDao recentOperateDao;
	
	public RecentOperateServiceImpl() {
		super(RecentOperate.class);
	}

	@Override
	public IBaseDao<RecentOperate> getDao() {
		return this.recentOperateDao;
	}

	@Override
	public void saveInfo(String action, String content, Long userId, String descn) {
		RecentOperate operate = new RecentOperate();
		operate.setAction(action);
		operate.setContent(content);
		operate.setDescn(descn);
		operate.setCreateUser(userId);
		this.recentOperateDao.insert(operate);
	}

	@Override
	public List<RecentOperate> selectRecentOperate(Map<String, Object> params) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		params.put("date", cal.getTime());
		return this.recentOperateDao.selectByMap(params);
	}

}