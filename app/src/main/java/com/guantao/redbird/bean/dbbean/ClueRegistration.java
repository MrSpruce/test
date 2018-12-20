package com.guantao.redbird.bean.dbbean;

/**
 * 线索注册
 */

import android.widget.EditText;

import com.guantao.redbird.myapplication.R;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

@Table(name = "registration_clue")
public class ClueRegistration {

    //线索编号
    @Column(name="clue_id",isId = true)
    private String clue_id;

    //线索编号_服务器端
    @Column(name="clue_id_server")
    private String clue_id_server;

    //线索名称
    @Column(name="clue_title")
    private String clue_title;

    //线索个数
    @Column(name="clue_number")
    private int clue_number;

    //线索来源
    @Column(name="clue_source")
    private String clue_source;

    //违法线索发生地-县区
    @Column(name ="illegal_address_county")
    private String illegal_address_county;

    //线索地址-村
    @Column(name="illegal_address_village")
    private String illegal_address_village;
    //线索地址-镇
    @Column(name="illegal_address_town")
    private String illegal_address_town;

    //线索详细地址
    @Column(name="illegal_address")
    private String illegal_address;

    //违法地范围
    @Column(name = "clue_geometry")
    private String clue_geometry;

    //线索内容
    @Column(name="clue_content")
    private String clue_content;

    //联系人
    @Column(name="contact")
    private String contact;

    //联系电话
    @Column(name="contact_phone")
    private String contact_phone;

    //联系地址
    @Column(name="contact_address")
    private String contact_address;

    //用户名称
    @Column(name="login_user_name")
    private String login_user_name;

    //用户签名
    @Column(name="operator_sign")
    private String operator_sign;

    //创建时间
    @Column(name="create_time")
    private long  create_time;

    //违法面积
    @Column(name = "illeage_area")
    private double illeage_area;

    //是否提交_基础信息
    @Column(name="is_apply")
    private long  is_apply;

    //是否提交_现场照片
    @Column(name="is_apply_photo")
    private long  is_apply_photo;

    //违法地纬度
    @Column(name="illegal_lat")
    private double  illegal_lat;

    //违法地经度
    @Column(name="illegal_lon")
    private double  illegal_lon;

    //部门编号
    @Column(name="department_id")
    private int  department_id;

    public String getClue_id() {
        return clue_id;
    }

    public void setClue_id(String clue_id) {
        this.clue_id = clue_id;
    }

    public String getClue_id_server() {
        return clue_id_server;
    }

    public void setClue_id_server(String clue_id_server) {
        this.clue_id_server = clue_id_server;
    }

    public String getClue_title() {
        return clue_title;
    }

    public void setClue_title(String clue_title) {
        this.clue_title = clue_title;
    }

    public int getClue_number() {
        return clue_number;
    }

    public void setClue_number(int clue_number) {
        this.clue_number = clue_number;
    }

    public String getClue_source() {
        return clue_source;
    }

    public void setClue_source(String clue_source) {
        this.clue_source = clue_source;
    }

    public String getIllegal_address_village() {
        return illegal_address_village;
    }

    public void setIllegal_address_village(String illegal_address_village) {
        this.illegal_address_village = illegal_address_village;
    }

    public String getIllegal_address_town() {
        return illegal_address_town;
    }

    public void setIllegal_address_town(String illegal_address_town) {
        this.illegal_address_town = illegal_address_town;
    }

    public String getIllegal_address_county() {
        return illegal_address_county;
    }

    public void setIllegal_address_county(String illegal_address_county) {
        this.illegal_address_county = illegal_address_county;
    }

    public String getIllegal_address() {
        return illegal_address;
    }

    public void setIllegal_address(String illegal_address) {
        this.illegal_address = illegal_address;
    }

    public String getClue_geometry() {
        return clue_geometry;
    }

    public void setClue_geometry(String clue_geometry) {
        this.clue_geometry = clue_geometry;
    }

    public String getClue_content() {
        return clue_content;
    }

    public void setClue_content(String clue_content) {
        this.clue_content = clue_content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getLogin_user_name() {
        return login_user_name;
    }

    public void setLogin_user_name(String login_user_name) {
        this.login_user_name = login_user_name;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public double getIlleage_area() {
        return illeage_area;
    }

    public void setIlleage_area(double illeage_area) {
        this.illeage_area = illeage_area;
    }

    public long getIs_apply() {
        return is_apply;
    }

    public void setIs_apply(long is_apply) {
        this.is_apply = is_apply;
    }

    public long getIs_apply_photo() {
        return is_apply_photo;
    }

    public void setIs_apply_photo(long is_apply_photo) {
        this.is_apply_photo = is_apply_photo;
    }

    public double getIllegal_lat() {
        return illegal_lat;
    }

    public void setIllegal_lat(double illegal_lat) {
        this.illegal_lat = illegal_lat;
    }

    public double getIllegal_lon() {
        return illegal_lon;
    }

    public void setIllegal_lon(double illegal_lon) {
        this.illegal_lon = illegal_lon;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getOperator_sign() {
        return operator_sign;
    }

    public void setOperator_sign(String operator_sign) {
        this.operator_sign = operator_sign;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "clue_id='" + clue_id + '\''+
                "clue_id='" + clue_id_server + '\''+
                ", clue_title='" + clue_title + '\'' +
                ", clue_number=" + clue_number  +
                ", is_apply =" + is_apply  +
                ", is_apply =" + is_apply_photo  +
                ", illegal_lat =" + illegal_lat  +
                ", illegal_lon =" + illegal_lon  +
                ", clue_source='" + clue_source + '\'' +
                ", illegal_address_county='" + illegal_address_county + '\'' +
                ", illegal_address_village='" + illegal_address_village + '\'' +
                ", illegal_address_town='" + illegal_address_town + '\'' +
                ", illegal_address='" + illegal_address + '\'' +
                ", clue_content='" + clue_content + '\'' +
                ", contact='" + contact + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                ", contact_address='" + contact_address + '\'' +
                ", login_user_name='" + login_user_name + '\'' +
                ", operator_sign='" + operator_sign + '\'' +
                ", create_time=" + create_time +
                ", illeage_area=" + illeage_area +
                ", department_id=" + department_id +
                '}';
    }

}
