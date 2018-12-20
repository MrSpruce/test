package com.guantao.redbird.bean.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "login")
public class Login {

    //用户登录名
    @Column(name="login_name",isId = true)
    private String login_name;
    //用户姓名
    @Column(name="user_name")
    private String user_name;
    //用户密码
    @Column(name="login_pwd")
    private String login_pwd;
    //用户登录编号-服务器端注册后生成
    @Column(name="login_id_server")
    private int login_id_server;
    //用户部门编号
    @Column(name="login_department_id")
    private int login_department_id;

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }

    public int getLogin_id_server() {
        return login_id_server;
    }

    public void setLogin_id_server(int login_id_server) {
        this.login_id_server = login_id_server;
    }

    public int getLogin_department_id() {
        return login_department_id;
    }

    public void setLogin_department_id(int login_department_id) {
        this.login_department_id = login_department_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    @Override
    public String toString() {
        return "Parent{" +
                "login_id_server=" + login_id_server +
                "login_department_id=" + login_department_id +
                ", login_name='" + login_name + '\'' +
                ", login_pwd='" + login_pwd + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }


}
