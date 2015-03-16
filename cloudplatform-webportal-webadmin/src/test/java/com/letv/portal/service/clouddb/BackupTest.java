package com.letv.portal.service.clouddb;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IBackupProxy;
import com.letv.portal.service.IMclusterService;
 
public class BackupTest extends AbstractTest{

	@Autowired
	private IBackupProxy backupProxy;
	@Autowired
	private IMclusterService mclusterService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			BackupTest.class);

    @Test
	public void testWholeBackup4Db(){
    	long mclusterId = 22L; 
    	
    	MclusterModel mcluster = this.mclusterService.selectById(mclusterId);
    	this.backupProxy.wholeBackup4Db(mcluster);
	}
    
    @Test
    public void testCheckBackupStatusTask() {
		this.backupProxy.checkBackupStatusTask();
	}
	
    @Test
    public void testBackupTask() {
    	this.backupProxy.backupTask();
    }
    
    @Test
    public void testDeleteOutData() {
    	this.backupProxy.deleteOutData();
    }
    
}
