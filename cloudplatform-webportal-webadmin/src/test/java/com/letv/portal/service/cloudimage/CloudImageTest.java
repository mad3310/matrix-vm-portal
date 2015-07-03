package com.letv.portal.service.cloudimage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudimage.ImageController;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.dictionary.Dictionary;
import com.letv.portal.model.image.Image;
 
public class CloudImageTest extends AbstractTest{

	@Autowired
	private ImageController imageController;
    
    /**
      * @Title: testImageList
      * @Description: 测试镜像列表接口  
      * @throws 
      * @author lisuxiao
      * @date 2015年7月3日 下午2:30:07
      */
    @Test
    @Ignore
    public void testImageList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = imageController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(1, ((Page)ro.getData()).getTotalRecords());
    }
    
    /**
      * @Title: testSaveImage
      * @Description: 测试镜像新增接口  
      * @throws 
      * @author lisuxiao
      * @date 2015年7月3日 下午2:29:31
      */
    @Test
    @Ignore
    public void testSaveImage() {
    	Image image = new Image();
    	image.setDictionaryId(1l);
    	image.setPurpose("nginx");
    	image.setUrl("1111:5000/letv/base-nginx:logstash-forwarder-0.0.6");
    	image.setTag("1111");
    	image.setIsUsed(1);
    	image.setDescn("etst1111111");
    	
    	imageController.saveImage(image);
    }
    
    /**
      * @Title: testUpdateImage
      * @Description: 测试镜像模块详情展现和更新接口  
      * @throws 
      * @author lisuxiao
      * @date 2015年7月3日 下午2:29:06
      */
    @Test
    //@Ignore
    public void testUpdateImage() {
    	Image image = (Image) imageController.detailById(5l).getData();
    	image.setIsUsed(1);
    	imageController.updateImage(image);
    }
    
    /**
      * @Title: testDeleteImage
      * @Description:   测试镜像删除接口 
      * @throws 
      * @author lisuxiao
      * @date 2015年7月3日 下午2:28:32
      */
    @Test
    @Ignore
    public void testDeleteImage() {
    	imageController.deleteImageById(1l);
    }
    
}
