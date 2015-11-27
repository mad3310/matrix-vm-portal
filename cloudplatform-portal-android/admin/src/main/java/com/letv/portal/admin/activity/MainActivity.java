package com.letv.portal.admin.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import com.letv.portal.admin.R;
import com.letv.portal.admin.uApplication;



public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public Dialog progressDialog=null;
    public static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uApplication.mainActivity=this;
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment=null;
        switch (position) {
            case 0:
                fragment=new Fragment_about();
                break;
            case 1:
                fragment=new Fragment_statistics();
                break;
            case 2:
                fragment=new Fragment_cluster();
                break;
            case 3:
                fragment=new Fragment_database();
                break;
            default:
                //还未完成的Fragment
                msg_DoesNotExist();
                return;
        }
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, (position));
        fragment.setArguments(args);

        ShowFragment(fragment);
    }

   //显示Fragment页面
    public void ShowFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        String[] itemTitle = getResources().getStringArray(R.array.item_title);
        switch (number) {
            case 0:
                mTitle = "关于";
                break;
            case 20:
                mTitle = getString(R.string.title_section20);
                break;
            default:
                mTitle=   itemTitle[number-1];
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void msg_DoesNotExist() {
        showInfoDlg(getResources().getString(R.string.msg_construction));
    }

    public void showInfoDlg(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("消息")
                .setMessage(msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("确定", null)
                .show();
    }

    public void ShowProcessdlg(String message){
        if (progressDialog==null) {
            progressDialog = new Dialog(this, R.style.progress_dialog);
        }
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(null==message?"卖力加载中":message);
        progressDialog.show();
    }

    public void HideProcessdlg() {
       if (null!=progressDialog) progressDialog.dismiss();
    }

    /**
     * 宿主activity要实现的回调接口
     * 用于activity与该fragment之间通讯
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * 当drawer中的某个item被选择是调用该方法
         */
        void onNavigationDrawerItemSelected(String title);
    }
}
