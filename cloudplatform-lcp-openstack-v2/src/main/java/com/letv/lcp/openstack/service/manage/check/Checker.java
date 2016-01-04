package com.letv.lcp.openstack.service.manage.check;

public interface Checker<T> {
	boolean check(T o) throws Exception;
}
