package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

/**
 * @auther jiangmingjun
 * @create 2018/1/25
 * @description 角色信息实体
 */
public class SysRoleBean {
    @NumberGenerator(name = "RolePK")
    private int roleId;
    /** 角色名称 */
    private String roleName;
    /** 备注 */
    private String note;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
