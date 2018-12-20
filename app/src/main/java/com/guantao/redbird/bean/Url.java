package com.guantao.redbird.bean;

public class Url {
    //
    //内网
    private static String URL = "http://192.168.1.105:8080/";  ///zhixing/user/Login.do
    private static String URL_2 = "http://61.50.105.254:38080/";
    private static String URL_3 = "http://183.196.89.147:8099/";  //"http://39.105.87.199:8080/"; //" ; //http://183.196.89.147:8099/";
    //注册接口
    public static String USER_REGISTER_PATH = URL_3
            + "zhixing/phone/addSysUser.do";
    //登录
    public static String USER_LOGIN_PATH = URL_3
            + "zhixing/user/loginmyUser.do";
    //部门信息
    public static String DEPARTMENT_INFOMATION = URL_3
            + "zhixing/department/findDepartments.do";
    //部门人员信息
    public static String DEPARTMENT_USERS = URL_3
            + "zhixing/phone/findDepAllUsers.do";
    //线索上传
    public static String APPLY_CLUE_PATH = URL_3
            + "zhixing/phone/saveClue.do";
    //线索上传
    public static String APPLY_INSPECTION_PATH = URL_3
            + "zhixing/phone/insertInspection";
    //查询部门人员信息-含loginid
    public static String DEPARTMENT_LOGIN_INFOMATION = URL_3
             + "zhixing/phone/findDepartmentLoginUser.do";
    public static String UPLOAD_IMAGE_URL =  "http://183.196.89.147:8099/javaweb_war/uploadserver";  //server接收地址" "http://39.105.87.199:8080/zhixing/phone/uploadServer_phone"
    //修改密码
    public static String UPDATE_PASSWARD_URL = "http://183.196.89.147:8099/zhixing/phone/modify_pwd";

}
