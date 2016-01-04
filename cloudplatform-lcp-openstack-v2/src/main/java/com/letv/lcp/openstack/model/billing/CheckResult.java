package com.letv.lcp.openstack.model.billing;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public class CheckResult {

    private boolean success;

    private String failureReason;

    public CheckResult() {
        this.success = true;
        this.failureReason = null;
    }

    public CheckResult(String failureReason) {
        this.success = false;
        this.failureReason = failureReason;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
