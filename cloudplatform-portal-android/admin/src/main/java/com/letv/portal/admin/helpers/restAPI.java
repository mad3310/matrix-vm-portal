package com.letv.portal.admin.helpers;

import android.widget.Toast;
import com.letv.portal.admin.activity.uFragment;
import com.letv.portal.admin.uApplication;
import android.support.v7.appcompat.R;
import com.letv.portal.admin.utils.HttpSessionClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

/**
 * Created by Justin on 2015/3/29.
 */
public class restAPI {
    public enum ArgTag {
        STATISTICS,MONITOR;

        public static ArgTag getArgTag(String argTag){
            return valueOf(argTag.toUpperCase());
        }
    }

    public static void dashboard(final uFragment.OnRestApiListener listener, final String... arg){
        String url= uApplication.context.getResources().getString(com.letv.portal.admin.R.string.server_host)+"/dashboard";
        switch (ArgTag.getArgTag(arg[0])) {
            case STATISTICS:  //资源概览
                url=url+"/statistics";
                   break;
            case MONITOR:  //RDS监控状态图表1-3
                url=url+"/monitor/"+arg[1];
                break;
            default:
                   break;
        }
        uApplication.mainActivity.ShowProcessdlg(null);
                HttpSessionClient.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        uApplication.mainActivity.HideProcessdlg();
                        listener.OnRestApi(true, bytes);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        uApplication.mainActivity.HideProcessdlg();
                        if (i == 302) {
                            //登录验证
                            OAuth.Auth(new uFragment.OnRestApiListener() {
                                @Override
                                public void OnRestApi(boolean onSuccess, byte[] bytes) {
                                    if (onSuccess) {
                                        dashboard(listener, arg);
                                        return;
                                    }
                                }
                            });
                        }
                    }
                });
    }

   //
}
