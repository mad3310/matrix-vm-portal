/*
 * Pprun's Public Domain.
 */
package com.letv.common.monitor;

import java.util.Set;

/**
 * The master of the statistics in package {@link org.pprun.common.monitor}.
 * Currently, there's only two methods to return the statistics as either {@literal JSON} or {@literal TEXT}.
 * 
 * <a href="mailto:quest.run@gmail.com">pprun</a>
 */
//@Component
public class MonitorImpl implements Monitor {

    public MonitorImpl() {
    }
    private Set<Statistic> statistics;

    /**
     * return the statistic as {@literal JSON}.
     */
    public String asJson() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Statistic s : statistics) {
            sb.append(s.asJson());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * return the statistic as {@literal Plain Text}.
     */
    public String asPlainText() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Statistic s : statistics) {
            sb.append(s.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
    
    /**
     * @return the statistics
     */
    public Set<Statistic> getStatistics() {
        return statistics;
    }

    /**
     * @param statistics the statistics to set
     */
    public void setStatistics(Set<Statistic> statistics) {
        this.statistics = statistics;
    }

}
