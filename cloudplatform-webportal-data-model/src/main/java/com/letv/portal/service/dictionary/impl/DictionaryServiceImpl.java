package com.letv.portal.service.dictionary.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.dictionary.IDictionaryDao;
import com.letv.portal.model.dictionary.Dictionary;
import com.letv.portal.service.dictionary.IDictionaryService;
import com.letv.portal.service.common.impl.BaseServiceImpl;

@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements IDictionaryService{
	
	private final static Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);
	
	@Resource
	private IDictionaryDao dictionaryDao;
	
	public DictionaryServiceImpl() {
		super(Dictionary.class);
	}

	@Override
	public IBaseDao<Dictionary> getDao() {
		return this.dictionaryDao;
	}
	
	@Override
	public void insert(Dictionary t) {
		if(t == null) {
			throw new ValidateException("参数不合法");
		}
		super.insert(t);
	}

}
