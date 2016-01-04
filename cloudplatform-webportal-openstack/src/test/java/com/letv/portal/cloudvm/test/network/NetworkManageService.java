package com.letv.portal.cloudvm.test.network;

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
import com.letv.portal.cloudvm.test.junitBase.AbstractTest;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.ResourceCreateService;
import com.letv.portal.service.openstack.exception.OpenStackException;


public class NetworkManageService extends AbstractTest {

	@Autowired
	private ResourceCreateService resourceCreateService;
	@Autowired
    private OpenStackService openStackService;
	@Autowired
    private IUserService userService;
	@Autowired
    private SessionServiceImpl sessionService;
	
	private OpenStackSession openStackSession;
	
	private final static Logger logger = LoggerFactory.getLogger(NetworkManageService.class);
	
	@Before
	public void createOpenstackSession() throws OpenStackException {
        openStackSession = openStackService.createSession(6l, "lisuxiao@letv.com", "lisuxiao");
        openStackSession.init(null);
        Session s = new Session();
        s.setUserId(6l);
        s.setOpenStackSession(openStackSession);
        sessionService.setSession(s, null);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
	}

	@Test
	public void testDeletePrivateNetwork() {
		try {
			openStackSession.getNetworkManager().deletePrivate("cn-beijing-1", "b8013e04-e8e4-4bd0-9553-61a5944e578a");
		} catch (OpenStackException e) {
			e.printStackTrace();
		}
	}
	
	
}
