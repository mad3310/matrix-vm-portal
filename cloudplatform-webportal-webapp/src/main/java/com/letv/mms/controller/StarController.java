package com.letv.mms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.util.PagingUtil;
import com.letv.mms.model.StarModel;
import com.letv.mms.service.IStarService;

/**
 * 
 * 
 * @author liunk
 */
@Controller
@RequestMapping("/star/*")
public class StarController extends BaseContorller {
    @Resource
    private IStarService starService;


    @RequestMapping
    public String list(HttpServletRequest req, Integer page, ModelMap modelMap) {
    	StarModel model = new StarModel();
    	int recordCount = starService.selectByModelCount(model);
    	int[] recordRange = PagingUtil.addPagingSupport(20, recordCount, page, 10, modelMap);
    	List<StarModel> starModelList = starService.selectByModel(model);
    	modelMap.put("starModelList", starModelList);
        return "/star/list";
    }
    
    @RequestMapping
    public String edit(HttpServletRequest req, Long id, ModelMap modelMap) {
    	StarModel starModel = starService.selectById(id);
    	modelMap.put("star", starModel);
    	return "/star/edit";
    }
    
    
    @RequestMapping
    public String editSubmit(HttpServletRequest req, StarModel star, ModelMap modelMap) {
    	starService.updateBySelective(star);
    	return "redirect:list.do";
    }
    
    

}
