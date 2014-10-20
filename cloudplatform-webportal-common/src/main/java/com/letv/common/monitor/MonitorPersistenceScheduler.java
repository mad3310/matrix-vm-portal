/*
 * Pprun's Public Domain.
 */
package com.letv.common.monitor;


import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This job persistent the statistic to log file for audit while server crashed.
 * it is scheduled to run every 5 minutes.
 * 
 * <a href="mailto:quest.run@gmail.com">pprun</a>
 */

//@Component
public class MonitorPersistenceScheduler {
    private static final Logger log = LoggerFactory.getLogger(MonitorPersistenceScheduler.class);
    
    private Monitor monitor;
    
	//@Scheduled(cron="0/5 * * * * ?")
    @Scheduled(fixedRate = 5 * 1000)
    public void persistStatistics() {
    	//System.out.println("123");
        log.info("Persistent monitor statistic start ...");
        System.out.println("Persistent monitor statistic start ...");
        try {
            //log.info(monitor.asPlainText());
            System.out.println(monitor.asPlainText());
            System.out.println(monitor.asJson());
        } catch (Exception ex) {
            //log.error("error in persistent monitor statistic", ex);
            System.out.println("error in persistent monitor statistic"+ex);
        }
        //log.info("Persistent monitor statistic done!");
        System.out.println("Persistent monitor statistic done!");
    }

    @Autowired
    @Required
    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
    
  
}
