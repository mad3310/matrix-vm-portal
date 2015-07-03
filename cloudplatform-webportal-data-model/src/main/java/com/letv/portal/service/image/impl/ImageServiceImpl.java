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
		if(t.getIsUsed()==1) {
			updateOthersIsUsed(t);
		}
		super.insert(t);
	}
	
	@Override
	public void update(Image t) {
		if(t!=null && t.getIsUsed()==1) {
			updateOthersIsUsed(t);
		}
		super.update(t);
	}
	
	/**
	  * @Title: updateOthersIsUsed
	  * @Description: 根据dictionaryId和purpose更新现在使用中的数据为：0-未使用 
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月3日 下午6:15:11
	  */
	private void updateOthersIsUsed(Image image) {
		imageDao.updateOthersIsUsed(image);
	}

}
