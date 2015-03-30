package com.letv.portal.admin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.portal.admin.R;
import com.letv.portal.admin.uApplication;

/**
 * Created by Justin on 2015/3/25.
 * 自定义Fragment基类
 */
public class uFragment extends Fragment {
private OnRestApiListener listener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
    }

    public interface OnRestApiListener {
         public void OnRestApi(boolean onSuccess, byte[] bytes);
    }
    public void setOnRestApiListener(OnRestApiListener listener) {
         this.listener = listener;
         }
}
