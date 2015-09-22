package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.cloudvm.ICloudvmServerDao;
import com.letv.portal.model.cloudvm.CloudvmFlavor;
import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.service.cloudvm.ICloudvmServerService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cloudvmServerService")
public class CloudvmServerServiceImpl extends BaseServiceImpl<CloudvmServer>
        implements ICloudvmServerService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory
            .getLogger(CloudvmServerServiceImpl.class);

    @Resource
    private ICloudvmServerDao cloudvmServerDao;

    @Autowired
    private SessionServiceImpl sessionService;

    public CloudvmServerServiceImpl() {
        super(CloudvmServer.class);
    }

    @Override
    public IBaseDao<CloudvmServer> getDao() {
        return cloudvmServerDao;
    }

    @Override
    public void insert(CloudvmServer cloudvmServer) {
        cloudvmServer.setCreateUser(sessionService.getSession().getUserId());
        super.insert(cloudvmServer);
    }

    @Override
    public void update(CloudvmServer cloudvmServer) {
        cloudvmServer.setUpdateUser(sessionService.getSession().getUserId());
        super.update(cloudvmServer);
    }

    @Override
    public CloudvmServer selectByServerId(String region, String serverId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("serverId", serverId);
        List<CloudvmServer> resultList = selectByMap(paras);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public int selectByUserIdCount(long userId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("createUser", userId);
        return cloudvmServerDao.selectByMapCount(paras);
    }

}
