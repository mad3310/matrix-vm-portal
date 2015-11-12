package com.letv.portal.controller.user;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.common.ICommonQuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhouxianguang on 2015/11/10.
 */
@Controller
@RequestMapping("/quota")
public class QuotaController {
    @Autowired
    private ICommonQuotaService commonQuotaService;
    @Autowired
    private SessionServiceImpl sessionService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResultObject getQuota(@RequestParam String region, ResultObject obj) throws Exception {
        long userId = sessionService.getSession().getUserId();
        obj.setData(commonQuotaService.insertDefaultAndSelectMapByRegion(userId, region));
        return obj;
    }
}
