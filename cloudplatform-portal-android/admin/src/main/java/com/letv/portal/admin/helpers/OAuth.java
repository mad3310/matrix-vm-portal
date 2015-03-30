package com.letv.portal.admin.helpers;

import android.os.Bundle;
import android.widget.Toast;
import com.letv.portal.admin.R;
import com.letv.portal.admin.activity.Fragment_login;
import com.letv.portal.admin.activity.uFragment;
import com.letv.portal.admin.uApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.util.HashMap;


import static com.letv.portal.admin.utils.HttpSessionClient.post;
import static com.letv.portal.admin.utils.HttpSessionClient.get;
/**
 * Created by Justin on 2015/3/27.
 */
public class OAuth {
    private  static  JSONObject UserInfo = null;
    public static String User_Name=null,User_Passwd=null;
    public static boolean User_ldap=false;

    public static void Auth(final uFragment.OnRestApiListener listener){
        if (null==User_Name || null==User_Passwd){
            //显示登录验证页面
            uFragment fragment=new Fragment_login();
            Bundle args = new Bundle();
            args.putInt(uApplication.mainActivity.ARG_SECTION_NUMBER, (20));
            fragment.setArguments(args);
            uApplication.mainActivity.ShowFragment(fragment);
        }else
            Auth(User_Name, User_Passwd, User_ldap,listener);
        }

     public static void Auth(final String username, final String passwd, final boolean ldap, final uFragment.OnRestApiListener listener){
         uApplication.mainActivity.ShowProcessdlg(  uApplication.context.getResources().getString(R.string.login_status_logging_in));
         login(username, passwd, ldap, new uFragment.OnRestApiListener() {
             @Override
             public void OnRestApi(boolean onSuccess, byte[] bytes) {
                 uApplication.mainActivity.HideProcessdlg();
                 if (onSuccess) {
                     User_Name = username;
                     User_Passwd = passwd;
                     User_ldap = ldap;
                     Toast.makeText(uApplication.context, "登录成功", Toast.LENGTH_LONG).show();
                 } else {
                     User_Passwd = null;
                     Auth(listener);
                     return;
                 }
                 //窗口显示登录结果
                 if (listener != null) listener.OnRestApi(onSuccess, bytes);
             }
         });
     }

//    public static void login(String username,String passwd,boolean ldap){
//        RequestParams Params=new RequestParams();
//                Params.put("username",username);
//        Params.put("password",passwd);
//                if (ldap) Params.put("ldap","true");
//
//         post(uApplication.context.getResources().getString(R.string.oauth_host) + "/login", Params, new AsyncHttpResponseHandler() {
//             @Override
//             public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                 try {
//                     JSONObject result = new JSONObject(new String(responseBody));
//                     //验证
//                     AuthorizeCode(result.getString("client_id"), result.getString("client_secret"));
//                 } catch (JSONException ex) {
//                     // 异常处理代码
//                     Toast.makeText(uApplication.context, "/login 数据异常：" + new String(responseBody), Toast.LENGTH_LONG).show();
//                 }
//             }
//
//             @Override
//             public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                 Toast.makeText(uApplication.context, "/login 登录失败", Toast.LENGTH_LONG).show();
//             }
//         });
//    }

