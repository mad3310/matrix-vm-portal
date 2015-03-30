package com.letv.portal.admin.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.letv.portal.admin.R;
import com.letv.portal.admin.helpers.restAPI;
import com.letv.portal.admin.uApplication;
import com.letv.portal.admin.utils.AsyncHttpCilentUtil;
import com.letv.portal.admin.utils.HttpSessionClient;
import android.os.Message;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Random;

/**
 * Created by Justin on 2015/3/24.
 */
public  class Fragment_statistics extends uFragment {
    private ViewPager mViewPager;
    private android.support.v7.app.ActionBar mActionBar;
    private MyPagerAdapter mPagerAdapter;
    private String[] addresses = { "first", "second", "third" };
    private  android.support.v7.app.ActionBar.Tab[] mTabs = new android.support.v7.app.ActionBar.Tab[addresses.length];
        private TextView statistics_label=null;
   // private TabFragmentPagerAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        uApplication.lastFragment=this;
          statistics_label = (TextView) rootView.findViewById(R.id.statistics_label);
        statistics_label.setText("*8888888kkk");
        initFragment(  rootView);
        restAPI.dashboard(new OnRestApiListener() {
            @Override
            public void OnRestApi(boolean onSuccess, byte[] bytes) {
                statistics_label.setText(new String(bytes));
            }
        }, "statistics");

        return rootView;
    }

    private void initFragment(View rootView) {
        //初始化 ActionBar
//        ActionBar  mActionBar=uApplication.mainActivity.getActionBar();
//        mActionBar.removeAllTabs();
//        ActionBar.Tab tab = mActionBar.newTab();
//
//        tab.setText(mAdapter.getPageTitle(i)).setTabListener(this);
//        mActionBar.addTab(tab);
//        for(int i=0;i<MAX_TAB_SIZE;i++){
//            Tab tab = mActionBar.newTab();
//            tab.setText(mAdapter.getPageTitle(i)).setTabListener(this);
//            mActionBar.addTab(tab);
//        }

        mViewPager = (ViewPager) rootView.findViewById(R.id.statistics_pager);
        mPagerAdapter = new MyPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(mPageChangeListener);
        mActionBar = uApplication.mainActivity.getSupportActionBar();
        mActionBar.removeAllTabs();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        for (int i = 0; i != addresses.length; i++)
        {
            mTabs[i] = mActionBar.newTab().setText(addresses[i]).setTabListener(mTabListener);
            mActionBar.addTab(mTabs[i]);
        }
        mActionBar.show();
//        AFragment aFragment = new AFragment();
//        actionBar.addTab(actionBar.newTab().setText("Tab-A")
//                .setTabListener(new ListenerA(aFragment)));
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0)
        {
            mActionBar.setSelectedNavigationItem(arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }
    };
    private android.support.v7.app.ActionBar.TabListener mTabListener = new  android.support.v7.app.ActionBar.TabListener() {
        @Override
        public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
            if (tab == mTabs[0])
            {
                mViewPager.setCurrentItem(0);
            } else if (tab == mTabs[1])
            {
                mViewPager.setCurrentItem(1);
            } else if (tab == mTabs[2])
            {
                mViewPager.setCurrentItem(2);
            }
        }

        @Override
        public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

        }

    };
    public class MyPagerAdapter extends FragmentPagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            return MyFragment.create(addresses[position]);
        }
        @Override
        public int getCount()
        {
            return addresses.length;
        }
    }
    public static class MyFragment extends Fragment
    {
        public static MyFragment create(String address)
        {
            MyFragment f = new MyFragment();
            Bundle b = new Bundle();
            b.putString("address", address);
            f.setArguments(b);
            return f;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            Random r = new Random(System.currentTimeMillis());
            Bundle b = getArguments();
            View v = inflater.inflate(R.layout.fragment_viewpager1_layout1, null);
            v.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
            TextView txvAddress = (TextView) v.findViewById(R.id.textView1);
            txvAddress.setTextColor(r.nextInt() >> 8 | 0xFF << 24);
            txvAddress.setBackgroundColor(r.nextInt() >> 8 | 0xFF << 24);
            txvAddress.setText("sddddddddd");
            return v;
        }
    }
}
