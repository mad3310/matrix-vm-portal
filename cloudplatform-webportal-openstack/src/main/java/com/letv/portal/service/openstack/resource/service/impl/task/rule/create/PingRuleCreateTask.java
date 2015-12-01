package com.letv.portal.service.openstack.resource.service.impl.task.rule.create;

import org.jclouds.openstack.neutron.v2.domain.*;
import org.jclouds.openstack.neutron.v2.extensions.SecurityGroupApi;

/**
 * Created by zhouxianguang on 2015/12/1.
 */
public class PingRuleCreateTask implements RuleCreateTask{
    @Override
    public boolean isMatch(Rule rule) {
        return rule.getDirection() == RuleDirection.INGRESS
                && rule.getEthertype() == RuleEthertype.IPV4
                && rule.getProtocol() == RuleProtocol.ICMP
                && "0.0.0.0/0".equals(rule.getRemoteIpPrefix());
    }

    @Override
    public void create(SecurityGroupApi securityGroupApi, SecurityGroup securityGroup) {
        securityGroupApi.create(Rule.CreateRule
                .createBuilder(RuleDirection.INGRESS,
                        securityGroup.getId())
                .ethertype(RuleEthertype.IPV4)
                .protocol(RuleProtocol.ICMP)
                .remoteIpPrefix("0.0.0.0/0").portRangeMax(255).portRangeMin(0).build());
    }
}
