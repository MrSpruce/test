package com.guantao.redbird.bean.responsebean;

import java.util.ArrayList;

public class DepartmentUserBean {
    public LoginUserResponse deploginuser_response;
    public class  LoginUserResponse{
        public LoginUser item_users;
        public String code;

    }
    public class LoginUser {
        public String getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public int getLogin_id() {
            return login_id;
        }

        public void setLogin_id(int login_id) {
            this.login_id = login_id;
        }

        String department_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        int login_id ;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        String user_id;
        String user_name;
    }

}
