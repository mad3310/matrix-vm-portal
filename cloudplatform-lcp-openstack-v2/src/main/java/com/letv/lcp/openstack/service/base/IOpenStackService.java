package com.letv.lcp.openstack.service.base;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.conf.OpenStackConf;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.portal.model.cloudvm.CloudvmRegion;


/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface IOpenStackService {

    IOpenStackSession createSession(long userVoUserId, String email,
                                   String userName);
    IOpenStackSession createSession(long userId);
    /**
      * @Title: createSession
      * @Description: 根据openstack租户id创建openstack session
      * @param userId 申请用户id
      * @param tenantId 租户id
      * @return IOpenStackSession   
      * @throws 
      * @author lisuxiao
      * @date 2016年2月17日 上午11:31:00
      */
    IOpenStackSession createSession(Long userId, Long tenantId);
    /**
      * @Title: createAdminSession
      * @Description: 创建管理员session
      * @param userId
      * @param tenantId
      * @return IOpenStackSession   
      * @throws 
      * @author lisuxiao
      * @date 2016年3月2日 上午11:24:41
      */
    IOpenStackSession createAdminSession(Long userId, Long tenantId);
    /**
      * @Title: createSession
      * @Description: 创建openstack session
      * @param applyUserId 申请人id  
      * @param applyUserEmail 申请人邮箱  
      * @param applyUserName 申请人用户名  
      * @param vmRegion 集群信息包括调用地址,用户信息  
      * @return IOpenStackSession   
      * @throws 
      * @author lisuxiao
      * @date 2016年1月26日 下午5:58:04
      */
    IOpenStackSession createSession(Long applyUserId, String applyUserEmail, String applyUserName, CloudvmRegion vmRegion);
    
    /**
      * @Title: getOpenStackConfDefault
      * @Description: 获取默认openstack参数配置信息
      * @return OpenStackConf   
      * @throws 
      * @author lisuxiao
      * @date 2016年1月26日 下午7:53:50
      */
    OpenStackConf getOpenStackConfDefault();

    String getOpenStackTenantNameFromMatrixUser(long userVoUserId, String email);

    boolean isUserExists(String tenantName, String password) throws OpenStackException;

    void registerUser(String tenantName, String password, String email) throws OpenStackException;

    void registerUserIfNotExists(String tenantName, String password, String email) throws OpenStackException;

    void registerAndInitUserIfNotExists(String tenantName, String password, String email) throws OpenStackException;

//    OpenStackSession createSessionForSync(long userVoUserId) throws OpenStackException;
}
