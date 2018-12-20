package com.mm.dss.demo1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.*;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.telephony.TelephonyManager;
import com.android.business.adapter.DataAdapteeImpl;
import com.android.business.adapter.DataAdapterInterface;
import com.android.business.entity.UserInfo;
import com.android.business.exception.BusinessException;
import com.example.dhcommonlib.util.PreferencesHelper;
import com.mm.dss.demo1.base.BaseActivity;
import com.mm.dss.demo1.devices.fragments.DeviceFragment;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_PHONE_STATE = 1;
    DeviceFragment mFragment;
    private DataAdapterInterface dataAdapterInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataAdapterInterface = DataAdapteeImpl.getInstance();
        mFragment = DeviceFragment.newInstance("", "");

        FragmentManager manager = getSupportFragmentManager();  //管理 框架
        FragmentTransaction ft = manager.beginTransaction();   //事务 开始 框架
        ft.replace(R.id.content, mFragment);   //DeviceFragment替换main里的框架   动态加载
        ft.commit();

        setIPPort("183.196.89.147", "29000");  //设置ip和端口
        login("ceshi", "admin123456");
//        etIP.setText("183.196.89.147");
//        etPort.setText("29000");
//        etUsername.setText("ceshi");
//        etPassword.setText("admin123456");



    }

    private void setIPPort(String ip, String port) {
        try {
            dataAdapterInterface.createDataAdapter("com.android.business.adapter.DataAdapteeImpl");
            dataAdapterInterface.initServer(ip, Integer.parseInt(port));
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }
    @Override

    public void onRequestPermissionsResult ( int requestCode, String[] permissions,int[] grantResults){

        if (requestCode == REQUEST_PHONE_STATE && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

            //String clientMac = tm.getDeviceId();

        }

    }


    private void login(final String username, final String password) {



                //TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

//            toast("需要动态获取权限");

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);

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
                }







    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    dataAdapterInterface.logout();
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    long lastBack = 0;
    @Override
    public void onBackPressed() {      //改写按下返回键的逻辑
        long currentBack = System.currentTimeMillis();
        if (currentBack - lastBack > 2000) {
            lastBack = currentBack;
            toast(R.string.main_exit_tips);
        } else {
            exitApp();
        }
    }

    private void exitApp(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            finish();
        }
        System.exit(0);
        Process.killProcess(Process.myPid());
    }
}
