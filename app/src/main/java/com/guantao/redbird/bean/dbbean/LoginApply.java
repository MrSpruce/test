package com.guantao.redbird.bean.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 上报信息表
 */
@Table(name = "login_apply")
public class LoginApply {

    //用户编号
    @Column(name="login_name",isId=true)
    private String login_name;

    //部门负责人姓名
    @Column(name="login_depleader_name")
    private String login_depleader_name;

    //审理线索负责人姓名
    @Column(name="apply_leader_name")
    private String apply_leader_name;



    //审理线索负责人登录编号
    @Column(name="apply_leader_loginid")
    private int apply_leader_loginid;



    //审理线索负责人登录名称
    @Column(name="apply_leader_loginname")
    private String apply_leader_loginname;

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_depleader_name() {
        return login_depleader_name;
    }

    public void setLogin_depleader_name(String login_depleader_name) {
        this.login_depleader_name = login_depleader_name;
    }

    public String getApply_leader_name() {
        return apply_leader_name;
    }

    public void setApply_leader_name(String apply_leader_name) {
        this.apply_leader_name = apply_leader_name;
    }

    public int getApply_leader_loginid() {
        return apply_leader_loginid;
    }

    public void setApply_leader_loginid(int apply_leader_loginid) {
        this.apply_leader_loginid = apply_leader_loginid;
    }


    public String getApply_leader_loginname() {
        return apply_leader_loginname;
    }

    public void setApply_leader_loginname(String apply_leader_loginname) {
        this.apply_leader_loginname = apply_leader_loginname;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "login_name='" + login_name + '\'' +
                "login_depleader_name='" + login_depleader_name + '\'' +
                ", apply_leader_name='" + apply_leader_name + '\'' +
                ", apply_leader_loginid=" + apply_leader_loginid +
                ", apply_leader_loginname='" + apply_leader_loginname + '\'' +
                '}';
    }









}
