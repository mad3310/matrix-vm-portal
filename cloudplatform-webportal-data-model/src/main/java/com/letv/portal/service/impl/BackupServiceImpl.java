package com.letv.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IBackupResultDao;
import com.letv.portal.model.BackupResultModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IBackupService;

/**Program Name: BackupServiceImpl <br>
 * Description:  备份结果service<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年1月5日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Service("backupService")
public class BackupServiceImpl extends BaseServiceImpl<BackupResultModel> implements IBackupService{
	
	private final static Logger logger = LoggerFactory.getLogger(BackupServiceImpl.class);
	
	@Resource
	private IBackupResultDao backupResultDao;

	public BackupServiceImpl() {
		super(BackupResultModel.class);
	}

	@Override
	public IBaseDao<BackupResultModel> getDao() {
		return this.backupResultDao;
	}

	@Override
	public void deleteByMclusterId(Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mclusterId", id);
		List<BackupResultModel> backups = this.backupResultDao.selectByMap(map);
		for (BackupResultModel backup : backups) {
			super.delete(backup);
		}
	}
}
