/*
 * Pprun's Public Domain.
 */
package com.letv.common.monitor;

import java.util.Set;

/**
 * monitor the general information of the server.
 * 
 * <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface ServerMonitorMXBean extends Statistic {

    String getHostName();

    int getPort();

    String getStartupAt();

    /**
     * The block IPs
     */
    Set<String> getBlackList();
}
