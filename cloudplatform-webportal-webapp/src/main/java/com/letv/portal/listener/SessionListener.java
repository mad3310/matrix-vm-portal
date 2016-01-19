package com.letv.portal.listener;

import java.io.IOException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.common.session.Session;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.session.IOpenStackSession;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public class SessionListener implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        Session session = (Session) sessionEvent.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
        if (session != null) {
            IOpenStackSession openStackSession = (IOpenStackSession) session.getOpenStackSession();
            if (openStackSession != null && !openStackSession.isClosed()) {
                try {
                    openStackSession.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            OpenStackServiceImpl.getOpenStackServiceGroup().getApiService().clearCache(session.getUserId(), sessionEvent.getSession().getId());
        }
    }
}
