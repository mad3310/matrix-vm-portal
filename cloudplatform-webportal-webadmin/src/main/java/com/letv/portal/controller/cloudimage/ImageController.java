package com.letv.portal.controller.cloudimage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.image.Image;
import com.letv.portal.service.image.IImageService;


@Controller
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	private IImageService imageService;
	
	private final static Logger logger = LoggerFactory.getLogger(ImageController.class);

	/**
	  * @Title: list
	  * @Description: 获取指定页数指定条数的Image列表
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午1:47:31
	  */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.imageService.selectPageByParams(page, params));
		return obj;
	}
	
	/**
	  * @Title: saveDictionary
	  * @Description: 保存Image信息
	  * @param dictionary
	  * @param request
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午5:48:00
	  */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResultObject saveImage(Image image) {
		ResultObject obj = new ResultObject();
		this.imageService.insert(image);
		return obj;
	}
	
	/**
	  * @Title: deleteDbUserById
	  * @Description: 根据主键id删除Image信息
	  * @param dictionaryId 主键id
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午5:55:50
	  */
	@RequestMapping(value="/{imageId}",method=RequestMethod.DELETE)
	public  @ResponseBody ResultObject deleteImageById(@PathVariable Long imageId) {
		Image image = new Image();
		image.setId(imageId);
		this.imageService.delete(image);
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**
	  * @Title: updateDbUser
	  * @Description: 修改Image信息
	  * @param dictionary
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午5:58:17
	  */
	@RequestMapping(value="/{imageId}", method=RequestMethod.POST)
	public @ResponseBody ResultObject updateImage(Image image) {
		this.imageService.update(image);		
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**
	  * @Title: detail
	  * @Description: 根据id返回详情
	  * @param id
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午6:47:24
	  */
	@RequestMapping(value ="/{imageId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject detailById(@PathVariable Long imageId){
		ResultObject obj = new ResultObject();	
		obj.setData(this.imageService.selectById(imageId));
		return obj;
	}
	
}
