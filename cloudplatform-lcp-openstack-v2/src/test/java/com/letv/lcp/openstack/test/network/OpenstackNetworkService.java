package com.letv.lcp.openstack.test.network;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.billing.IResourceCreateService;
import com.letv.lcp.openstack.service.network.IOpenstackNetworkService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.test.junitBase.AbstractTest;
import com.letv.lcp.openstack.util.JsonUtil;
import com.letv.portal.service.common.IUserService;


public class OpenstackNetworkService extends AbstractTest {

	@Autowired
	private IResourceCreateService resourceCreateService;
	@Autowired
    private IOpenStackService openStackService;
	@Autowired
    private IUserService userService;
	@Autowired
    private SessionServiceImpl sessionService;
	@Autowired
	private IOpenstackNetworkService openstackNetworkService;
	
	private IOpenStackSession openStackSession;
	
	private final static Logger logger = LoggerFactory.getLogger(OpenstackNetworkService.class);
	
	private final static Long userId = 6l;
	private final static String routerParam = "{\"region\":\"cn-beijing-1\",\"name\":\"testoknew11\",\"enablePublicNetworkGateway\":\"true\",\"publicNetworkId\":\"5663fa0c-ec67-40d8-bc56-b206672280fb\",\"count\":1,\"order_time\":\"1\"}";
	private final static String ipParam = "{\"region\":\"cn-beijing-1\",\"name\":\"nihaonew\",\"publicNetworkId\":\"5663fa0c-ec67-40d8-bc56-b206672280fb\",\"bandWidth\":1,\"count\":1,\"order_time\":\"1\"}";
	
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
	@Ignore
	public void testCreateRouter() {
		try {
			RouterCreateConf storage = JsonUtil.fromJson(routerParam, new TypeReference<RouterCreateConf>() {
	        }, true);
			openstackNetworkService.createRouter(userId, storage, null, null);
		} catch (OpenStackException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateFloatingIp() {
		try {
			FloatingIpCreateConf floatingIpCreateConf = JsonUtil.fromJson(ipParam, new TypeReference<FloatingIpCreateConf>() {
			}, true);
			openstackNetworkService.createFloatingIp(userId, floatingIpCreateConf, null, null);
		} catch (OpenStackException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testDeletePrivateNetwork() {
		try {
			openStackSession.getNetworkManager().deletePrivate("cn-beijing-1", "b8013e04-e8e4-4bd0-9553-61a5944e578a");
		} catch (OpenStackException e) {
			e.printStackTrace();
		}
	}
	
	
}
