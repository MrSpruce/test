package com.guantao.redbird.myapplication;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.model.LatLng;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.x;


import java.io.File;
import java.util.List;

public class MyApplication extends Application {

    public static String userName;           //用户姓名
    public static String userId;             //用户登录名称
    public static String departmentId;       //用户所属部门
    public static int    loginId_Server;     //用户服务器端login_id
    public static String clueId_Server;       //线索插入后服务器端生成的线索编号
    public static String city;
    public static String APPLICATION_COUNTRY = "馆陶"; //适用地
    public static int REQUEST_CLUEDETAIL_CODE = 100; //线索详细页面标志码
    public static int REQUEST_MAPEDIT_CODE = 20;      //地图-绘制边界界面
    public static String PHOTO_STORE_PATH = Environment.getExternalStorageDirectory() + "/land_mintor_guantao/PIC/"; //照片存储路径

    private DbManager.DaoConfig daoConfig;
    /**
     * 保存定位坐标
     */
    public static LatLng locationLatlng;
    /**
     * 定位端
     */
    public static LocationClient mLocClient;

    public static MKOfflineMap mkOfflineMap;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        x.Ext.init(this);                          //初始化数据库控件
        x.Ext.setDebug(BuildConfig.DEBUG);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        userName = null;
        userId = null;
        city = null;
        locationLatlng = null;
        mLocClient = new LocationClient(getApplicationContext());
        mkOfflineMap = null;


        String sPath = Environment.getExternalStorageDirectory() + "/land_mintor_guantao/"; //Environment.getExternalStorageDirectory() + "/";
        File fileFolder = new File(sPath);
        if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"parksurvey"的目录
            fileFolder.mkdir();
        }

        File file = new File(sPath);

        //初始化本机用户数据库
        //initDatabase();

        daoConfig = new DbManager.DaoConfig()
                .setDbName("landEnforce_guantao.db")//创建数据库的名称parksurvey_xian
                .setDbVersion(1)//数据库版本号
                .setDbDir(file)
                .setAllowTransaction(true)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {

                    @Override
                    public void onUpgrade(DbManager db,int oldVersion,int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作

    }
    public DbManager.DaoConfig getDaoConfig() {

        return daoConfig;
    }
}
