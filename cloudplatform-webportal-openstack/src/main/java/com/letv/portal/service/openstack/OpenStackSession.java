package com.letv.portal.service.openstack;

import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMManager;

import java.io.Closeable;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface OpenStackSession extends Closeable{
    ImageManager getImageManager();
    
    NetworkManager getNetworkManager();
    
    VMManager getVMManager();

    boolean isClosed();

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
