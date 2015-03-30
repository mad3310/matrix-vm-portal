package com.letv.portal.service.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.CommonException;
import com.letv.portal.dao.IMonitorDao;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.monitor.MonitorViewYModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;

@Service("monitorServiceByJdbc")
public class MonitorServiceImplByJdbc extends BaseServiceImpl<MonitorDetailModel> implements IMonitorService {

	private final static Logger logger = LoggerFactory.getLogger(MonitorServiceImplByJdbc.class);
	
	@Autowired
	private IMonitorDao monitorDao;
	
	@Autowired
	private IMonitorIndexService monitorIndexService;
	
	@Autowired
	private IContainerService containerService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public MonitorServiceImplByJdbc() {
		super(MonitorDetailModel.class);
	}

	@Override
	public void deleteOutDataByIndex(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ").append(map.get("dbName")).append(" where id between ? and ?");
		logger.debug("delete sql:" + sql.toString());
		try {
			jdbcTemplate.update(sql.toString(), new Object[] {map.get("min"),map.get("max")},
			          new int[] {java.sql.Types.INTEGER,java.sql.Types.INTEGER});
		} catch (Exception e) {
			logger.debug("delete monitor data error:" + sql.toString() + ",errorDetail:" + e.getMessage());
			throw new CommonException("delete monitor data error:" + sql.toString());
		}
	}

	@Override
	public List<MonitorViewYModel> getMonitorViewData(Long MclusterId,
			Long chartId, Integer strategy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonitorViewYModel> getDbData(String ip, Long chartId,
			Integer strategy, boolean isTimeAveraging) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> selectDistinct(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonitorDetailModel> selectDateTime(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonitorIndexModel> selectMonitorCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float selectDbStorage(Long mclusterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectDbConnect(Long mclusterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectExtremeIdByMonitorDate(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBaseDao<MonitorDetailModel> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
