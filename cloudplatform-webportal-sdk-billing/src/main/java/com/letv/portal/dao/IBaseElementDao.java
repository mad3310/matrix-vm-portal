package com.letv.portal.dao;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.BaseElement;

public interface IBaseElementDao extends IBaseDao<BaseElement> {

	List<BaseElement> selectByName(String name);

}
