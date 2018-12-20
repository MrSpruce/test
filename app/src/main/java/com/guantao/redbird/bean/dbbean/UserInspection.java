package com.guantao.redbird.bean.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="user_inspection")
public class UserInspection {

    //
    @Column(name="num",isId=true,autoGen=true)
    private int num;

    //用户编号-工号
    @Column(name="user_id")
    private String user_id;

    //服务端loginid
    @Column(name="loginId_server")
    private int loginId_server;
    //部门编号
    @Column(name="deparmentid")
    private int deparmentid;

    @Column(name="county")
    private String county;
    @Column(name="township")
    private String township;
    @Column(name="village")
    private String village;
    @Column(name="detailaddress")
    private String detailaddress;

    //巡查时间
    @Column(name="Inspect_time")
    private long  Inspect_time;

    //纬度
    @Column(name="lat")
    private double  lat;

    //经度
    @Column(name="lon")
    private double  lon;

    //巡查状态
    @Column(name="status")
    private String status;

    @Column(name="is_apply")
    private int is_apply;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getLoginId_server() {
        return loginId_server;
    }

    public void setLoginId_server(int loginId_server) {
        this.loginId_server = loginId_server;
    }

    public int getDeparmentid() {
        return deparmentid;
    }

    public void setDeparmentid(int deparmentid) {
        this.deparmentid = deparmentid;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDetailaddress() {
        return detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }

    public long getInspect_time() {
        return Inspect_time;
    }

    public void setInspect_time(long inspect_time) {
        Inspect_time = inspect_time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIs_apply() {
        return is_apply;
    }

    public void setIs_apply(int is_apply) {
        this.is_apply = is_apply;
    }
}
