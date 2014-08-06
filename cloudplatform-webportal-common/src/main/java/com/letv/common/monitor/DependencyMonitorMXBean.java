/*
 * Pprun's Public Domain.
 */
package com.letv.common.monitor;

/**
 * check the status of the depenent component.
 * 
 * <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface DependencyMonitorMXBean extends Statistic {
    String getName();
    boolean isLiving();
}
