package com.letv.lcp.openstack.service.session;

import java.io.Closeable;
import java.io.Serializable;

import com.letv.common.session.Session;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.conf.OpenStackConf;
import com.letv.lcp.openstack.model.user.OpenStackUser;
import com.letv.lcp.openstack.service.manage.ImageManager;
import com.letv.lcp.openstack.service.manage.NetworkManager;
import com.letv.lcp.openstack.service.manage.VMManager;
import com.letv.lcp.openstack.service.manage.VolumeManager;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface IOpenStackSession extends Closeable, Serializable {
    ImageManager getImageManager() throws OpenStackException;
    
    NetworkManager getNetworkManager() throws OpenStackException;
    
    VMManager getVMManager() throws OpenStackException;
    
    VolumeManager getVolumeManager() throws OpenStackException;

    boolean isClosed();

    boolean isAuthority();

    void init(Session session) throws OpenStackException;

    void init() throws OpenStackException;
    
    OpenStackUser getOpenStackUser();
    
    OpenStackConf getOpenStackConf();
    

//    Set<String> listRegions();
//
//    VM createVM(String region, VMConf conf);
//
//    void deleteVM(String region, VM vm);
//
//    List<VM> listVM(String region);
//
//    void startVM(String region, VM vm);
//
//    void stopVM(String region, VM vm);
    
    // vm image network identify
    
}