    private static void login(String username,String passwd,boolean ldap, final uFragment.OnRestApiListener listener){

        String url=String.format("%s/nopagelogin?username=%s&password=%s&%s",
                uApplication.context.getResources().getString(R.string.oauth_host),
                username,
                URLEncoder.encode(passwd),
                ldap?"&ldap=true":"" );
         post(url, null, new AsyncHttpResponseHandler() {
             @Override
             public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                 try {
                     JSONObject result = new JSONObject(new String(responseBody));
                     //验证
                     callback_getSessionID(result.getString("client_id"), result.getString("client_secret"),listener);
                 } catch (JSONException ex) {
                     // 异常处理代码
                     Toast.makeText(uApplication.context, "/login 数据异常：" + new String(responseBody), Toast.LENGTH_LONG).show();
                 }
             }

             @Override
             public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                 Toast.makeText(uApplication.context, "/login 登录失败", Toast.LENGTH_LONG).show();
             }
         });
    }

    //获取sessionid
    private static void callback_getSessionID(final String client_id, final String client_secret,final uFragment.OnRestApiListener listener){
        String url=String.format("%s/oauth/callback?client_id=%s&client_secret=%s",
                uApplication.context.getResources().getString(R.string.server_host),
                client_id,
                client_secret);
        get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                AuthorizeCode(client_id, client_secret,listener);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AuthorizeCode(client_id, client_secret,listener);
              //  Toast.makeText(uApplication.context, "/oauth/callback  获取sessionid失败", Toast.LENGTH_LONG).show();
            }
        });
    }

       //获取验证码
    private static void AuthorizeCode(final String client_id, final String client_secret,final uFragment.OnRestApiListener listener){
       // client_id=client_id-huangqiaojia~LDAP-1426491946278&response_type=code&redirect_uri=http://rds2.et.letv.com/oauth/callback
        String url=String.format("%s/authorize?client_id=%s&response_type=code&redirect_uri=%s",
                uApplication.context.getResources().getString(R.string.oauth_host),
                client_id,
                "http://rds2.et.letv.com/oauth/callback");

//        String url=String.format("%s/oauth/callback?client_id=%s&client_secret=%s",
//                uApplication.context.getResources().getString(R.string.server_host),
//                client_id,
//                client_secret);
       // AsyncHttpCilentUtil.getClient().setEnableRedirects(true);
        get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                if (result.indexOf("invalid client_id or client_secret") > 0) {
                    Toast.makeText(uApplication.context, "AuthorizeCode 用户非法", Toast.LENGTH_LONG).show();
                    return;
                }
                if (result.indexOf("client not login") > 0) {
                    Toast.makeText(uApplication.context, "AuthorizeCode 用户未登录", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(uApplication.context, "AuthorizeCode 失败" + statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 302) {
                    for (Header header : headers) {
                        if (header.getName().equals("Location")) {
                            try {
                                URL u = new URL(header.getValue());

                                HashMap<String, String> querymap = new HashMap<String, String>();
                                for (String q : u.getQuery().split("&")) {
                                    if (q.split("=")[0].equals("code")) {
                                       // AccessToken(q.split("=")[1], client_id, client_secret);
                                        callback_code(q.split("=")[1], client_id, client_secret,listener);
                                        return;
                                    }
                                }
                                ;
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }

                }
                Toast.makeText(uApplication.context, "AuthorizeCode 失败" + statusCode, Toast.LENGTH_LONG).show();
            }
        });
    }

    //code验证
    private static void callback_code(final String code,final String client_id,final String client_secret,final uFragment.OnRestApiListener listener){
        String url=String.format("%s/oauth/callback?code=%s",
                uApplication.context.getResources().getString(R.string.server_host),
                code);
        get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                    AccessToken(code, client_id, client_secret,listener);
                  //  Toast.makeText(uApplication.context, "callback_code:"+result, Toast.LENGTH_LONG).show();
                    return;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode==302){
                    AccessToken(code, client_id, client_secret,listener);
                }else
                Toast.makeText(uApplication.context, "callback_code 失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    //获取Token
    private static void AccessToken(String code, String client_id, String client_secret,final uFragment.OnRestApiListener listener){
        String url=String.format("%s/accesstoken?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s",
                uApplication.context.getResources().getString(R.string.oauth_host),
                code,
                client_id,
                client_secret,
                "http://rds2.et.letv.com/oauth/callback");
        get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);

                if (result.indexOf("access_token") == -1) {
                    Toast.makeText(uApplication.context, "AccessToken 用户未登录", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    JSONObject resultcode = new JSONObject(result);
                    //获取资源
                    Userinfo(resultcode.getString("access_token"),listener);
                } catch (JSONException ex) {
                    // 异常处理代码
                    Toast.makeText(uApplication.context, "/AccessToken 数据异常：" + new String(responseBody), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(uApplication.context, "AccessToken 失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private static void Userinfo(String access_token,final uFragment.OnRestApiListener listener){
        String url=String.format("%s/userdetailinfo?access_token=%s",
                uApplication.context.getResources().getString(R.string.oauth_host),
                access_token );
        get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    UserInfo = new JSONObject(new String(responseBody));
                 //   Toast.makeText(uApplication.context, "登录成功", Toast.LENGTH_LONG).show();
                    if (null!=listener){
                        listener.OnRestApi(true,responseBody);
                    }
                    // AsyncHttpCilentUtil.getClient().setEnableRedirects(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(uApplication.context, "Userinfo 数据错误", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(uApplication.context, "登录失败" + statusCode, Toast.LENGTH_LONG).show();
            }
        });
    }
}
