package com.letv.portal.service.openstack.util;

public class Ref<T> {
	private T target;

	public Ref() {
	}

	public Ref(T target) {
		this();
		set(target);
	}

	public void set(T target) {
		this.target = target;
	}

	public T get() {
		return target;
	}
}
