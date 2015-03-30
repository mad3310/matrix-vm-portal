package com.letv.portal.admin.activity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.letv.portal.admin.R;
import com.letv.portal.admin.uApplication;
import com.letv.portal.admin.helpers.OAuth;
import com.letv.portal.admin.utils.SimpleCrypto;

/**
 * Created by Justin on 2015/3/24.
 */
public  class Fragment_login extends uFragment {
    private EditText ed_username,ed_passwd;
    private CheckBox cb_ldap,cb_autologin,cb_Remember_me;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ed_username=(EditText)rootView.findViewById(R.id.et_username);
        ed_passwd=(EditText)rootView.findViewById(R.id.et_passwd);
        cb_ldap=(CheckBox)rootView.findViewById(R.id.cb_ldap);
        cb_Remember_me=(CheckBox)rootView.findViewById(R.id.cb_Remember_me);
          cb_autologin=(CheckBox)rootView.findViewById(R.id.cb_auto);
        init();

        ((Button) rootView.findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_username.getText().toString().equals("")) {
                    uApplication.mainActivity.showInfoDlg("用户名不能为空");
                    ed_username.setFocusable(true);
                    return;
                }
                if (ed_passwd.getText().toString().equals("")) {
                    uApplication.mainActivity.showInfoDlg("密码不能为空");
                    ed_passwd.setFocusable(true);
                    return;
                }
                if (cb_autologin.isChecked()) {
                    cb_Remember_me.setChecked(true);
                }
                //登录
                OAuth.Auth(ed_username.getText().toString(), ed_passwd.getText().toString(), cb_ldap.isChecked(), new uFragment.OnRestApiListener() {

                    @Override
                    public void OnRestApi(boolean onSuccess, byte[] bytes) {
                        if (onSuccess) {

                            uFragment fragment = new Fragment_statistics();
                            Bundle args = new Bundle();
                            args.putInt(uApplication.mainActivity.ARG_SECTION_NUMBER, (0));
                            fragment.setArguments(args);
                            uApplication.mainActivity.ShowFragment(fragment);

                            uApplication.mainActivity.showInfoDlg(getResources().getString(R.string.login_success));

                            SharedPreferences.Editor editor = uApplication.AppOptions.edit();
                            //保存登录信息
                            if (cb_Remember_me.isChecked()) {
                                try {
                                    editor.putString("uname", SimpleCrypto.encrypt( "111",ed_username.getText().toString()));
                                    editor.putString("upwd", SimpleCrypto.encrypt( "112",ed_passwd.getText().toString()));
                                    editor.putBoolean("uldap", cb_ldap.isChecked());
                                    editor.putBoolean("uautologin", cb_autologin.isChecked());
                                    editor.putBoolean("uremember_me", cb_Remember_me.isChecked());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                editor.remove("uautologin");
                                editor.remove("uname");
                                editor.remove("upwd");
                                editor.remove("uldap");
                                editor.remove("uremember_me");
                            }
                            editor.commit();
                        } else {
//                            uApplication.mainActivity.showInfoDlg(getResources().getString(R.string.login_failure));
                            new AlertDialog.Builder(uApplication.mainActivity)
                                    .setTitle("消息")
                                    .setIcon(android.R.drawable.stat_notify_error)
                                    .setMessage(getResources().getString(R.string.login_failure))
                                    .setNegativeButton("确定", null)
                                    .show();
                        }
                    }
                });
            }
        });
        ((TextView) rootView.findViewById(R.id.register_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login
                uApplication.mainActivity.msg_DoesNotExist();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init() {

        if (uApplication.AppOptions.contains("uremember_me") && uApplication.AppOptions.getBoolean("uremember_me",false)){
            try {
                ed_username.setText(SimpleCrypto.decrypt("111",uApplication.AppOptions.getString("uname", "")));
                ed_passwd.setText(SimpleCrypto.decrypt("112", uApplication.AppOptions.getString("upwd", "")));
                cb_ldap.setChecked(uApplication.AppOptions.getBoolean("uldap", false));
                cb_Remember_me.setChecked(true);
                cb_autologin.setChecked(uApplication.AppOptions.getBoolean("uautologin", false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void register(View v) {
        uApplication.mainActivity.msg_DoesNotExist();
    }

}
