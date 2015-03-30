package com.letv.portal.admin.utils;

import android.content.Context;
import android.os.*;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.letv.portal.admin.uApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Justin on 2015/3/24.
 */
public class HttpSessionClient {
    private static HttpParams httpParams;
    private static DefaultHttpClient httpClient = null;
   // private static String JSESSIONID; //定义一个静态的字段，保存sessionID
    private static HashMap  CookieVals = new HashMap(); //定义一个静态的字段，保存sessionID

    public static DefaultHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * @return
     * @throws Exception HttpClient               返回
     * @Title: getHttpClient
     * @Description: 获取HttpClient
     */
    public static HttpClient getInstance(Context context) {
        return getInstance(context, false);
    }

    public static HttpClient getInstance(Context context, boolean EnabledRedirect) {
        if (null == httpClient) {
            // 初始化工作
            SSLSocketFactory sf=null;
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore
                        .getDefaultType());

                trustStore.load(null, null);
                 sf = new SSLSocketFactoryEx(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  //允许所有主机的验证
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            // 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
            httpParams = new BasicHttpParams();

            // 设置连接超时
            HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
            // 设置socket超时
            HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
            HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
            // 设置重定向，缺省为 true
            HttpClientParams.setRedirecting(httpParams, EnabledRedirect);
            // 设置 user agent
            String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
            HttpProtocolParams.setUserAgent(httpParams, userAgent);
            //  HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
            // HttpProtocolParams.setContentCharset(httpParams,HTTP.DEFAULT_CONTENT_CHARSET);
            //  HttpProtocolParams.setUseExpectContinue(httpParams, true);

            // 设置连接管理器的超时
            ConnManagerParams.setTimeout(httpParams, 10000);

            // 设置http https支持
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", sf, 443));

            ClientConnectionManager conManager = new ThreadSafeClientConnManager(
                    httpParams, schReg);

            // 创建一个 HttpClient 实例
            // 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient
            // 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
            httpClient = new DefaultHttpClient(conManager, httpParams);

           //302处理
            httpClient.setRedirectHandler(new RedirectHandler() {
                @Override
                public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
                    saveCookie();
                    return true;
                }


                @Override
                public URI getLocationURI(HttpResponse response, HttpContext context)
                        throws ProtocolException {
                    Header header = response.getFirstHeader("Location");
                    URI uri=null;
                   if (header!=null) {
                       try {
                             uri=new URI(header.getValue());
                       } catch (URISyntaxException e) {
                           e.printStackTrace();
                       }
                   }
                    return uri;
                }
            });
        }
        return httpClient;
    }

   private static String getCookieStr(){
       String result = "";
       Iterator iter = CookieVals.keySet().iterator();
       while (iter.hasNext()) {
           Object key = iter.next();
           Object val = CookieVals.get(key);
           result=result+key+"="+val+";";
       }
       return result;
   }
    public static void get(String url, AsyncHttpResponseHandler res) {
        get(url, null, res);
    }


    public static void get(String url, Map params, AsyncHttpResponseHandler res) {
        AsyncHttpTask_get t = new AsyncHttpTask_get(url, params, res);
        t.execute();
    }

    /**
     * @param url
     * @param params
     * @return
     * @throws Exception String               返回
     * @Title: doPost
     * @Description: doPost请求
     */
    public static void post(String url, List<NameValuePair> params, AsyncHttpResponseHandler res) {
        AsyncHttpTask_post t = new AsyncHttpTask_post(url, params, res);
        t.execute();
    }

    public static class AsyncHttpTask_get extends AsyncTask<Integer, Integer, String> {
        private AsyncHttpResponseHandler res;
        private String url;
        private Map params;
        private HttpResponse httpResponse = null;

        public AsyncHttpTask_get(String url, Map params, AsyncHttpResponseHandler res) {
            super();
            this.res = res;
            this.url = url;
            this.params = params;
        }

        //异步操作
        @Override
        protected String doInBackground(Integer... integers) {
             /* 建立HTTPGet对象 */
            String paramStr = "";
            if (params != null) {
                Iterator iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    paramStr += paramStr = "&" + key + "=" + val;
                }
            }
            if (!paramStr.equals("")) {
                paramStr = paramStr.replaceFirst("&", "?");
                url = url + paramStr;
            }
            HttpGet httpRequest = new HttpGet(url);
            if (CookieVals.size()>0){
                httpRequest.setHeader("Cookie", getCookieStr());
            }
//            if (null != JSESSIONID) {
//                httpRequest.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
//            }

        /* 发送请求并等待响应 */

            try {
                httpResponse = httpClient.execute(httpRequest);
                return null;
                // publishProgress(0);
            } catch (IOException e) {
                e.printStackTrace();
                if (null != res) {
                    Throwable error = new Throwable(e);
                    res.onFailure(0, null, null, error);
                }
            }

            return null;
        }

        //异步操作执行结束
        @Override
        protected void onPostExecute(String result) {
            if (null == res) return;
            if (null != httpResponse && result == null) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                byte[] data = null;
                try {
                    data = EntityUtils.toByteArray(httpResponse.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveCookie();
                if (statusCode == 200 || statusCode == 201) {
                    res.onSuccess(statusCode, httpResponse.getAllHeaders(), data);
                } else {
                    Throwable error = new Throwable("");
                    res.onFailure(statusCode, httpResponse.getAllHeaders(), data, error);
                }
                return;

            }

            Throwable error = new Throwable(result);
            res.onFailure(0, null, null, error);
        }

        //在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
        @Override
        protected void onProgressUpdate(Integer... values) {
            // int vlaue = values[0];
        }
    }

    public static class AsyncHttpTask_post extends AsyncTask<Integer, Integer, String> {
        private AsyncHttpResponseHandler res;
        private String url;
        private List<NameValuePair> params;
        private HttpResponse httpResponse = null;

        public AsyncHttpTask_post(String url, List<NameValuePair> params, AsyncHttpResponseHandler res) {
            super();
            this.res = res;
            this.url = url;
            this.params = params;
        }

        //异步操作
        @Override
        protected String doInBackground(Integer... integers) {
              /* 建立HTTPPost对象 */
            HttpPost httpRequest = new HttpPost(url);
        /* 添加请求参数到请求对象 */
            if (params != null && params.size() > 0) {
                try {
                    httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
//            if (null != JSESSIONID) {
//                httpRequest.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
//            }


        /* 发送请求并等待响应 */
            try {
                httpResponse = httpClient.execute(httpRequest);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                if (null != res) {
                    Throwable error = new Throwable(e);
                    res.onFailure(0, null, null, error);
                }
            }


            return null;
        }

        //异步操作执行结束
        @Override
        protected void onPostExecute(String result) {
            if (null == res) return;
            if (null != httpResponse && result == null) {
                byte[] data = null;
                try {
                    data = EntityUtils.toByteArray(httpResponse.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200 || statusCode == 201) {
                 /* 获取cookieStore */
                    saveCookie();
                    res.onSuccess(statusCode, httpResponse.getAllHeaders(), data);
                } else {
                    Throwable error = new Throwable("");
                    res.onFailure(statusCode, httpResponse.getAllHeaders(), data, error);
                }
                return;
            }

            Throwable error = new Throwable(result);
            res.onFailure(0, null, null, error);
        }

        //在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
        @Override
        protected void onProgressUpdate(Integer... values) {
            // int vlaue = values[0];
        }
    }

    public static void saveCookie() {
         /* 获取cookieStore */
        CookieStore cookieStore = httpClient.getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
//            if ("JSESSIONID".equals(cookies.get(i).getName())) {
//                JSESSIONID = cookies.get(i).getValue();
//                break;
//            }
            CookieVals.put(cookies.get(i).getName(), cookies.get(i).getValue());
        }
    }
}
