package com.letv.portal.proxy.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.portal.enumeration.BackupStatus;
import com.letv.portal.model.BackupResultModel;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IBackupProxy;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IBackupService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IHclusterService;
import com.letv.portal.service.IMclusterService;
import com.mysql.jdbc.StringUtils;


@Component("backupProxy")
public class BackupProxyImpl extends BaseProxyImpl<BackupResultModel> implements IBackupProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(BackupProxyImpl.class);
	
	@Autowired
	private IBackupService backupService;
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IHclusterService hclusterService;
	@Autowired
	private IDbService dbService;
	@Autowired
	private IPythonService pythonService;
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;

	@Override
	public IBaseService<BackupResultModel> getService() {
		return backupService;
	}
	
	@Override
	public void backupTask() {
		this.backupTask(0); //all
	}
	@Override
	public void backupTask(int count) {
		//选择有意义的mcluster集群。  RUNNING(1),STARTING(7),STOPPING(8),STOPED(9),DANGER(13),CRISIS(14).
		if(count == 0)
			count = 5;
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("type", "rds");
		
		List<HclusterModel> hclusters = this.hclusterService.selectByMap(params);
		params.clear();
		
		List<MclusterModel> mclusters = new ArrayList<MclusterModel>();
		for (HclusterModel hcluster : hclusters) {
			params.put("hclusterId", hcluster.getId());
			mclusters.addAll(this.mclusterService.selectValidMclusters(count,params));
		}
		for (MclusterModel mclusterModel : mclusters) {
			//进行备份。
			this.wholeBackup4Db(mclusterModel);
		}
	}

	@Override
	@Async
	public void wholeBackup4Db(MclusterModel mcluster) {
		Date date = new Date();
		if(mcluster == null)
			return;
		//选择集群的第二个节点ip (ps:mcluster manager 要求打到第二个节点)。
		ContainerModel container = this.selectValidVipContianer(mcluster.getId(), "mclustervip");
		//选择该集群下的所有db库（不止一个）
		List<DbModel> dbModels = this.dbService.selectDbByMclusterId(mcluster.getId());
		
		if(container == null || dbModels.isEmpty()) return;
		
		BackupStatus status = BackupStatus.BUILDING;
		String resultDetail = "";
		//调用备份接口
		String result = this.pythonService.wholeBackup4Db(container.getIpAddr(),mcluster.getAdminUser(),mcluster.getAdminPassword());
		if(StringUtils.isNullOrEmpty(result)) {
			status = BackupStatus.FAILD;
			resultDetail = "Connection refused";
		} else {
			if(result.contains("\"code\": 200")) {
				status = BackupStatus.BUILDING;
			} else {
				status = BackupStatus.FAILD;
				resultDetail = "api not found";
			}
		}
		for (DbModel dbModel : dbModels) {
			//将备份记录写入数据库。
			BackupResultModel backup = new BackupResultModel();
			backup.setMclusterId(mcluster.getId());
			backup.setDbId(dbModel.getId());
			backup.setBackupIp(container.getIpAddr());
			backup.setStartTime(date);
			backup.setStatus(status);
			backup.setResultDetail(resultDetail);
			super.insert(backup);
			//发送邮件通知
			if(status == BackupStatus.FAILD)
				sendBackupFaildNotice(dbModel.getDbName(),mcluster.getMclusterName(), resultDetail,date,container.getIpAddr());
		}
	}
	
	private ContainerModel selectValidVipContianer(Long mclusterId,String type){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mclusterId", mclusterId);
		map.put("type", type);
		List<ContainerModel> containers = this.containerService.selectAllByMap(map);
		if(containers.isEmpty()) {
			return null;
		}
		return containers.get(0);
	}

	@Override
	public void checkBackupStatusTask(int count) {
		if(count == 0)
			count = 5;
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("status", BackupStatus.BUILDING);
		List<BackupResultModel> results = this.selectByMap(params);
	
		this.backupTask4addNew(count);
		
		for (BackupResultModel backup : results) {
			this.checkBackupStatus(backup);
		}
		
	}

	@Override
	@Async
	public void checkBackupStatus(BackupResultModel backup) {
		BackupStatus status = BackupStatus.BUILDING;
		String resultDetail = "";
		String result = this.pythonService.checkBackup4Db(backup.getBackupIp());
		if(StringUtils.isNullOrEmpty(result)) {
			status = BackupStatus.FAILD;
			resultDetail = "Connection refused";
		} else {
			if(result.contains("\"code\": 200")) {
				if(result.contains("backup success")) { 
					status = BackupStatus.SUCCESS;
				} else if(result.contains("backup is processing")) {
					status = BackupStatus.BUILDING;
				}
			} else if(result.contains("\"code\": 411")) {
				status = BackupStatus.FAILD;
				resultDetail = result.substring(result.indexOf("\"errorDetail\": \"")+1, result.lastIndexOf("\"},"));
			} else {
				status = BackupStatus.FAILD;
				resultDetail = "api not found";
			}
		}
		
		Date date = new Date();
		backup.setStatus(status);
		backup.setResultDetail(resultDetail);
		backup.setEndTime(date);
		this.backupService.updateBySelective(backup);
		
		
		if(status.equals(BackupStatus.FAILD)) {
			logger.info("check backup faild");
			//发送邮件通知
			sendBackupFaildNotice(backup.getDb().getDbName(),backup.getMcluster().getMclusterName(),resultDetail,backup.getStartTime(),backup.getBackupIp());
		}
	}
	
	private void backupTask4addNew(int count) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("type","rds");
		List<HclusterModel> hclusters = this.hclusterService.selectByMap(params);
		params.clear();
		for (HclusterModel hcluster : hclusters) {
			params.clear();
			params.put("status", BackupStatus.BUILDING);
			params.put("hclusterId", hcluster.getId());
			List<BackupResultModel> results = this.selectByMap(params);
			if(!results.isEmpty())
				count -= results.size();
			params.clear();
			params.put("hclusterId", hcluster.getId());
			BackupResultModel recentBackup = this.selectRecentBackup(params);
			if(recentBackup == null)
				continue;
			List<MclusterModel> mclusters = this.mclusterService.selectNextValidMclusterById(recentBackup.getMclusterId(), hcluster.getId(),count);
			for (MclusterModel mclusterModel : mclusters) {
				this.wholeBackup4Db(mclusterModel);
			}
			
		}
	}
	private BackupResultModel selectRecentBackup(Map<String,Object> params) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar curDate = Calendar.getInstance();
		curDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),curDate.get(Calendar.DATE), 0, 0, 0);
		params.put("startTime", format.format(new Date(curDate.getTimeInMillis())));
		List<BackupResultModel> results = this.backupService.selectByStatusAndDateOrderByMclusterId(params);
		if(results.isEmpty())
			return null;
		return results.get(0);
	}

	private void sendBackupFaildNotice(String dbName,String mclusterName,String resultDetail,Date startTime,String backupIp) {
		logger.info("check backup faild:send email--" + dbName + mclusterName);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", dbName);
		params.put("mclusterName", mclusterName);
		params.put("resultDetail", resultDetail);
		params.put("startTime", format.format(startTime));
		params.put("backupIp", backupIp);
		
		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",ERROR_MAIL_ADDRESS,"乐视云平台web-portal系统报警通知","backupFaildNotice.ftl",params);
		defaultEmailSender.sendMessage(mailMessage);
	}
	
	@Override
	@Async
	public void deleteOutData() {
		Map<String,Object> map = new  HashMap<String,Object>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -15);    //得到前15天
		long date = cal.getTimeInMillis();
		Date monthAgo = new Date(date);
		map.put("startTime", monthAgo);
		
		List<Map<String,Object>> ids = this.backupService.selectExtremeIdByMonitorDate(map);
		if(ids.isEmpty() || ids.get(0) == null || ids.get(0).isEmpty()) {
			return;
		}
		Map<String, Object> extremeIds = ids.get(0);
		Long max = (Long)extremeIds.get("maxId");
		Long min = (Long)extremeIds.get("minId");
		if(max == null || max == 0 || max == min)
			return;
		Long j = min;
		for (Long i = min; i <= max; i+=100) {
			j = i-100;
			map.put("min", j);
			map.put("max", i);
			this.backupService.deleteOutDataByIndex(map);
		}
		map.put("min", max-100);
		map.put("max", max);
		this.backupService.deleteOutDataByIndex(map);
		
		
		
	}
}
