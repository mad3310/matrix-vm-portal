package com.letv.portal.letvcloud.bill.utils;

/**
 * Created by chenliusong on 2015/6/23.
 */
public final class BillConstants {

    /**
     * 计费周期 按小时计费
     */
    public static final String BILL_PERIOD_HOUR = "0";

    /**
     * 计费周期 按日计费
     */
    public static final String BILL_PERIOD_DAY = "1";

    /**
     * 计费周期 按月计费
     */
    public static final String BILL_PERIOD_MONTH = "2";

    /**
     * 业务 停用
     */
    public static final String BILL_SERVICE_STATE_DISABLE = "0";

    /**
     * 业务 启用
     */
    public static final String BILL_SERVICE_STATE_ENABLE = "1";
    /**
     * Task redis队列名称
     */
    public static final String BILL_COUNT_TASK = "bill_deduction_task";
    /**
     * 扣费记录自增队列
     */
    public static final String BILL_RECHARGE_SEQ="bill_recharge_seq";

    /**
     * 扣费任务创建状态
     */
    public static final String BILL_TASK_CREATE = "0";

    /**
     * 功能点基本计算值（对应损耗值）
     */
    public static final double BILL_BASE_CALCULATE_VALUE = 1d;
}
