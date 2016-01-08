package com.letv.lcp.openstack.test.storage;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.billing.IResourceCreateService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.storage.IOpenstackStorageService;
import com.letv.lcp.openstack.test.junitBase.AbstractTest;
import com.letv.lcp.openstack.util.JsonUtil;
import com.letv.portal.service.common.IUserService;


public class OpenstackStorageService extends AbstractTest {

	@Autowired
	private IResourceCreateService resourceCreateService;
	@Autowired
    private IOpenStackService openStackService;
	@Autowired
    private IUserService userService;
	@Autowired
    private SessionServiceImpl sessionService;
	@Autowired
	private IOpenstackStorageService openstackStorageService;
	
	private IOpenStackSession openStackSession;
	
	private final static Logger logger = LoggerFactory.getLogger(OpenstackStorageService.class);
	
	private final static Long userId = 6l;
	private final static String volumeParam = "{\"region\":\"cn-beijing-1\",\"name\":\"newnew1122\",\"description\":\"\",\"volumeTypeId\":\"d3e50993-e9a7-415b-bbb7-f0b2e4e7dc50\",\"size\":10,\"volumeSnapshotId\":\"\",\"count\":1,\"order_time\":\"1\"}";
	
	@Before
	public void createOpenstackSession() throws OpenStackException {
        openStackSession = openStackService.createSession(userId);
        openStackSession.init(null);
        Session s = new Session();
        s.setUserId(6l);
        s.setOpenStackSession(openStackSession);
        sessionService.setSession(s, null);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
	}
	
	@Test
	public void testCreateStorage() {
		try {
			VolumeCreateConf storage = JsonUtil.fromJson(volumeParam, new TypeReference<VolumeCreateConf>() {
	        }, true);
			openstackStorageService.create(userId, storage, null, null, null);
			//方法中有使用线程池异步保存数据库
			Thread.sleep(10000l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
