package com.letv.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IBaseElementDao;
import com.letv.portal.model.BaseElement;
import com.letv.portal.service.IBaseElementService;

@Service("baseElementService")
public class BaseElementServiceImpl extends BaseServiceImpl<BaseElement> implements IBaseElementService {
	
	public BaseElementServiceImpl() {
		super(BaseElement.class);
	}

	@Autowired
	private IBaseElementDao baseElementDao;

	@Override
	public IBaseDao<BaseElement> getDao() {
		return this.baseElementDao;
	}

}
