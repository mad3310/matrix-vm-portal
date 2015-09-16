package com.letv.portal.dao.letvcloud;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.letv.portal.model.letvcloud.BillFeather;
import com.letv.portal.model.letvcloud.BillServiceOrderDetail;

/**
 * Created by wanglei14 on 2015/6/18.
 */
public interface BillServiceOrderDetailMapper {
    Long insert(BillServiceOrderDetail detail);

    Long update(BillServiceOrderDetail detail);

    List<BillFeather> getDetailsByOrderId(long orderId);

    BillFeather getFeather(@Param(value="orderId")long orderId,@Param(value="featherCode")String featherCode);

    Map<String, Object> getMainFeather(@Param(value = "orderId") long orderId, @Param(value = "featherType") String featherType);
}
