package com.letv.portal.dao.base;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.base.BasePrice;

public interface IBasePriceDao extends IBaseDao<BasePrice> {
	List<Integer> selectChargeTypeByStandard(String standard);
}
