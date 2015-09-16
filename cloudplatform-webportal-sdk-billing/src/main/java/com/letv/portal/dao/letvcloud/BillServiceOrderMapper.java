package com.letv.portal.dao.letvcloud;

import java.util.List;
import java.util.Map;

import com.letv.portal.letvcloud.bill.vo.FeatherCondition;
import com.letv.portal.model.letvcloud.BillServiceOrder;

/**
 * Created by wanglei14 on 2015/6/18.
 */

public interface BillServiceOrderMapper {
    Long insert(BillServiceOrder serviceOrder);

    Long update(BillServiceOrder serviceOrder);

    Long getOrderId(FeatherCondition featherCondition);

    BillServiceOrder getServiceOrder(BillServiceOrder order);

    List<Long> getUserByServiceCode(String serviceCode);

    Long isOrderExist(Map<String,Object> params);

    Map<String, Object> getState(Map<String, Object> params);
    List<String> getUserService(long userId);
}
