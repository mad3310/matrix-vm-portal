package com.letv.portal.dao.letvcloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.letv.portal.model.letvcloud.BillDeduction;

public interface BillTaskControlMapper {
    /**
     * 根据状态和计费周期查询扣费任务
     * @return
     */
    List<BillDeduction> getBillTaskByStateAndPeriod(@Param(value = "state")String state,@Param(value = "period")String period);


}