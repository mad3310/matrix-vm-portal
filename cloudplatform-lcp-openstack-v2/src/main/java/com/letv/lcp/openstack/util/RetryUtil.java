package com.letv.lcp.openstack.util;

import com.letv.lcp.openstack.model.erroremail.ErrorMailMessageModel;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.util.function.Function0;


/**
 * Created by zhouxianguang on 2015/11/20.
 */
public class RetryUtil {

    public static void retry(Function0<Boolean> process, int times, String errorMessage) {
        for (int i = 0; i < times; i++) {
            try {
                boolean isFinished = process.apply();
                if (isFinished) {
                    return;
                }
            } catch (Exception e) {
                ExceptionUtil.logAndEmail(e);
            }
        }
        OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService()
                .sendErrorEmail(new ErrorMailMessageModel().exceptionMessage(errorMessage));
    }
}
