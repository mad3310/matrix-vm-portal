package com.letv.portal.service.dictionary;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.dictionary.DictionaryController;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.dictionary.Dictionary;
 
public class DictionaryTest extends AbstractTest{

	@Autowired
	private DictionaryController dictionaryController;
    
    /**
      * @Title: testDictionaryList
      * @Description: 测试字典列表接口  
      * @throws 
      * @author lisuxiao
      * @date 2015年7月1日 下午2:30:07
      */
    @Test
    @Ignore
    public void testDictionaryList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = dictionaryController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(1, ((Page)ro.getData()).getTotalRecords());
    }
    
    /**
      * @Title: testSaveDictionary
      * @Description: 测试字典新增接口  
      * @throws 
      * @author lisuxiao
      * @date 2015年7月1日 下午2:29:31
      */
    @Test
    @Ignore
    public void testSaveDictionary() {
    	Dictionary dictionary = new Dictionary();
    	dictionary.setName("GCE");
    	dictionary.setType(0);
    	dictionary.setDescn("测试1");
    	dictionaryController.saveDictionary(dictionary);
    }
    
    /**
      * @Title: testUpdateDictionary
      * @Description: 测试字典模块详情展现和更新接口  
      * @throws 
      * @author lisuxiao
      * @date 2015年7月1日 下午2:29:06
      */
    @Test
    @Ignore
    public void testUpdateDictionary() {
    	Dictionary dictionary = (Dictionary) dictionaryController.detailById(1l).getData();
    	dictionary.setName("OSS");
    	dictionaryController.updateDictionary(dictionary);
    }
    
    /**
      * @Title: testDeleteDictionary
      * @Description:   测试字典删除接口 
      * @throws 
      * @author lisuxiao
      * @date 2015年7月1日 下午2:28:32
      */
    @Test
    public void testDeleteDictionary() {
    	dictionaryController.deleteDictionaryById(1l);
    }
    
}
