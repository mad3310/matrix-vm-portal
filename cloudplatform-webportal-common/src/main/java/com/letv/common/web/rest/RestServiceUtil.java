/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.web.rest;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.common.util.CommonUtil;
import com.letv.common.util.RSAUtil;


/**
 * A utility class to collect the common util methods for REST service.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public final class RestServiceUtil {

    private static final Logger log = LoggerFactory.getLogger(RestServiceUtil.class);

    /**
     * 加密 src, 使用给定的公钥.
     * 
     * @param src
     * @param publicKeyString
     * @return 
     */
    public static String encryptToken(String src, String publicKeyString) {

        // encrypt the token
        String token = null;
        try {
            PublicKey publicKey = RSAUtil.getPublicKeyFromString(publicKeyString);
            //token = RSAUtil.encrypt(src, publicKey);
            
            String encryptToken = RSAUtil.encrypt(src, publicKey);
            // encode the key as it will be passed in url:
            token = URLEncoder.encode(encryptToken, CommonUtil.UTF8);

            log.debug("encrypt tokekn is: {}", token);
        } catch (Exception e) {
            throw new IllegalStateException("Exception in encryptToken", e);
        }
        return token;
    }

    /**
     * 解密 src, 使用给定的私钥.
     * 
     * @param src
     * @param privateKeyString
     * @return 
     */
    public static String decryptToken(String src, String privateKeyString) {

        // decrypt the token
        String token = null;
        try {
            String encryptToken = URLDecoder.decode(src, CommonUtil.UTF8);
            PrivateKey privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);
            token = RSAUtil.decrypt(encryptToken, privateKey);

            log.debug("decrypted tokekn is: {}", token);
        } catch (Exception e) {
            throw new IllegalStateException("Exception in decryptToken", e);
        }
        return token;
    }
    
    
    /**
     * 解密 src, 使用给定的私钥.
     * 因为Spring mvc3 自动将 url decode, 所以此方法为 Spring mvc 3 特有.
     * 
     * @param src
     * @param privateKeyString
     * @return 
     */
    public static String decryptTokenWithoutUrlDecode(String src, String privateKeyString) {

        // decrypt the token
        String token = null;
        try {
            // spring mvc3 auto decode the uri/url
            //String encryptToken = URLDecoder.decode(src, CommonUtil.UTF8);
            PrivateKey privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);
            token = RSAUtil.decrypt(src, privateKey);

            log.debug("decrypted tokekn is: {}", token);
        } catch (Exception e) {
            throw new IllegalStateException("Exception in decryptToken", e);
        }
        return token;
    }
}
