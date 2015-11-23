package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.cloudvm.ICloudvmFlavorDao;
import com.letv.portal.dao.cloudvm.ICloudvmRegionDao;
import com.letv.portal.model.cloudvm.CloudvmFlavor;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.cloudvm.ICloudvmFlavorService;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cloudvmFlavorService")
public class CloudvmFlavorServiceImpl extends BaseServiceImpl<CloudvmFlavor>
        implements ICloudvmFlavorService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory
            .getLogger(CloudvmFlavorServiceImpl.class);

    @Resource
    private ICloudvmFlavorDao cloudvmFlavorDao;

    @Autowired
    private SessionServiceImpl sessionService;

    public CloudvmFlavorServiceImpl() {
        super(CloudvmFlavor.class);
    }

    @Override
    public IBaseDao<CloudvmFlavor> getDao() {
        return cloudvmFlavorDao;
    }

    @Override
    public CloudvmFlavor selectByFlavorId(String region, String flavorId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("flavorId", flavorId);
        List<CloudvmFlavor> resultList = selectByMap(paras);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }
}
