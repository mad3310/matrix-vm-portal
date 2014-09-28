package com.letv.common.session;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import com.letv.common.exception.CommonException;

@Component("sessionService")
public class SessionServiceImpl {
   private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);
   private NamedThreadLocal<Session>  tlSession = new NamedThreadLocal<Session>("store of request status");  
   
   public void setSession(Session session, String message) {
      Session oldSession = getSession();
      tlSession.set(session);
      if (logger.isDebugEnabled()) {
         String logMessage = String.format("User session channged from %s to %s. Details: %s",
               oldSession != null ? oldSession : "[no old session]",
               session != null ? session : "[no new session]",
               message);
         logger.debug(logMessage);
      }
   }

   public Session getSession() {
	   Session session = tlSession.get();
      return session;
   }
   
   public <T> T runWithSession(Session session, String context, Executable<T> executable) {
	  final Session oldSession = getSession();
      try {
         setSession(session, "Session channged: " + context);
         return executable.execute();
      }catch (Throwable t) {
         throw new CommonException("Session channged occurs error!",t);
      }finally {	 
    	  if(oldSession == null)
    		  setSession(null, "Session recovered: " + context);
    	  else
    		  setSession(oldSession, "Session recovered: " + context);
      }
   }
}