package com.letv.portal.service.cloudvm;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.controller.cloudvm.Util;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMCreateConf;
import com.letv.portal.service.openstack.resource.manager.VMManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public class VMTest extends AbstractTest {
    private final static Logger logger = LoggerFactory
            .getLogger(VMTest.class);

    @Autowired
    private SessionServiceImpl sessionService;

    @Test
    public void createVM() {
//        OpenStackSession openStackSession = Util.session(sessionService);
//
//        ImageManager imageManager = openStackSession.getImageManager();
//        NetworkManager networkManager = openStackSession.getNetworkManager();
//        VMManager vmManager = openStackSession.getVMManager();
//
//        Set<String> regions=vmManager.getRegions();
//        String region=regions.iterator().next();
//
//        ImageResource imageResource = imageManager.list(region).iterator().next();
//
//        FlavorResource flavorResource = vmManager.getFlavorResource(region, flavorId);
//
//        List<NetworkResource> networkResources = new ArrayList<NetworkResource>(networkIds.length);
//        for (String networkId : networkIds) {
//            networkResources.add(networkManager.get(region, networkId));
//        }
//
//        VMCreateConf vmCreateConf = new VMCreateConf(name, imageResource, flavorResource, networkResources);
//        VMResource vmResource = vmManager.create(region, vmCreateConf);
    }
}
