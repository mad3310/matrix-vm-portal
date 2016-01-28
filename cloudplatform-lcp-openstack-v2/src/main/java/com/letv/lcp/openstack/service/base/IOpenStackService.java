package com.letv.lcp.openstack.service.base;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.conf.OpenStackConf;
import com.letv.lcp.openstack.service.session.IOpenStackSession;


/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface IOpenStackService {

    IOpenStackSession createSession(long userVoUserId, String email,
                                   String userName);
    IOpenStackSession createSession(long userId);
    /**
      * @Title: createSession
      * @Description: 创建openstack session
      * @param userId 用户id  
      * @param openStackConf openstack调用地址等相关信息
      * @return IOpenStackSession   
      * @throws 
      * @author lisuxiao
      * @date 2016年1月26日 下午5:58:04
      */
    IOpenStackSession createSession(long userId, OpenStackConf openStackConf);
    
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
