package com.letv.portal.controller.cloudvm;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.util.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.exception.OpenStackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/osi")
public class ImageController {

    @Autowired
    private SessionServiceImpl sessionService;

    @RequestMapping(value="/regions",method = RequestMethod.GET)
    public
    @ResponseBody
    ResultObject regions() {
        ResultObject result = new ResultObject();
        try {
        result.setData(Util.session(sessionService).getImageManager().getRegions().toArray(new String[0]));
        } catch (OpenStackException e) {
			throw e.matrixException();
		}
        return result;
    }

    @RequestMapping(value = "/region/{region}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultObject list(@PathVariable String region) {
        ResultObject result = new ResultObject();
        try {
            result.setData(Util.session(sessionService).getImageManager().list(region));
        } catch (OpenStackException e) {
        	throw e.matrixException();
        }
        return result;
    }
    
    @RequestMapping(value = "/region/{region}/group", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultObject group(@PathVariable String region) {
        ResultObject result = new ResultObject();
        try {
            result.setData(Util.session(sessionService).getImageManager().group(region));
        } catch (OpenStackException e) {
        	throw e.matrixException();
        }
        return result;
    }

    @RequestMapping(value = "/region/{region}/image/{imageId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultObject get(@PathVariable String region, @PathVariable String imageId) {
        ResultObject result = new ResultObject();
        try {
            result.setData(Util.session(sessionService).getImageManager().get(region, imageId));
        } catch (OpenStackException e) {
        	throw e.matrixException();
        }
        return result;
    }

    @RequestMapping(value = "/image/list", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultObject listImage(@RequestParam String region, @RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer currentPage,
                                @RequestParam(required = false) Integer recordsPerPage) {
        Params obj = new Params().p("id", "fake-id").p("name","fake image").p("region","cn-beijing-1").p("createdAt",11111111).p("size",1).p("status","ACTIVE").p("minRam",1).p("minDisk",1);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(obj);
        Page page;
        if (currentPage == null || recordsPerPage == null) {
            page = new Page();
        } else {
            page = new Page(currentPage, recordsPerPage);
        }
        page.setData(list);
        ResultObject result = new ResultObject();
        result.setData(page);
        return result;
    }
}
