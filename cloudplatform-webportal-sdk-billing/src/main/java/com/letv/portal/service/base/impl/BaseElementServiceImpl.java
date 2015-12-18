package com.letv.portal.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.base.IBaseElementDao;
import com.letv.portal.model.base.BaseElement;
import com.letv.portal.service.base.IBaseElementService;
import com.letv.portal.service.common.impl.BaseServiceImpl;

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

	@Override
	public boolean isUnique(String name) {
		List<BaseElement> elements = this.baseElementDao.selectByName(name);
		return elements == null || elements.isEmpty();
	}

}
