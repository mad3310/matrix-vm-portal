package com.letv.portal.proxy;

import com.letv.portal.model.swift.SwiftServer;

public interface ISwiftServerProxy extends IBaseProxy<SwiftServer> {
	
	public void saveAndBuild(SwiftServer swift);
	public void deleteAndBuild(Long swiftId);
}
