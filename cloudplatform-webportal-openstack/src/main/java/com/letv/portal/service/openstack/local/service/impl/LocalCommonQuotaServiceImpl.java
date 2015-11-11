package com.letv.portal.service.openstack.local.service.impl;

import com.letv.portal.model.common.CommonQuota;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.common.ICommonQuotaService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.util.CollectionUtil;
import com.letv.portal.service.openstack.util.ThreadUtil;
import com.letv.portal.service.openstack.util.function.Function;
import com.letv.portal.service.openstack.util.function.Function1;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Quota;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by zhouxianguang on 2015/11/11.
 */
@Service
public class LocalCommonQuotaServiceImpl implements LocalCommonQuotaSerivce {

    private ICommonQuotaService commonQuotaService;

    @Override
    public void updateLocalCommonQuotaService(final long userVoUserId, final String osTenantId, final NovaApi novaApi, final NeutronApi neutronApi, final CinderApi cinderApi) throws OpenStackException {
        ThreadUtil.concurrentRunAndWait(new Function<Void>() {
            @Override
            public Void apply() throws Exception {
                Set<String> regions = novaApi.getConfiguredRegions();
                ThreadUtil.concurrentFilter(CollectionUtil.toList(regions), new Function1<Void, String>() {
                    @Override
                    public Void apply(String region) throws Exception {
                        Quota quota = novaApi.getQuotaApi(region).get().getByTenant(osTenantId);
                        List<CommonQuota> commonQuotaList = commonQuotaService.insertDefaultAndSelectByRegion(userVoUserId, region);
                        ThreadUtil.concurrentFilter(commonQuotaList, new Function1<Void, CommonQuota>() {
                            @Override
                            public Void apply(CommonQuota commonQuota) throws Exception {
                                CommonQuotaType type = commonQuota.getType();
                                if (type == CommonQuotaType.CLOUDVM_CPU) {

                                } else if (type == CommonQuotaType.CLOUDVM_VM) {

                                } else if (type == CommonQuotaType.CLOUDVM_MEMORY){

                                }
                                return null;
                            }
                        });
                        return null;
                    }
                });
                return null;
            }
        }, new Function<Void>() {
            @Override
            public Void apply() throws Exception {
                Set<String> regions = neutronApi.getConfiguredRegions();
                ThreadUtil.concurrentFilter(CollectionUtil.toList(regions), new Function1<Void, String>() {
                    @Override
                    public Void apply(String region) throws Exception {

                        return null;
                    }
                });
                return null;
            }
        }, new Function<Void>() {
            @Override
            public Void apply() throws Exception {
                Set<String> regions = cinderApi.getConfiguredRegions();
                ThreadUtil.concurrentFilter(CollectionUtil.toList(regions), new Function1<Void, String>() {
                    @Override
                    public Void apply(String region) throws Exception {

                        return null;
                    }
                });
                return null;
            }
        });
    }
}
