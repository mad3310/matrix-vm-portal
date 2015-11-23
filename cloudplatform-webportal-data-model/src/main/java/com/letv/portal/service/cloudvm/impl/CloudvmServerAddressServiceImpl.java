package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmServerAddressDao;
import com.letv.portal.dao.cloudvm.ICloudvmServerLinkDao;
import com.letv.portal.model.cloudvm.CloudvmServerAddress;
import com.letv.portal.model.cloudvm.CloudvmServerLink;
import com.letv.portal.service.cloudvm.ICloudvmServerAddressService;
import com.letv.portal.service.cloudvm.ICloudvmServerLinkService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
@Service
public class CloudvmServerAddressServiceImpl extends BaseServiceImpl<CloudvmServerAddress> implements ICloudvmServerAddressService {

    @Resource
    private ICloudvmServerAddressDao cloudvmServerAddressDao;

    public CloudvmServerAddressServiceImpl() {
        super(CloudvmServerAddress.class);
    }

    @Override
    public IBaseDao<CloudvmServerAddress> getDao() {
        return cloudvmServerAddressDao;
    }

    @Override
    public List<CloudvmServerAddress> selectByRegionAndServerId(String region, String serverId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("serverId", serverId);
        return cloudvmServerAddressDao.selectByMap(paras);
    }

    @Override
    public void deleteByRegionAndServerId(String region, String serverId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("serverId", serverId);
        cloudvmServerAddressDao.deleteByRegionAndServerId(paras);
    }
}
