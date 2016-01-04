package com.letv.lcp.openstack.service.local.impl;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.local.ILocalCommonQuotaSerivce;
import com.letv.portal.model.common.CommonQuota;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.common.ICommonQuotaService;

/**
 * Created by zhouxianguang on 2015/11/11.
 */
@Service
public class LocalCommonQuotaServiceImpl implements ILocalCommonQuotaSerivce {

    @Autowired
    private ICommonQuotaService commonQuotaService;

    @Override
    public void checkQuota(long userVoUserId, String region, CommonQuotaType type, int value) throws OpenStackException {
        CommonQuota commonQuota = commonQuotaService.insertDefaultAndGet(userVoUserId, region, type.getModule(), type);
        if (commonQuota == null) {
            throw new OpenStackException("User quota is not exists.", MessageFormat.format("用户的{0}配额不可用", type.getName()));
        }
        if (commonQuota.getValue() < value) {
            Object[] arguments = new Object[]{type.getName(), type.getUnit(), commonQuota.getValue(), value, value - commonQuota.getValue()};
            throw new UserOperationException(
                    MessageFormat.format("{0} exceeding the quota,tenantId:{1},region:{2},value:{3}", type.toString(), userVoUserId, region, value),
                    MessageFormat.format("{0}超出配额{4}{1}", arguments));
        }
    }

//    @Override
//    public void updateLocalCommonQuotaService(final long userVoUserId, final String osTenantId, final NovaApi novaApi, final NeutronApi neutronApi, final CinderApi cinderApi) throws OpenStackException {
//        ThreadUtil.concurrentRunAndWait(new Function0<Void>() {
//            @Override
//            public Void apply() throws Exception {
//                Set<String> regions = novaApi.getConfiguredRegions();
//                ThreadUtil.concurrentFilter(CollectionUtil.toList(regions), new Function1<Void, String>() {
//                    @Override
//                    public Void apply(String region) throws Exception {
//                        final Quota quota = novaApi.getQuotaApi(region).get().getByTenant(osTenantId);
//                        List<CommonQuota> commonQuotaList = commonQuotaService.insertDefaultAndSelectByRegion(userVoUserId, region);
//                        ThreadUtil.concurrentFilter(commonQuotaList, new Function1<Void, CommonQuota>() {
//                            @Override
//                            public Void apply(CommonQuota commonQuota) throws Exception {
//                                CommonQuotaType type = commonQuota.getType();
//                                Integer lastestValue = null;
//                                if (type == CommonQuotaType.CLOUDVM_VM) {
//                                    lastestValue = quota.getInstances();
//                                } else if (type == CommonQuotaType.CLOUDVM_CPU) {
//                                    lastestValue = quota.getCores();
//                                } else if (type == CommonQuotaType.CLOUDVM_MEMORY) {
//                                    lastestValue = quota.getRam();
//                                } else if (type == CommonQuotaType.CLOUDVM_VM) {
//                                    quota.get
//                                }
//                                if (lastestValue != null && commonQuota.getValue() != lastestValue.longValue()) {
//                                    commonQuota.setValue(lastestValue.longValue());
//                                    commonQuotaService.update(commonQuota);
//                                }
//                                return null;
//                            }
//                        });
//                        return null;
//                    }
//                });
//                return null;
//            }
//        }, new Function0<Void>() {
//            @Override
//            public Void apply() throws Exception {
//                Set<String> regions = neutronApi.getConfiguredRegions();
//                ThreadUtil.concurrentFilter(CollectionUtil.toList(regions), new Function1<Void, String>() {
//                    @Override
//                    public Void apply(String region) throws Exception {
//
//                        return null;
//                    }
//                });
//                return null;
//            }
//        }, new Function0<Void>() {
//            @Override
//            public Void apply() throws Exception {
//                Set<String> regions = cinderApi.getConfiguredRegions();
//                ThreadUtil.concurrentFilter(CollectionUtil.toList(regions), new Function1<Void, String>() {
//                    @Override
//                    public Void apply(String region) throws Exception {
//
//                        return null;
//                    }
//                });
//                return null;
//            }
//        });
//    }
}
