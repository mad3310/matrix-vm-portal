package com.letv.portal.service.image.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.image.IImageDao;
import com.letv.portal.model.image.Image;
import com.letv.portal.service.image.IImageService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("imageService")
public class ImageServiceImpl extends BaseServiceImpl<Image> implements IImageService{
	
	private final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Resource
	private IImageDao imageDao;
	
	public ImageServiceImpl() {
		super(Image.class);
	}

	@Override
	public IBaseDao<Image> getDao() {
		return this.imageDao;
	}
	
	@Override
	public void insert(Image t) {
		if(t == null) {
			throw new ValidateException("参数不合法");
		}
		super.insert(t);
	}

}
