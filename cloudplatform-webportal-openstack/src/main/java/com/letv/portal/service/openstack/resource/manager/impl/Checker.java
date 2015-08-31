package com.letv.portal.service.openstack.resource.manager.impl;

public interface Checker<T> {
	boolean check(T o) throws Exception;
}
