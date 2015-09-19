package com.letv.portal.dao.letvcloud;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.letvcloud.BillRechargeRecord;

/**
 * Created by wanglei14 on 2015/6/29.
 */
public interface BillRechargeRecordMapper {
    int insert(BillRechargeRecord record);
    int updateSuc(Map<String,Object> params);
    BillRechargeRecord getAmount(String tradeNum);
    List<BillRechargeRecord> getUserAmountRecord(Map<String,Object> params);
    int getAddRecordCnt(Long userId);
}
