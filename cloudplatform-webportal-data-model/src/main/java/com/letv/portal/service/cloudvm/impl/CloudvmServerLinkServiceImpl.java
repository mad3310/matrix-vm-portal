package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmServerLinkDao;
import com.letv.portal.model.cloudvm.CloudvmServerLink;
import com.letv.portal.service.cloudvm.ICloudvmServerLinkService;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
@Service
public class CloudvmServerLinkServiceImpl extends BaseServiceImpl<CloudvmServerLink> implements ICloudvmServerLinkService {

    @Resource
    private ICloudvmServerLinkDao cloudvmServerLinkDao;

    public CloudvmServerLinkServiceImpl() {
        super(CloudvmServerLink.class);
    }

    @Override
    public IBaseDao<CloudvmServerLink> getDao() {
        return cloudvmServerLinkDao;
    }

    @Override
    public List<CloudvmServerLink> selectByRegionAndServerId(String region, String serverId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("serverId", serverId);
        return cloudvmServerLinkDao.selectByMap(paras);
    }

    @Override
    public void deleteByRegionAndServerId(String region, String serverId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("serverId", serverId);
        cloudvmServerLinkDao.deleteByRegionAndServerId(paras);
    }
}
