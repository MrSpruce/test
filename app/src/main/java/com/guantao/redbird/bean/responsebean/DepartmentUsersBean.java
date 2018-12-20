package com.guantao.redbird.bean.responsebean;



import java.util.ArrayList;

public class DepartmentUsersBean {
    public DepartUsersResponse department_response;
    public class  DepartUsersResponse{
        public ArrayList<DepartmentUserInfo> item_users;
        public String code;
        public int total;
    }

    public class DepartmentUserInfo {

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        private String user_id;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        private String user_name;

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        private int department_id;

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        private String department_name;

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        private String duty;
    }
}
