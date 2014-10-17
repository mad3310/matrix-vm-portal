package com.letv.portal.python.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.util.ConfigUtil;
import com.letv.portal.constant.Constant;
import com.letv.portal.enumeration.BuildStatus;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.enumeration.DbUserStatus;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.BuildModel;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IBuildService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.IUserService;
import com.mysql.jdbc.StringUtils;

@Service("buildTaskService")
public class BuildTaskServiceImpl implements IBuildTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(BuildTaskServiceImpl.class);
	
	private static long PYTHON_CREATE_CHECK_TIME = ConfigUtil.getlong("python_create_check_time"); //300000;//单位：ms
	private static long PYTHON_CHECK_INTERVAL_TIME = ConfigUtil.getlong("python_check_interval_time");// 2000;//单位：ms
	
	private static long PYTHON_CREATE_INTERVAL_INIT_TIME = ConfigUtil.getlong("python_create_interval_init_time");//60000;//单位：ms
	
	private static long PYTHON_INIT_CHECK_TIME = ConfigUtil.getlong("python_init_check_time");//600000;//单位：ms
	private static long PYTHON_INIT_CHECK_INTERVAL_TIME = ConfigUtil.getlong("python_init_check_interval_time");//5000;//单位：ms
	
	@Resource
	private IMclusterService mclusterService;
	@Resource
	private IDbUserService dbUserService;
	@Resource
	private IPythonService pythonService;
	@Resource
	private IDbService dbService;
	@Resource
	private IContainerService containerService;
	@Resource
	private IBuildService buildService;
	@Resource
	private IUserService userService;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Override
	@Async
	public void buildMcluster(MclusterModel mclusterModel) {
		this.buildMcluster(mclusterModel, null);
	}
	
	@Override
	@Async
	public void buildMcluster(MclusterModel mclusterModel,Long dbId) {
		boolean nextStep = true;
		
		this.buildService.initStatus(mclusterModel.getId());
		
		try {
			if(nextStep) {
				nextStep = createContainer(mclusterModel,dbId);
			}
			if(nextStep) {
				nextStep = initContainer(mclusterModel,dbId);
			}
			if(nextStep) {
				mclusterModel.setStatus(MclusterStatus.RUNNING.getValue());
				this.mclusterService.audit(mclusterModel);
				this.buildResultToMgr("mcluster集群" + mclusterModel.getMclusterName(), "成功","", ERROR_MAIL_ADDRESS);
			}
		} catch (Exception e) {
			BuildModel nextBuild = new BuildModel();
			nextBuild.setMclusterId(mclusterModel.getId());
			nextBuild.setStartTime(new Date());
			nextBuild.setStatus(BuildStatus.FAIL.getValue());
			nextBuild.setMsg(e.getMessage());
			this.buildService.updateByStatus(nextBuild);
			mclusterModel.setStatus(MclusterStatus.BUILDFAIL.getValue());
			this.mclusterService.audit(mclusterModel);
			if(dbId!=null) {
				DbModel dbModel = new DbModel();
				dbModel.setId(dbId);
				dbModel.setStatus(DbStatus.BUILDFAIL.getValue());
				this.dbService.updateBySelective(dbModel);
			}
			this.buildResultToMgr("mcluster集群" + mclusterModel.getMclusterName(), "失败", e.getMessage(), ERROR_MAIL_ADDRESS);
			return;
		}
		if(nextStep && dbId != null) {
			this.buildDb(dbId);
		}
	}
	
	@Override
	public boolean createContainer(MclusterModel mclusterModel,Long dbId) {
		boolean nextStep = true;
		
		int step = 0;
		Date startTime = new Date();
		
		if(nextStep) {
			step++;
			nextStep = this.analysis(transResult(this.pythonService.createContainer(mclusterModel.getMclusterName())), step, startTime, mclusterModel.getId(), dbId);
		}
		if(nextStep) {
			step++;
			Map<String,Object> result =  transResult(pythonService.checkContainerCreateStatus(mclusterModel.getMclusterName()));
			Date checkStartDate = new Date();
			while(!analysisCheckCreateResult(result)) {
				try {
					Thread.sleep(PYTHON_CHECK_INTERVAL_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Date checkDate = new Date();
				long  diff = checkDate.getTime() - checkStartDate.getTime();
				if(diff >PYTHON_CREATE_CHECK_TIME) {
					BuildModel nextBuild = new BuildModel();
					nextBuild.setMclusterId(mclusterModel.getId());
					nextBuild.setStep(step);
					nextBuild.setStartTime(new Date());
					nextBuild.setStatus(BuildStatus.FAIL.getValue());
					nextBuild.setMsg("time over check");
					this.buildService.updateByStep(nextBuild);
					
					if(dbId!=null) {
						DbModel dbModel = new DbModel();
						dbModel.setId(dbId);
						dbModel.setStatus(DbStatus.BUILDFAIL.getValue());
						this.dbService.updateBySelective(dbModel);
					}
					mclusterModel.setStatus(MclusterStatus.BUILDFAIL.getValue());
					this.mclusterService.audit(mclusterModel);
					
					this.buildResultToMgr("mcluster集群" + mclusterModel.getMclusterName(), "失败", "check create containers time out", ERROR_MAIL_ADDRESS);
					return false;
				}
				result = transResult(pythonService.checkContainerCreateStatus(mclusterModel.getMclusterName()));
			}
			nextStep = analysis(result, step, startTime, mclusterModel.getId(), dbId);
			//保存container信息
			
			List<Map> containers = (List<Map>) ((Map)result.get("response")).get("containers");
			
			for (Map map : containers) {
				ContainerModel container = new ContainerModel();
				try {
					BeanUtils.populate(container, map);
					container.setMclusterId(mclusterModel.getId());
					container.setIpMask((String) map.get("netMask"));
					container.setContainerName((String) map.get("containerName"));
					//物理机集群维护完成后，修改此处，需要关联物理机id
//					container.setHostId((Long)map.get("hostIp"));
				}catch (Exception e) {
					e.printStackTrace();
				}
				this.containerService.insert(container);
			}
		}
		return nextStep;
	}
	
	@Override
	@Async
	public void buildDb(Long dbId) {
		Integer status = null;
		String resultMsg = "";
		String detail = "";
		Map<String,Object> params = this.dbService.selectCreateParams(dbId);
		try {
			String result = this.pythonService.createDb((String)params.get("nodeIp"), (String)params.get("dbName"), (String)params.get("dbName"), null, (String)params.get("username"), (String)params.get("password"));
			
			if(analysisResult(transResult(result))) {
				resultMsg = "成功";
				status = DbStatus.RUNNING.getValue();
				this.buildResultToUser("DB数据库" + params.get("dbName") + "创建",((BigInteger)params.get("createUser")).longValue());
			} else {
				resultMsg = "失败";
				status = DbStatus.BUILDFAIL.getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "失败";
			detail = e.getMessage();
			status = DbStatus.BUILDFAIL.getValue();
		} finally {
			this.buildResultToMgr("DB数据库" + params.get("dbName"), resultMsg, detail, ERROR_MAIL_ADDRESS);
			DbModel dbModel = new DbModel();
			dbModel.setId(dbId);
			dbModel.setStatus(status);
			this.dbService.updateBySelective(dbModel);
		}
	}
    

	@Override
	public void buildUser(String ids) {
		String[] str = ids.split(",");
		String resultMsg = "";
		String detail = "";
		for (String id : str) {
			//查询所属db 所属mcluster 及container数据
			DbUserModel dbUserModel = this.dbUserService.selectById(Long.parseLong(id));
			Map<String,Object> params = this.dbUserService.selectCreateParams(Long.parseLong(id));
			try {
				String result = this.pythonService.createDbUser(dbUserModel, (String)params.get("dbName"), (String)params.get("nodeIp"), (String)params.get("username"), (String)params.get("password"));
				if(analysisResult(transResult(result))) {
					resultMsg="成功";
					dbUserModel.setStatus(DbUserStatus.RUNNING.getValue());
					this.buildResultToUser("DB数据库("+params.get("dbName")+")用户" + dbUserModel.getUsername() + "创建", ((BigInteger)params.get("createUser")).longValue());
				} else {
					resultMsg="失败";
					dbUserModel.setStatus(DbUserStatus.BUILDFAIL.getValue());
				}
			} catch (Exception e) {
				resultMsg="失败";
				detail = e.getMessage();
				dbUserModel.setStatus(DbUserStatus.BUILDFAIL.getValue());
			} finally {
				this.buildResultToMgr("DB数据库("+params.get("dbName")+")用户" + dbUserModel.getUsername(), resultMsg, detail, ERROR_MAIL_ADDRESS);
				this.dbUserService.updateStatus(dbUserModel);
			}
		}
		
	}
	/**
	 * Methods Name: deleteDbUser <br>
	 * Description: 删除 DbUser<br>
	 * @author name: wujun
	 * @param dbUserId
	 */
	public void deleteDbUser(String ids){
		String[] str = ids.split(",");	
		String resultMsg = "";
		String detail = "";
		for (String id : str) {
		    DbUserModel dbUserModel = this.dbUserService.selectById(Long.parseLong(id));
		    if(DbStatus.RUNNING.getValue() == dbUserModel.getStatus()) {
		    	Map<String,Object> params = this.dbUserService.selectCreateParams(Long.parseLong(id));
		    	try {
		    		String result = this.pythonService.deleteDbUser(dbUserModel, (String)params.get("dbName"), (String)params.get("nodeIp"), (String)params.get("username"), (String)params.get("password"));
		    		if(analysisResult(transResult(result))) {
		    			resultMsg="用户删除成功";
		    			this.buildResultToUser("DB数据库("+params.get("dbName")+")用户" + dbUserModel.getUsername() + "删除",((BigInteger)params.get("createUser")).longValue());
		    		} else {
		    			resultMsg="用户删除失败";
		    		}
		    	} catch (Exception e) {
		    		detail = e.getMessage();
		    		resultMsg="用户删除失败";
		    	}finally{
		    		this.buildResultToMgr("DB数据库("+params.get("dbName")+")用户" + dbUserModel.getUsername(), resultMsg, detail, ERROR_MAIL_ADDRESS);
		    	}
		    }
		}
	}
	
	@Override
	public boolean initContainer(MclusterModel mclusterModel,Long dbId) {
		
		boolean nextStep = true;
		List<ContainerModel> containers = this.containerService.selectByMclusterId(mclusterModel.getId());
		
		int step = 2;
		//假设数据
		String username = mclusterModel.getAdminUser();
		String password = mclusterModel.getAdminPassword();
		
		String mclusterName = mclusterModel.getMclusterName();
		
		String nodeIp1 = containers.get(0).getIpAddr();
		String nodeName1 = containers.get(0).getContainerName();
		
		String nodeIp2 = containers.get(1).getIpAddr();
		String nodeName2 = containers.get(1).getContainerName();

		String nodeIp3 = containers.get(2).getIpAddr();
		String nodeName3 = containers.get(2).getContainerName();
		
		Long mclusterId = mclusterModel.getId();
		
		/*mcluster-manager测试用集群
		
		物理机
		10.200.91.142
		10.200.91.143
		10.200.91.144
		root/dabingge$1985
		*/
		
		Date startTime = new Date();
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.initZookeeper(nodeIp1)),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.initUserAndPwd4Manager(nodeIp1,username,password)),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.postMclusterInfo(mclusterName, nodeIp1, nodeName1, username, password)),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.initMcluster(nodeIp1, username, password)),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.syncContainer(nodeIp2, username, password)),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.postContainerInfo(nodeIp2, nodeName2, username, password)),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.syncContainer(nodeIp3, username, password)),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.postContainerInfo(nodeIp3, nodeName3, username, password)),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(transResult(this.pythonService.startMcluster(nodeIp1, username, password)),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			Map<String,Object> result =  transResult(pythonService.checkContainerStatus(nodeIp1, username, password));
			Date checkStartDate = new Date();
			while(!analysisCheckInitResult(result)) {
				try {
					Thread.sleep(PYTHON_INIT_CHECK_INTERVAL_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Date checkDate = new Date();
				long  diff = checkDate.getTime() - checkStartDate.getTime();
				if(diff >PYTHON_INIT_CHECK_TIME) {
					BuildModel nextBuild = new BuildModel();
					nextBuild.setMclusterId(mclusterModel.getId());
					nextBuild.setStep(step);
					nextBuild.setStartTime(new Date());
					nextBuild.setStatus(BuildStatus.FAIL.getValue());
					nextBuild.setMsg("time over check");
					this.buildService.updateByStep(nextBuild);
					mclusterModel.setStatus(MclusterStatus.BUILDFAIL.getValue());
					this.mclusterService.audit(mclusterModel);
					this.buildResultToMgr("mcluster集群"+mclusterModel.getMclusterName(), "失败", "check init containers time out", ERROR_MAIL_ADDRESS);
					return false;
				}
				result = transResult(pythonService.checkContainerStatus(nodeIp1, username, password));
			}
			nextStep = analysis(result, step, startTime, mclusterModel.getId(), null);
		}
		return nextStep;
	}
	
	private boolean analysis(Map<String,Object> jsonResult,int step,Date startTime,Long mclusterId,Long dbId){
		Map<String,Object> meta = (Map)jsonResult.get("meta");
		BuildModel buildModel = new BuildModel();
		
		buildModel.setMclusterId(mclusterId);
		buildModel.setDbId(dbId);
		buildModel.setStep(step);
		buildModel.setStartTime(startTime);
		buildModel.setEndTime(new Date());
		
		boolean flag = true;
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			Map<String,Object> response = (Map)jsonResult.get("response");
			buildModel.setCode((String)response.get("code"));
			buildModel.setMsg((String) response.get("message"));
			buildModel.setStatus(BuildStatus.SUCCESS.getValue());
		} else {
			buildModel.setCode(String.valueOf(meta.get("code")));
			buildModel.setMsg((String)meta.get("errorDetail"));
			buildModel.setStatus(BuildStatus.FAIL.getValue());
			flag =  false;
			this.buildResultToMgr("mcluster集群", "失败", (String)meta.get("errorDetail"), ERROR_MAIL_ADDRESS);
			MclusterModel mclusterModel = new MclusterModel();
			mclusterModel.setId(mclusterId);
			mclusterModel.setStatus(MclusterStatus.BUILDFAIL.getValue());
			this.mclusterService.audit(mclusterModel);
		}
		this.buildService.updateByStep(buildModel);
		if(flag) {
			BuildModel nextBuild = new BuildModel();
			nextBuild.setMclusterId(mclusterId);
			nextBuild.setStep(step+1);
			nextBuild.setStartTime(new Date());
			nextBuild.setStatus(BuildStatus.BUILDING.getValue());
			this.buildService.updateByStep(nextBuild);
		}
		return flag;
	}
	
	private Map<String,Object> transResult(String result){
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	private boolean analysisResult(Map result){
		boolean flag = false;
		Map meta = (Map) result.get("meta");
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			flag = true;
		} 
		return flag;
	}
	private boolean analysisCheckCreateResult(Map result){
		boolean flag = false;
		Map meta = (Map) result.get("meta");
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			if(Constant.PYTHON_API_RESULT_SUCCESS.equals(((Map)result.get("response")).get("code"))) {
				flag = true;
			}
		} 
		return flag;
	}
	private boolean analysisCheckInitResult(Map result){
		boolean flag = false;
		Map meta = (Map) result.get("meta");
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			if(Constant.MCLUSTER_INIT_STATUS_RUNNING.equals(((Map)result.get("response")).get("message"))) {
				flag = true;
			}
		} 
		return flag;
	}
	
	@Override
	public void buildResultToMgr(String buildType,String result,String detail,String to){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("buildType", buildType);
		map.put("buildResult", result);
		map.put("errorDetail", detail);
		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统", StringUtils.isNullOrEmpty(to)?ERROR_MAIL_ADDRESS:to,"乐视云平台web-portal系统通知","buildForMgr.ftl",map);
		try {
			defaultEmailSender.sendMessage(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	@Override
	public void buildResultToUser(String buildType,Long to){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("buildType", buildType);
		UserModel user = this.userService.selectById(to);
		if(null != user) {
			MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",user.getEmail(),"乐视云平台web-portal系统通知","buildForUser.ftl",map);
			try {
				defaultEmailSender.sendMessage(mailMessage);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
		}
	}
	@Override
	public void removeMcluster(MclusterModel mcluster) {
		this.mclusterService.delete(mcluster);
		this.containerService.deleteByMcluster(mcluster.getId());
		this.buildService.deleteByMclusterId(mcluster.getId());
	}
}
