package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.ResourceCreateService;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.MultiVmCreateContext;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.VMCreateConf2;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.VmCreateContext;
import com.letv.portal.service.openstack.util.Util;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
@Service
public class ResourceCreateServiceImpl implements ResourceCreateService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceCreateServiceImpl.class);

//    @Autowired
//    private SessionServiceImpl sessionService;

    @Autowired
    private OpenStackService openStackService;

    @Autowired
    private IUserService userService;

    @Override
    public List<ResourceLocator> createVm(long userId, String reqParaJson) throws MatrixException {
        try {
//            Session session = sessionService.getSession();
//            logger.info("ResourceCreateServiceImpl.createVm session = ", session);

            VMCreateConf2 vmCreateConf = Util.fromJson(reqParaJson, new TypeReference<VMCreateConf2>() {
            });

            UserVo userVo = userService.getUcUserById(userId);
            OpenStackSession openStackSession = openStackService.createSession(Long.toString(userId), userVo.getEmail(), userVo.getUsername());
            openStackSession.init(true);

            MultiVmCreateContext multiVmCreateContext = openStackSession.getVMManager().create2(vmCreateConf);

            List<ResourceLocator> resourceLocators = new LinkedList<ResourceLocator>();
            for (VmCreateContext vmCreateContext : multiVmCreateContext.getVmCreateContexts()) {
                resourceLocators.add(new ResourceLocator(multiVmCreateContext.getVmCreateConf().getRegion(), vmCreateContext.getServer().getId()));
            }
            return resourceLocators;
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }
}
