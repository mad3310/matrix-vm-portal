package com.letv.portal.service.openstack.util;

import com.letv.portal.service.openstack.erroremail.impl.ErrorMailMessageModel;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.util.function.Function0;

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
