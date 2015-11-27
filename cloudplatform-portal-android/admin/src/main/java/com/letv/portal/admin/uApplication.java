package com.letv.portal.admin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.letv.portal.admin.activity.MainActivity;
import com.letv.portal.admin.activity.uFragment;
import com.letv.portal.admin.helpers.OAuth;
import com.letv.portal.admin.utils.AsyncHttpCilentUtil;
import com.letv.portal.admin.utils.HttpSessionClient;
import android.os.Handler;
import com.letv.portal.admin.utils.SimpleCrypto;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Justin on 2015/3/22.
 */

public class uApplication extends Application {
    public static Context context;
    public static final String appTag = "AppPortal.admin";
    public static SharedPreferences AppOptions;
    public static MainActivity mainActivity=null;
    public static uFragment lastFragment=null;
    public static Handler mainHandler;
    public static String loginUrl=null;

//    public static AsyncHttpClient httpClient = new AsyncHttpClient();

    private String value;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化全局变量
        this.context=getApplicationContext();
        AppOptions = getSharedPreferences("options", Context.MODE_PRIVATE);

        //自动登录时，进行参数读取
        if (AppOptions.contains("uautologin") && AppOptions.getBoolean("uautologin",false)){
            try {
                OAuth.User_Name= SimpleCrypto.decrypt("111", AppOptions.getString("uname", ""));
                OAuth.User_Passwd= SimpleCrypto.decrypt("112", AppOptions.getString("upwd", ""));
                OAuth.User_ldap= AppOptions.getBoolean("uldap",false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        try {
//            //初始化http连接对象(非自动跳转模式)
//            HttpSessionClient.getHttpClient(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            HttpSessionClient.getInstance(this.context);

    }



}