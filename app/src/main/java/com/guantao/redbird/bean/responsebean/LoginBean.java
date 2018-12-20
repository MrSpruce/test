package com.guantao.redbird.bean.responsebean;

/**
 *
 * 登录接口--返回json对应的Bean
 *
 */
public class LoginBean {

    public LoginResponse user_login_response;

    public class LoginResponse{

        public String created;
        public String user_id;
        public String user_name;
        public String code;
        public String login_id;
        public String department_id;
    }

}
