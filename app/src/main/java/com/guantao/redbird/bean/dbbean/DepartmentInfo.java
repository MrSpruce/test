package com.guantao.redbird.bean.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 部门信息表
 */
@Table(name = "department_information")
public class DepartmentInfo {

    //部门编号
    @Column(name="department_id",isId=true)
    private int department_id;

    //部门名称
    @Column(name = "department_name")
    private String department_name;

    //部门备注
    @Column(name="department_memo")
    private String department_memo;

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
}
