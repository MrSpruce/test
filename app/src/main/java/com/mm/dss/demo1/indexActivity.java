package com.mm.dss.demo1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;

import com.android.business.adapter.DataAdapteeImpl;
import com.android.business.adapter.DataAdapterInterface;
import com.android.business.entity.UserInfo;
import com.android.business.exception.BusinessException;
import com.example.dhcommonlib.util.PreferencesHelper;
import com.mm.dss.demo1.base.BaseActivity;

/**
 * Created by 26499 on 2017/11/14.
 */

public class indexActivity extends BaseActivity  {
    private static final int REQUEST_PHONE_STATE = 1;


//    private EditText etIP;
//    private EditText etPort;
//    private EditText etUsername;
//    private EditText etPassword;
//    private Button bLogin;
    private DataAdapterInterface dataAdapterInterface;
    private Context mContext;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    dissmissProgressDialog();
                    boolean ret = (Boolean) msg.obj;
                    if (ret) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        finish();
                    } else {
                        toast(R.string.login_failed);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //initView();
        initData();
    }

//    private void initView() {
////        etIP = (EditText) findViewById(R.id.ip);
////        etPort = (EditText) findViewById(R.id.port);
////        etUsername = (EditText) findViewById(R.id.username);
////        etPassword = (EditText) findViewById(R.id.password);
////        bLogin = (Button) findViewById(R.id.login);
////        bLogin.setOnClickListener(this);
////        etIP.setText("172.7.55.251");
////        etPort.setText("9000");
////        etUsername.setText("system");
////        etPassword.setText("admin123");
////        etIP.setText("183.196.89.147");
////        etPort.setText("29000");
////        etUsername.setText("ceshi");
////        etPassword.setText("admin123456");
//    }

    private void initData() {
        dataAdapterInterface = DataAdapteeImpl.getInstance();
        mContext = this;
    }


    public void deng(View view) {


//                String ip = etIP.getText().toString().trim();
//                String port = etPort.getText().toString().trim();
//                String username = etUsername.getText().toString().trim();
//                String password = etPassword.getText().toString().trim();
//                if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(port) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
//                    toast(R.string.login_param_null);
//                    return;
//                }
                setIPPort("183.196.89.147", "29000");
                login("ceshi", "admin123456");

        }


    private void setIPPort(String ip, String port) {
        try {
            dataAdapterInterface.createDataAdapter("com.android.business.adapter.DataAdapteeImpl");
            dataAdapterInterface.initServer(ip, Integer.parseInt(port));
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    /**
     *加个获取权限的监听

     */

    @Override

    public void onRequestPermissionsResult ( int requestCode, String[] permissions,int[] grantResults){

        if (requestCode == REQUEST_PHONE_STATE && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

            //String clientMac = tm.getDeviceId();

        }

    }


    private void login(final String username, final String password) {
        showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

//            toast("需要动态获取权限");

                    ActivityCompat.requestPermissions(indexActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);

                } else {

//            toast("不需要动态获取权限");

                    TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                    String clientMac = tm.getDeviceId();


                    //String clientMac = tm.getDeviceId();
                    UserInfo info = null;
                    try {
                        info = dataAdapterInterface.login(username, password, "1", clientMac, 2);
                    } catch (BusinessException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (info != null) {
                        Object groupid = info.getExtandAttributeValue("groupId");
                        String strGroupID = "";
                        if (groupid instanceof String) {
                            strGroupID = (String) groupid;
                        } else if (groupid instanceof Long) {
                            strGroupID = ((Long) groupid).toString();
                        }
                        PreferencesHelper.getInstance(getApplicationContext()).set("USER_GROUPID", strGroupID);
                    }
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = (info != null);
                    handler.sendMessage(msg);
                }



            }


        }).start();


    }
}



//Android6.0需要动态获取权限




