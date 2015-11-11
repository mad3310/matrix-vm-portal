package com.letv.portal.service.common.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.common.ICommonQuotaDao;
import com.letv.portal.model.common.CommonQuota;
import com.letv.portal.model.common.CommonQuotaModule;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.common.ICommonQuotaService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonQuotaServiceImpl extends BaseServiceImpl<CommonQuota> implements ICommonQuotaService {

    private final static Logger logger = LoggerFactory.getLogger(CommonQuotaServiceImpl.class);

    @Resource
    private ICommonQuotaDao commonQuotaDao;

    public CommonQuotaServiceImpl() {
        super(CommonQuota.class);
    }

    @Override
    public IBaseDao<CommonQuota> getDao() {
        return this.commonQuotaDao;
    }

    @Override
    public List<CommonQuota> selectByRegion(long tenantId, String region) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        return commonQuotaDao.selectByMap(paras);
    }

    @Override
    public List<CommonQuota> selectByRegionAndModule(long tenantId, String region, CommonQuotaModule module) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (module == null) {
            throw new ValidateException("模块不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("module", module);
        return commonQuotaDao.selectByMap(paras);
    }

    @Override
    public CommonQuota get(long tenantId, String region, CommonQuotaModule module, CommonQuotaType type) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (module == null) {
            throw new ValidateException("模块不能为空");
        }
        if (type == null) {
            throw new ValidateException("类型不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("module", module);
        paras.put("type", type);
        List<CommonQuota> commonQuotas = commonQuotaDao.selectByMap(paras);
        if (!commonQuotas.isEmpty()) {
            return commonQuotas.get(0);
        }
        return null;
    }

    @Override
    public List<CommonQuota> insertDefaultAndselectByRegion(long tenantId, String region) {
        List<CommonQuota> commonQuotas = selectByRegion(tenantId, region);
        Map<CommonQuotaType, CommonQuota> typeToQuota = new HashMap<CommonQuotaType, CommonQuota>();
        for (CommonQuota commonQuota : commonQuotas) {
            typeToQuota.put(commonQuota.getType(), commonQuota);
        }
        for (CommonQuotaType quotaType : CommonQuotaType.values()) {
            if (typeToQuota.get(quotaType) == null) {
                CommonQuota commonQuota = new CommonQuota();
                commonQuota.setRegion(region);
                commonQuota.setTenantId(tenantId);
                commonQuota.setCreateUser(tenantId);
                commonQuota.setModule(quotaType.getModule());
                commonQuota.setType(quotaType);
                commonQuota.setValue(quotaType.getDefaultQuota());
                insert(commonQuota);
                commonQuotas.add(commonQuota);
            }
        }
        return commonQuotas;
    }

    @Override
    public Map<String, Long> insertDefaultAndselectMapByRegion(long tenantId, String region) {
        List<CommonQuota> commonQuotas = insertDefaultAndselectByRegion(tenantId, region);
        Map<String, Long> typeToValue = new HashMap<String, Long>();
        for (CommonQuota commonQuota : commonQuotas) {
            typeToValue.put(commonQuota.getType().toString().toUpperCase(), commonQuota.getValue());
        }
        return typeToValue;
    }
}
