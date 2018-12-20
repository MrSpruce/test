package com.guantao.redbird.utils;

import android.content.Context;

import com.guantao.redbird.bean.Url;
import com.guantao.redbird.bean.dbbean.ClueRegistration;
import com.guantao.redbird.bean.dbbean.Login;
import com.guantao.redbird.bean.dbbean.LoginApply;
import com.guantao.redbird.bean.dbbean.UserInspection;
import com.guantao.redbird.bean.responsebean.LoginBean;
import com.guantao.redbird.myapplication.MainActivity;
import com.guantao.redbird.myapplication.MyApplication;
import com.guantao.redbird.myapplication.MyCallBack;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DBUtils {

    public DBUtils() {
    }

    public static int getClueNumber() {
        int nNum = 0;

        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(ClueRegistration.class).tableIsExist() == false)
                return nNum;

        } catch (DbException e) {
            e.printStackTrace();
        }


        try {
            List<ClueRegistration> beanClueReg =
                    db.selector(ClueRegistration.class)
                            .where("clue_id", "!=", null)
                            .orderBy("clue_number", true)
                            .findAll();
            int nLen = beanClueReg.size();
            if (nLen == 0)
                return nNum;
            nNum = beanClueReg.get(0).getClue_number();


        } catch (DbException e) {
            e.printStackTrace();
        }

        return nNum;

    }

    public static Login getLoginBeanbyUserId(String UserId) {
        Login bean = null;
        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(LoginApply.class).tableIsExist() == false)
                return null;

        } catch (DbException e) {
            e.printStackTrace();
        }

        try {
            bean = db.findById(Login.class, UserId);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return bean;

    }

    /**
     * 根据线索编号获取线索信息
     *
     * @param clueId
     * @return
     */
    public static ClueRegistration getBeanbyClueId(String clueId) {
        ClueRegistration bean = null;
        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(ClueRegistration.class).tableIsExist() == false)
                return null;

        } catch (DbException e) {
            e.printStackTrace();
        }

        try {
            bean = db.findById(ClueRegistration.class, clueId);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return bean;

    }

    /**
     * 根据用户编号获取提交认信息
     *
     * @param UserId
     * @return
     */
    public static LoginApply getBeanbyUserId(String UserId) {
        LoginApply bean = null;
        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(LoginApply.class).tableIsExist() == false)
                return null;

        } catch (DbException e) {
            e.printStackTrace();
        }

        try {
            bean = db.findById(LoginApply.class, UserId);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return bean;

    }


    /**
     * 根据线索编号删除记录
     *
     * @param clueId
     * @return
     */
    public static boolean deleteBeanbyClueId(String clueId) {
        ClueRegistration bean = null;
        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(ClueRegistration.class).tableIsExist() == false)
                return false;

        } catch (DbException e) {
            e.printStackTrace();
        }
        try {
            String sqlString = "DELETE FROM registration_clue where clue_id ='" + clueId + "'";
            db.execNonQuery(sqlString);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 上报线索-服务器端
     *
     * @param beanClue
     * @param context
     */
    public static void applyClue(final ClueRegistration beanClue, final Context context) {
        if (beanClue == null) {
            ToastUtil.showToast(context, "无线索信息！");
            return;
        }
        //判断是否有网络
        String path = Url.APPLY_CLUE_PATH;
        NetworkConnectionDetector networkConnectionDetector = new NetworkConnectionDetector(context);
        if (networkConnectionDetector.isConnectingToInternet()) {
            int nType = networkConnectionDetector.getNetworkType();
            String sIp = networkConnectionDetector.getIP();
            if (networkConnectionDetector.getNetworkType() == NetworkConnectionDetector.NETTYPE_WIFI
                    && networkConnectionDetector.getIP().startsWith("192.168.1.")
                    && networkConnectionDetector.getWifiName().startsWith("\"JZ")
                    && networkConnectionDetector.getWifiName().length() == 5) {
                path = Url.DEPARTMENT_INFOMATION;
            }
        } else {
            ToastUtil.showToast(context, "无网络连接");
        }
        //上传
        HashMap<String, Object> map = new HashMap<>();

        map.put("login_name", MyApplication.userName);                                //用户姓名
        map.put("clue_title", beanClue.getClue_title());                              //违法线索标题
        map.put("clue_source", beanClue.getClue_source());                            //违法线索来源
        map.put("illegal_address", beanClue.getIllegal_address());                   //违法线索地址
        map.put("illegal_address_village", beanClue.getIllegal_address_village());  //违法线索村庄
        map.put("illegal_address_town", beanClue.getIllegal_address_town());         //违法线索镇
        map.put("contact", beanClue.getContact());                                     //违法联系人
        map.put("contact_phone", beanClue.getContact_phone());                         //违法联系人-电话
        map.put("contact_address", beanClue.getContact_address());                    //违法联系人-地址
        map.put("clue_content", beanClue.getClue_content());                           //违法内容
        map.put("clue_geometry", beanClue.getClue_geometry());                         //线索范围
        map.put("operator_suggestion", "建议核查");
        Date date = new Date();
        long datetime = date.getTime();
        map.put("operator_suggestion_time", String.valueOf(datetime));
        map.put("operator_sign", beanClue.getOperator_sign());                         //操作人员签名

        PostUtils.Post(path, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                ToastUtil.showToast(context, result);
                if (result.equals("插入成功！")) {
                    beanClue.setIs_apply(1);
                    try {
                        MainActivity.getApplicationDb().update(beanClue);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.showToast(context, "登录超时，请检查网络连接或服务器连接状态");
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }

    /**
     * 上报线索-服务器端-更新
     * @param beanClue
     * @param context
     */
    public static void applyClue2(final ClueRegistration beanClue, final Context context) {
        LoginApply applyBean = getBeanbyUserId(MyApplication.userId);
        Login loginbean = getLoginBeanbyUserId(MyApplication.userId);

        if (beanClue == null) {
            ToastUtil.showToast(context, "无线索信息！");
            return;
        }
        //判断是否有网络
        String path = Url.APPLY_CLUE_PATH;
        NetworkConnectionDetector networkConnectionDetector = new NetworkConnectionDetector(context);
        if (networkConnectionDetector.isConnectingToInternet()) {
            int nType = networkConnectionDetector.getNetworkType();
            String sIp = networkConnectionDetector.getIP();
            if (networkConnectionDetector.getNetworkType() == NetworkConnectionDetector.NETTYPE_WIFI
                    && networkConnectionDetector.getIP().startsWith("192.168.1.")
                    && networkConnectionDetector.getWifiName().startsWith("\"JZ")
                    && networkConnectionDetector.getWifiName().length() == 5) {
                path = Url.DEPARTMENT_INFOMATION;
            }
        } else {
            ToastUtil.showToast(context, "无网络连接");
        }
        //上传
        HashMap<String, Object> map = new HashMap<>();

        map.put("login_name", loginbean.getUser_name());                              //用户姓名
        map.put("clue_title", beanClue.getClue_title());                              //违法线索标题
        map.put("clue_source", beanClue.getClue_source());                            //违法线索来源
        map.put("illegal_address", beanClue.getIllegal_address());                   //违法线索地址
        map.put("illegal_address_village", beanClue.getIllegal_address_village());  //违法线索村庄
        map.put("illegal_address_town", beanClue.getIllegal_address_town());         //违法线索镇
        map.put("contact", beanClue.getContact());                                     //违法联系人
        map.put("contact_phone", beanClue.getContact_phone());                         //违法联系人-电话
        map.put("contact_address", beanClue.getContact_address());                    //违法联系人-地址
        map.put("clue_content", beanClue.getClue_content());                           //违法内容
        map.put("clue_geometry", beanClue.getClue_geometry());                         //线索范围
        map.put("operator_suggestion", "建议核查");
        Date date = new Date();
        long datetime = date.getTime();
        map.put("operator_suggestion_time", String.valueOf(datetime));
        map.put("operator_sign", beanClue.getOperator_sign());                         //操作人员签名
        map.put("leader_login_id", applyBean.getApply_leader_loginid());
        map.put("operator_login_id", loginbean.getLogin_id_server());
        PostUtils.Post2(path, map, new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                MyApplication.clueId_Server = result; //服务器端线索编号
                ToastUtil.showToast(context, result);
                //if(result.indexOf("馆陶")!=-1){ //获取服务器线索编号
                beanClue.setIs_apply(1);
                beanClue.setClue_id_server(result);
                try {
                    MainActivity.getApplicationDb().update(beanClue);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                ToastUtil.showToast(context, "上传成功！");
              // }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.showToast(context, "登录超时，请检查网络连接或服务器连接状态");
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }


    //liang20180809

    /**
     * 根据num获取签到信息
     *
     * @param num
     * @return
     */
    public static UserInspection getBeanbyInspectionId(int num) {
        UserInspection bean = null;
        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(UserInspection.class).tableIsExist() == false)
                return null;

        } catch (DbException e) {
            e.printStackTrace();
        }

        try {
            bean = db.selector(UserInspection.class)
                    .where("num", "=", num).findFirst();

        } catch (DbException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 根据签到编号删除记录
     *
     * @param num
     * @return
     */
    public static boolean deleteUserInspectionbyNum(int num) {
        ClueRegistration bean = null;
        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(UserInspection.class).tableIsExist() == false)
                return false;

        } catch (DbException e) {
            e.printStackTrace();
        }
        try {
            String sqlString = "DELETE FROM user_inspection where num =" + num + "";
            db.execNonQuery(sqlString);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上报线索-服务器端
     *
     * @param
     * @param context
     */
    public static void applyInspection(final UserInspection userInspection, final Context context) {
        if (userInspection == null) {
            ToastUtil.showToast(context, "无签到信息！");
            return;
        }
        //判断是否有网络
        String path = "http://39.105.87.199:8080/zhixing/phone/insertInspection";
        NetworkConnectionDetector networkConnectionDetector = new NetworkConnectionDetector(context);
        if (networkConnectionDetector.isConnectingToInternet()) {
//            int nType = networkConnectionDetector.getNetworkType();
//            String sIp = networkConnectionDetector.getIP();
//            if (networkConnectionDetector.getNetworkType() == NetworkConnectionDetector.NETTYPE_WIFI
//                    && networkConnectionDetector.getIP().startsWith("192.168.1.")
//                    && networkConnectionDetector.getWifiName().startsWith("\"JZ")
//                    && networkConnectionDetector.getWifiName().length() == 5) {
//                path = Url.DEPARTMENT_INFOMATION;
//            }
        } else {
            ToastUtil.showToast(context, "无网络连接");
        }
        //上传
        HashMap<String, Object> map = new HashMap<>();

        map.put("loginId_server", userInspection.getLoginId_server());
        map.put("country", userInspection.getCounty());
        map.put("township", userInspection.getTownship());
        map.put("village", userInspection.getVillage());
        map.put("detailAddress", userInspection.getDetailaddress());
        map.put("inspect_time", userInspection.getInspect_time());
        map.put("lat", userInspection.getLat());
        map.put("longitude", userInspection.getLon());
        map.put("status", userInspection.getStatus());

        PostUtils.Post(path, map, new MyCallBack<String>() {

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
//                ToastUtil.showToast(context, result);
                if (Boolean.parseBoolean(result)) {
                    userInspection.setIs_apply(1);
                    //MainActivity.getApplicationDb().update(userInspection);
                    if (updateInspectionIsApplyByUserIdAndTime(userInspection) == 1) {
                        ToastUtil.showToast(context, "上报成功！");
                    } else {
                        ToastUtil.showToast(context, "上报成功！，上报状态修改失败。");
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.showToast(context, "登录超时，请检查网络连接或服务器连接状态");
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }

    public static int updateInspectionIsApplyByUserIdAndTime(UserInspection userInspection) {
        int state = 0;
        ClueRegistration bean = null;
        DbManager db = MainActivity.getApplicationDb();
        try {
            if (db.getTable(UserInspection.class).tableIsExist() == false)
                return state;

        } catch (DbException e) {
            e.printStackTrace();
        }
        try {
            String sqlString = "update user_inspection set is_apply = 1 where Inspect_time = " + userInspection.getInspect_time() + " and user_id = " + userInspection.getUser_id();
            state = db.executeUpdateDelete(sqlString);
        } catch (DbException e) {
            e.printStackTrace();
            return state;
        }
        return state;
    }

}
