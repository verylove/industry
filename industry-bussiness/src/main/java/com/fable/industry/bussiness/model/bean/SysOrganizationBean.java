package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

/**
 * @auther jiangmingjun
 * @create 2018/1/25
 */
public class SysOrganizationBean {
    @NumberGenerator(name = "CompanyPK")
    private int comId;
    /** 组织编号 */
    private String comCode;
    /** 组织名称 */
    private String comName;
    /** 上级组织 */
    private Integer pid;
    /** 上级组织名称 */
    private String pidName;
    /** 是否第三方供应 */
    private String isSupplier;
    /** 联系人（供应商） */
    private String contact;
    /** 联系人电话（供应商） */
    private String contactPhone;
    /** 描述 */
    private String note;
    /** 地址 */
    private String address;
    /** 邮箱地址 */
    private String email;
    /** URL地址 */
    private String url;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName;
    }

    public String getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(String isSupplier) {
        this.isSupplier = isSupplier;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
