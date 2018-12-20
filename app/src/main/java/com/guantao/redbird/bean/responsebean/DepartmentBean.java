package com.guantao.redbird.bean.responsebean;

import java.util.ArrayList;

public class DepartmentBean {
    public DepartmentResponse department_response;

    public class DepartmentResponse{
        public ArrayList<Department> item_dep;
        public String code;
        public int total;

    }

    public class Department {
        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getDepartment_memo() {
            return department_memo;
        }

        public void setDepartment_memo(String department_memo) {
            this.department_memo = department_memo;
        }

        private int department_id;
        private String department_name;
        private String department_memo;
    }

}
