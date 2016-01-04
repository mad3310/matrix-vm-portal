package com.letv.lcp.openstack.util.cloudvm;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.v2_0.domain.Link;

import com.letv.portal.model.cloudvm.CloudvmServerLink;

/**
 * Created by zhouxianguang on 2015/9/29.
 */
public class CloudvmServerLinkUtil {
    public static boolean equal(CloudvmServerLink localLink, Link remoteLink) {
        String remoteLinkHref = remoteLink.getHref().toString();
        String remoteLinkRelation = remoteLink.getRelation().value();
        String remoteLinkType = null;
        if (remoteLink.getType().isPresent()) {
            remoteLinkType = remoteLink.getType().get();
        }
        if (!StringUtils.equals(localLink.getHref(), remoteLinkHref)) {
            return false;
        }
        if (!StringUtils.equals(localLink.getRelation(), remoteLinkRelation)) {
            return false;
        }
        if (!StringUtils.equals(localLink.getType(), remoteLinkType)) {
            return false;
        }
        return true;
    }
}
