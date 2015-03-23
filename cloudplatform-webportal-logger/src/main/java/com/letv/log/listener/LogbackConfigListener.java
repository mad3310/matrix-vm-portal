/*
 * @Title: LogbackConfigListener.java
 * @Package com.letv.log.listener
 * @Description: TODO
 * @author xufei1 <xufei1@letv.com>
 * @date 2012-12-6 下午2:53:45
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-6                          
 */
package com.letv.log.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/** 
 * <p></p>
 * 
 * @author xufei1 <xufei1@letv.com>
 * Create at:2012-12-6 下午2:53:45
 */
public class LogbackConfigListener implements ServletContextListener {
     
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		 LogbackWebConfigurer.initLogging(event.getServletContext());
	}

	public void contextInitialized(ServletContextEvent event) {  
		 LogbackWebConfigurer.initLogging(event.getServletContext());
	}

}
