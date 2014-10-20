/*
 * Pprun's Public Domain.
 */
package com.letv.common.monitor;

/**
 * The master of the statistics in package {@link org.pprun.common.monitor}.
 * Currently, there's only two methods to return the statistics as either {@literal JSON} or {@literal TEXT}.
 * 
 * <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface Monitor {

    /**
     * return the statistic as {@literal JSON}.
     */
    String asJson() throws Exception;
    
    /**
     * return the statistic as {@literal Plain Text}.
     */
    String asPlainText() throws Exception;
}