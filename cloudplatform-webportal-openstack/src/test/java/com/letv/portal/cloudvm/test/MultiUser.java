package com.letv.portal.cloudvm.test;

import com.letv.portal.service.openstack.util.tuple.Tuple2;
import com.letv.portal.service.openstack.util.tuple.Tuple3;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/18.
 */
public class MultiUser {
    public static String[] userEmails = new String[]{
            "zhoubingzheng@letv.com"
            , "liuhao1@letv.com"
            , "lisuxiao@letv.com"
            , "hanxiangxiang@letv.com"
            , "yaokuo@letv.com"
            , "gaomin@letv.com"
            , "jiangfei@letv.com"
            , "sunweiwei1@letv.com"
            , "liuyulong@letv.com"
            , "zhangzhengyao@letv.com"
            , "zhangzeng@letv.com"
            , "howie_liu@126.com"
            , "zhangdongmao@letv.com"
            , "liufy@letv.com"
            , "xuyanwei@letv.com"
            , "jiangfei5945@hotmail.com"
            , "zhouxianguang@letv.com"
    };

    public static String emailToPw(String email) {
        return DigestUtils.sha512Hex((email + "abcdefg12345")
                .getBytes(Charsets.UTF_8));
    }

    public static List<Tuple3<String, String, String>> getUserNameAndPassList() {
        List<Tuple3<String, String, String>> list = new LinkedList<Tuple3<String, String, String>>();
        for (String email : userEmails) {
            String userName = email + ":" + email;
            String pw = emailToPw(email);
            list.add(new Tuple3<String, String, String>(email, userName, pw));
        }
        return list;
    }
}
