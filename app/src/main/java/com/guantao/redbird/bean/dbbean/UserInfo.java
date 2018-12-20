package com.guantao.redbird.bean.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "user_information")
public class UserInfo {

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    //用户编号
    @Column(name="user_id",isId=true)
    private String user_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    //用户名称
    @Column(name="user_name")
    private String user_name;

    //部门编号
    @Column(name="department_id")
    private int department_id;

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    //用户职务
    @Column(name="duty")
    private String duty;

}
