package com.letv.portal.service.openstack.resource.service.impl.task.rule.create;

import org.jclouds.openstack.neutron.v2.domain.Rule;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2.extensions.SecurityGroupApi;

/**
 * Created by zhouxianguang on 2015/12/1.
 */
public interface RuleCreateTask {
    boolean isMatch(Rule rule);
    void create(SecurityGroupApi securityGroupApi, SecurityGroup securityGroup);
}
