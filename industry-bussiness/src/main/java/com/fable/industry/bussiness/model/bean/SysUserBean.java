package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import java.sql.Timestamp;

/**
 * @auther jiangmingjun
 * @create 2018/1/25
 * @description 用户实体
 */
public class SysUserBean {

    @NumberGenerator(name = "UserPK")
    private int userId;
    /** 用户名 */
    private String userName;
    /** 登录名称 */
    private String login;
    /** 密码 */
    private String password;
    /** 联系电话 */
    private String telePhone;
    /** 最后一次登录时间 */
    private Timestamp lastLoginTime;
    /** 最后一次登录IP */
    private String lastLoginIP;
    /** 备注 */
    private String note;
    /** 创建者 */
    private Integer creatorId;
    /** 创建时间 */
    private Timestamp createdTime;
    /** 修改者 */
    private Integer updateId;
    /** 修改时间 */
    private Timestamp updatedTime;
    /** 用户是否锁定 */
    private String lockStatus;
    /** 用户锁定时间 */
    private Timestamp lockTime;
    /** 公司ID */
    private Integer comId;
    /** 分片键 */
    private Integer disTribution;
    /** 是否是管理员 */
    private Integer isAdmin;
    /** 办公电话 */
    private String officePhone;
    /** 邮箱地址 */
    private String email;

    //额外添加需要显示的字段
    /** 角色名称 */
    private String roleName;
    /** 角色ID */
    private Integer roleId;
    /** 单位信息 */
    private String comName;
    /** 验证码 */
    private String code;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Timestamp getLockTime() {
        return lockTime;
    }

    public void setLockTime(Timestamp lockTime) {
        this.lockTime = lockTime;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Integer getDisTribution() {
        return disTribution;
    }

    public void setDisTribution(Integer disTribution) {
        this.disTribution = disTribution;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
