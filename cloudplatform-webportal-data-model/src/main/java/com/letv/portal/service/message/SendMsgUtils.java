package com.letv.portal.service.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendMsgUtils {

    private static final Logger logger = LoggerFactory.getLogger(SendMsgUtils.class);


    @Value("${sms.url}")
    private  String url;
    @Value("${sms.corpId}")
    private  String corpID;
    @Value("${sms.srcAddr}")
    private  String srcAddr;
    @Value("${sms.china.corpId}")
    private String chinaCorpId;
    @Value("${sms.china.srcAddr}")
    private String chinaSrcAddr;


    public  void sendMessage(String mobile, String message) {
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(1000);



            String allUrl = getSendSmsUrl(mobile, message);

            GetMethod getMethod = new GetMethod(allUrl);

//            PostMethod postMethod = new PostMethod(url);
//
//            postMethod.addParameter("corpID",corpID);
//
//            postMethod.addParameter("srcAddr",srcAddr);
//
//            postMethod.addParameter("destAddr",mobile);
//
//            postMethod.addParameter("msg",msg);

            if(httpClient.executeMethod(getMethod) != 200)
                logger.error("send msg failure !mobile:{},message:{}",mobile,message);

          BufferedReader br = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(),getMethod.getResponseCharSet())) ;
            StringBuilder sb = new StringBuilder();
            String temp = null;
            while ((temp = br.readLine())!= null){
                sb.append(temp);
            }
            logger.info("send msg success !mobile:{},message:{},result:{}",mobile,message,sb.toString());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getSendSmsUrl(String mobile, String message) throws UnsupportedEncodingException {
        String sendMessage;
        String sendMobile;
        String sendCorpId;
        String sendSrcAddr;
        if(mobile.startsWith("86")) {
            sendMessage = URLEncoder.encode(message, "GBK");
            sendMobile= mobile.substring(2);
            sendCorpId = chinaCorpId;
            sendSrcAddr = chinaSrcAddr;

        }else{
            sendMessage = URLEncoder.encode(message, "GBK");
            sendMobile= mobile;
            sendCorpId = corpID;
            sendSrcAddr = srcAddr;

        }

        String allUrl = url + "?destAddr=" +sendMobile + "&msg=" + sendMessage + "&srcAddr=" + sendSrcAddr + "&corpID=" + sendCorpId;
        logger.info("sendmsg get Url:{}",allUrl);
        return allUrl;
    }
    
    public static void main(String[] args) {
    	SendMsgUtils sm = new SendMsgUtils();
    	sm.sendMessage("18510086398", "test");
	}
}
