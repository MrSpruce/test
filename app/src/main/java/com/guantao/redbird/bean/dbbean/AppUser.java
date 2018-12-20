package com.guantao.redbird.bean.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "app_user")
public class AppUser {
    @Column(name="id",isId=true,autoGen=true)
    private int id;

    @Column(name="user_id")
    private String user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name="user_phone")
    private String user_phone;

    @Column(name="remark")
    private String remark;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
