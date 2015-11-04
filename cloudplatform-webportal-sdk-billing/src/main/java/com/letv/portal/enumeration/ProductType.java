package com.letv.portal.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.letv.portal.service.openstack.billing.BillingResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.VolumeResource;

public enum ProductType{
	
	VM(2l, VolumeResource.class, "openstack"),
	VOLUME(3l, VMResource.class, "openstack"),
	FLOATINGIP(4l, VMResource.class, "openstack"),
	ROUTER(5l, VMResource.class, "openstack");
	
	private final Long id;
	private final Class<?> type;
	private final String caller;
	
	private ProductType(Long id, Class<?> type, String caller) {
		this.id = id;
		this.type = type;
		this.caller = caller;
	}

	public Long getId() {
		return id;
	}

	public Class<?> getType() {
		return type;
	}
	
	public String getCaller() {
		return caller;
	}


	private static final Map<String, Map<Long,Class<?>>> idToTypeMap = new HashMap<String, Map<Long, Class<?>>>();
	static{
		ProductType[] productTypes=ProductType.values();
		for(ProductType productType:productTypes){
			Map<Long, Class<?>> idAndType = null;
			if(idToTypeMap.get(productType.caller)==null) {
				idAndType = new HashMap<Long, Class<?>>();
				idToTypeMap.put(productType.caller, idAndType);
			} else {
				idAndType = idToTypeMap.get(productType.caller);
			}
			idAndType.put(productType.id, productType.type);
		}
	}

	public static Class<?> idToType(String caller, Long id){
		return idToTypeMap.get(caller).get(id);
	}
	
}
