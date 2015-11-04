package com.letv.portal.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.letv.portal.service.openstack.resource.FloatingIpResource;
import com.letv.portal.service.openstack.resource.RouterResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.VolumeResource;

public enum ProductType{
	
	VM(2l, VMResource.class, "openstack"),
	VOLUME(3l, VolumeResource.class, "openstack"),
	FLOATINGIP(4l, FloatingIpResource.class, "openstack"),
	ROUTER(5l, RouterResource.class, "openstack");
	
	private final Long id;
	private final Object type;
	private final String caller;
	
	private ProductType(Long id, Object type, String caller) {
		this.id = id;
		this.type = type;
		this.caller = caller;
	}

	public Long getId() {
		return id;
	}

	public Object getType() {
		return type;
	}
	
	public String getCaller() {
		return caller;
	}


	private static final Map<String, Map<Long,Object>> idToTypeMap = new HashMap<String, Map<Long, Object>>();
	static{
		ProductType[] productTypes=ProductType.values();
		for(ProductType productType:productTypes){
			Map<Long, Object> idAndType = null;
			if(idToTypeMap.get(productType.caller)==null) {
				idAndType = new HashMap<Long, Object>();
				idToTypeMap.put(productType.caller, idAndType);
			} else {
				idAndType = idToTypeMap.get(productType.caller);
			}
			idAndType.put(productType.id, productType.type);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T idToType(String caller, Long id){
		return (T)idToTypeMap.get(caller).get(id);
	}
	
}
