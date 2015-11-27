package com.letv.portal.service.openstack.util;

import com.letv.portal.service.openstack.util.constants.Constants;

import java.text.MessageFormat;

/**
 * Created by zhouxianguang on 2015/11/27.
 */
public class NameUtil {
    public static String nameAddNumber(String name, int number) {
        String resultName = MessageFormat.format("{0}({1})", name, number);
        int needlessCharAmount = resultName.length() - Constants.nameMaxLength;
        if (needlessCharAmount > 0) {
            name = name.substring(0, name.length() - needlessCharAmount);
            resultName = MessageFormat.format("{0}({1})", name, number);
        }
        return resultName;
    }
}
