package com.letv.portal.admin.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.portal.admin.R;
import com.letv.portal.admin.uApplication;

/**
 * Created by Justin on 2015/3/24.
 */
public  class Fragment_database extends uFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uApplication.lastFragment=this;
        View rootView = inflater.inflate(R.layout.fragment_database, container, false);
        return rootView;
    }


}
