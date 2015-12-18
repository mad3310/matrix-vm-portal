package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.cloudvm.ICloudvmServerDao;
import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.service.cloudvm.ICloudvmServerService;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
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
        Session session = sessionService.getSession();
        if (session != null) {
            cloudvmServer.setCreateUser(session.getUserId());
        }
        super.insert(cloudvmServer);
    }

    @Override
    public void update(CloudvmServer cloudvmServer) {
        Session session = sessionService.getSession();
        if (session != null) {
            cloudvmServer.setUpdateUser(session.getUserId());
        }
        super.update(cloudvmServer);
    }

    @Override
    public CloudvmServer selectByServerId(String region, String serverId) {
        return selectByServerId(null, region, serverId);
    }

    @Override
    public CloudvmServer selectByServerId(Long userId, String region, String serverId) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(serverId)) {
            throw new ValidateException("虚拟机ID不能为空");
        }

        Map<String, Object> paras = new HashMap<String, Object>();
        if (userId != null) {
            paras.put("createUser", userId);
        }
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

    @Override
    public List<CloudvmServer> selectForSync(Long minId, Page page) {
        Map<String, Object> paras = new HashMap<String, Object>();
        if (minId != null) {
            paras.put("id", minId);
        }
        if (page != null) {
            paras.put("page", page);
        }
        return cloudvmServerDao.selectByMapForSync(paras);
    }

    @Override
    public List<CloudvmServer> selectByName(String region, String name, Page page) {
        return selectByName(null, region, name, page);
    }

    @Override
    public List<CloudvmServer> selectByName(Long userId, String region, String name, Page page) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        if (userId != null) {
            paras.put("createUser", userId);
        }
        paras.put("region", region);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        if (page != null) {
            paras.put("page", page);
        }
        return cloudvmServerDao.selectByMap(paras);
    }

    @Override
    public int selectCountByName(String region, String name) {
        return selectCountByName(null, region, name);
    }

    @Override
    public int selectCountByName(Long userId, String region, String name) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        if (userId != null) {
            paras.put("createUser", userId);
        }
        paras.put("region", region);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        return cloudvmServerDao.selectByMapCount(paras);
    }

}
