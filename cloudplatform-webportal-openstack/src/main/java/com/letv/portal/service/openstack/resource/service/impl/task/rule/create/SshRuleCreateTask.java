package com.letv.portal.service.openstack.resource.service.impl.task.rule.create;

import org.jclouds.openstack.neutron.v2.domain.*;
import org.jclouds.openstack.neutron.v2.extensions.SecurityGroupApi;

/**
 * Created by zhouxianguang on 2015/12/1.
 */
public class SshRuleCreateTask implements RuleCreateTask {
    @Override
    public boolean isMatch(Rule rule) {
        return rule.getDirection() == RuleDirection.INGRESS
                && rule.getEthertype() == RuleEthertype.IPV4
                && rule.getProtocol() == RuleProtocol.TCP
                && "0.0.0.0/0".equals(rule.getRemoteIpPrefix())
                && rule.getPortRangeMin() == 22
                && rule.getPortRangeMax() == 22;
    }

    @Override
    public void create(SecurityGroupApi securityGroupApi, SecurityGroup securityGroup) {
        securityGroupApi.create(Rule.CreateRule
                .createBuilder(RuleDirection.INGRESS,
                        securityGroup.getId())
                .ethertype(RuleEthertype.IPV4)
                .protocol(RuleProtocol.TCP).portRangeMin(22)
                .portRangeMax(22).remoteIpPrefix("0.0.0.0/0").build());
    }
}
