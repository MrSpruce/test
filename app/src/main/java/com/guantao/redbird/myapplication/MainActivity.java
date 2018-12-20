package com.guantao.redbird.myapplication;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.guantao.redbird.bean.dbbean.Login;
import com.guantao.redbird.ui.activity.ClueActivity;
import com.guantao.redbird.utils.MyLocationListener;
import com.guantao.redbird.utils.ToastUtil;
import com.guantao.redbird.utils.PostUtils;
import com.guantao.redbird.utils.GsonUtils;
import com.guantao.redbird.utils.NetworkConnectionDetector;
import com.guantao.redbird.bean.Url;
import com.guantao.redbird.bean.responsebean.LoginBean;



import org.xutils.DbManager;
import org.xutils.DbManager.DaoConfig;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.et_login_name)
    private EditText et_login_name;
    @ViewInject(R.id.et_login_phone)
    private EditText et_login_phone;
    @ViewInject(R.id.bt_login)
    private Button bt_login;
    @ViewInject(R.id.bt_register)
    private Button bt_register;
    @ViewInject(R.id.ibt_drop_down)
    private ImageButton ibt_drop_down;
    @ViewInject(R.id.ll_input)
    private LinearLayout ll_input;

    public static String userName;
    public static String userId;
    public static String city;
    private DaoConfig daoConfig;

    private Context mContext;

    private boolean bLoginFlag = false;

    private static  DbManager db;
    private String login_name;
    private String login_phone;
    //	private String login_id = null;
    private static final String REGEX_MOBILE = "^((1[3|5|7|8|][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private ProgressDialog progress;
    private PopupWindow popupWindow;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private String mProviderName;
    /* Position */
    private static final int MINIMUM_TIME = 10000;  // 10s
    private static final int MINIMUM_DISTANCE = 50; // 50m




    /**
     * 保存定位坐标
     */
    public static LatLng locationLatlng;
    /**
     * 保存定位状态信息
     */
    public static  BDLocation locationDbInfo = null;

    /**
     * 定位端
     */
    public static LocationClient mLocationClient = null;


    private MyLocationListener myListener = new MyLocationListener();



    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }*/
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SDKInitializer.initialize(getApplicationContext());

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现

        //SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);


        //注入view和事件
        x.view().inject(this);

        mContext = this;

        //setContentView(R.layout.activity_login);

        //x.Ext.init(getApplication());
        //x.Ext.setDebug(BuildConfig.DEBUG);

        userName = null;
        userId = null;

        mLocationClient = new LocationClient(getApplicationContext()); //声明LocationClient类

        mLocationClient.registerLocationListener(myListener); //注册监听函数


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                    },
                    1);
        }

        String sPath = Environment.getExternalStorageDirectory()  + "/land_mintor_guantao/PIC"; //Environment.getExternalStorageDirectory() + "/";
        File fileFolder = new File(sPath);
        if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"parksurvey"的目录
            fileFolder.mkdir();
        }

        /*File file = new File(sPath);

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
        //获取数据库
        db = x.getDb(daoConfig);
        db.getDatabase();*/

        //获取数据库
        db = x.getDb(((MyApplication)getApplication()).getDaoConfig());
        // mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);



        // Get the best provider between gps, network and passive
        Criteria criteria = new Criteria();
        mProviderName = locManager.getBestProvider(criteria, true);

        // API 23: we have to check if ACCESS_FINE_LOCATION and/or ACCESS_COARSE_LOCATION permission are granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // No one provider activated: prompt GPS
            if (mProviderName == null || mProviderName.equals("")) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }

            // At least one provider activated. Get the coordinates
            switch (mProviderName) {
                case "passive":
                    //locManager.requestLocationUpdates( mProviderName,MINIMUM_TIME, MINIMUM_DISTANCE, this);
                    Location location = locManager.getLastKnownLocation(mProviderName);
                    break;

                case "network":
                    break;

                case "gps":
                    break;

            }

            // One or both permissions are denied.
        } else {

            // The ACCESS_COARSE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }

            // The ACCESS_FINE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        MY_PERMISSION_ACCESS_FINE_LOCATION);
            }

        }


        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
        }

        //兼容android7.0 使用共享文件的形式
        /*ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
        //检查是否有存储权限，以免崩溃
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ToastUtil.makeText(this,"请开启存储权限",ToastUtil.LENGTH_SHORT).show();
            return;
        }*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        initLocation();
        // 开始定位
        if (!mLocationClient.isStarted()) {
            mLocationClient.restart();
        }else if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }

    //初始化本机用户的sqlite本地数据库
   /* private void initDatabase(){
        // 获得可写的数据库对象（若数据库不存在。先创建数据库。再获取可读可写的数据库对象；假设数据库存在，就直接打开）
        oh = new MyOpenHelper(getApplicationContext(), "person.db", null, 1);
        db = oh.getWritableDatabase();
        ToastUtil.showToast(mContext,"本地数据库创建成功");

    }*/

    public DaoConfig getDaoConfig() {
        return daoConfig;
    }

    /**
     * 点击事件
     * @param v
     */
    @Event(value = {R.id.et_login_phone,R.id.bt_login,R.id.bt_register,R.id.ibt_drop_down})
    private void onClick(View v){
        switch (v.getId()) {
            case R.id.et_login_phone:

                et_login_phone.setKeyListener(new NumberKeyListener() {

                    protected char[] getAcceptedChars() {
                        char[] numberChars = { '1', '2', '3', '4', '5', '6', '7',
                                '8', '9', '0', };
                        return numberChars;
                    }

                    @Override
                    public int getInputType() {
                        // TODO Auto-generated method stub
                        return InputType.TYPE_CLASS_PHONE;
                    }

                });
                break;

            case R.id.bt_login://登录
                login_name = "1234";
                login_phone = "1234";
                Intent login_intent = new Intent(mContext, ClueActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserId",   login_name);
                bundle.putString("UserName", login_name);
                login_intent.putExtras(bundle);
                startActivity(login_intent);
                break;
            case R.id.bt_register://注册
                //Intent register_intent = new Intent(mContext, RegisterActivity.class);
                //startActivityForResult(register_intent, 9);
                break;
            case R.id.ibt_drop_down://下拉框

                //List<Cache> findAllCache = findAllCache();
                //if (findAllCache != null) {
                 //   showPopupWindow(findAllCache);
               // }
                break;

            default:
                break;
        }
    }

    /**
     * 登录
     * @param login_name
     * @param login_phone
     */
    private void Login() {

        HashMap<String,Object> map = new HashMap<>();
        map.put("login_name", login_name);
        map.put("login_pwd", login_phone);

        String path = Url.USER_LOGIN_PATH;  //Url.DEPARTMENT_INFOMATION; //
        NetworkConnectionDetector networkConnectionDetector = new NetworkConnectionDetector(mContext);


        if (networkConnectionDetector.isConnectingToInternet()) {
            int nType = networkConnectionDetector.getNetworkType();
            String sIp = networkConnectionDetector.getIP();
            if (networkConnectionDetector.getNetworkType() == NetworkConnectionDetector.NETTYPE_WIFI
                    && networkConnectionDetector.getIP().startsWith("192.168.1.")
                    && networkConnectionDetector.getWifiName().startsWith("\"JZ")
                    && networkConnectionDetector.getWifiName().length() == 5) {
                path = Url.USER_LOGIN_PATH;
            }
        }else {
            ToastUtil.showToast(mContext, "无网络连接");
        }

        PostUtils.Post(path, map, new MyCallBack<String>(){

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);

                LoginBean loginBean =GsonUtils.java2Bean(result,LoginBean.class);
                //DepartmentBean departmentBean = GsonUtils.java2Bean(result, DepartmentBean.class);
                //String s = departmentBean.department_response.code;
                if (TextUtils.equals(loginBean.user_login_response.code, "1")) {

                    ToastUtil.showToast(mContext, "登录成功");
                    MyApplication.userId = login_name;                                      //用户登录名
                    String login_id_server = loginBean.user_login_response.login_id;
                    MyApplication.loginId_Server = Integer.parseInt(login_id_server);         //用户服务器登录编号
                    String user_name =loginBean.user_login_response.user_name;
                    MyApplication.userName = user_name;                                       //用户姓名
                    String dempartment_id = loginBean.user_login_response.department_id;
                    MyApplication.departmentId = dempartment_id;                              //用户所属部门编号
                    //登录成功后将服务器端信息保存在本地
                    Login beanLogin = new Login();
                    beanLogin.setLogin_name(login_name);
                    beanLogin.setLogin_pwd(login_phone);//login_phone
                    beanLogin.setUser_name(user_name);
                    if(!login_id_server.trim().isEmpty()) {
                        beanLogin.setLogin_id_server(Integer.parseInt(login_id_server));
                    }
                    if(!dempartment_id.trim().isEmpty()) {
                        beanLogin.setLogin_department_id(Integer.parseInt(dempartment_id));
                    }
                    try {
                        db.save(beanLogin);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    //加进度条
                    //			加载progressDialog
                    progress = ProgressDialog.show(mContext, null, "正在登录....");
                    progress.setCancelable(true);
                    //触摸屏幕其它区域，就会让这个progressDialog消失
                    progress.setCanceledOnTouchOutside(false);
                    ToastUtil.showToast(mContext, "登录成功");

                    progress.dismiss();//隐藏进度条
                    Intent login_intent = new Intent(mContext, ClueActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("UserId",   login_name);
                    bundle.putString("UserName", login_name);
                    login_intent.putExtras(bundle);
                    startActivity(login_intent);

                }else if (TextUtils.equals(loginBean.user_login_response.code, "0")) {
                    ToastUtil.showToast(mContext, "登录失败");
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.showToast(mContext, "登录超时，请检查网络连接或服务器连接状态");
            }
            @Override
            public void onFinished() {
                super.onFinished();
                //progress.dismiss();//隐藏进度条
            }

        });


    }

    /**
     * 获取当前数据库
     * @return
     */
    public static  DbManager getApplicationDb() {
        return db;
    }



    private static boolean isPhone(String phone) {
        return Pattern.matches(REGEX_MOBILE, phone);
    }

    private void initLocation() {

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mLocationClient.setLocOption(option);

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


}
