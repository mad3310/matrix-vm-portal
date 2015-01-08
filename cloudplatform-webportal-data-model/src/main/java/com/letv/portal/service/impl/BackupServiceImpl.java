package com.letv.portal.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IBackupResultDao;
import com.letv.portal.model.BackupResultModel;
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

}
