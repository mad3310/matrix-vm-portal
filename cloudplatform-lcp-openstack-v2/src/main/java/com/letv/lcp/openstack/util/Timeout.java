package com.letv.lcp.openstack.util;

import com.google.common.base.Objects;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhouxianguang on 2015/11/3.
 */
public class Timeout {
    private Long time;
    private TimeUnit unit;

    public Timeout() {
    }

    public Long time() {
        return this.time;
    }

    public TimeUnit unit() {
        return this.unit;
    }

    public Timeout time(final Long time) {
        this.time = time;
        return this;
    }

    public Timeout unit(final TimeUnit unit) {
        this.unit = unit;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeout timeout = (Timeout) o;
        return Objects.equal(time, timeout.time) &&
                Objects.equal(unit, timeout.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time, unit);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("time", time)
                .add("unit", unit)
                .toString();
    }
}
