package com.letv.lcp.openstack.util;

import java.util.Random;
import java.util.UUID;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public class RandomUtil {
    public static String generateRandomSessionId(){
        return UUID.randomUUID().toString();
    }

    public static String generateRandomPassword(int length) {
        final String charactors = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int count = 0; count < length; count++) {
            int index = random.nextInt(charactors.length());
            stringBuilder.append(charactors.charAt(index));
        }
        return stringBuilder.toString();
    }
}
