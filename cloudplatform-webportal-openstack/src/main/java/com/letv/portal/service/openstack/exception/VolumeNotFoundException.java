package com.letv.portal.service.openstack.exception;

public class VolumeNotFoundException extends ResourceNotFoundException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5162654075606572632L;

	public VolumeNotFoundException(String id) {
		super("Volume", "卷", id);
	}

}
