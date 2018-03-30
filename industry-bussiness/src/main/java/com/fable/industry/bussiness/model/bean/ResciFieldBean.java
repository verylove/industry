package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

/**
 * @auther jiangmingjun
 * @create 2018/1/25
 * @description 资源域实体
 */
public class ResciFieldBean {
    @NumberGenerator(name = "ResciPK")
    private int id;
    /** 序号 */
    private Integer index;
    /** 域名称 */
    private String fieldName;
    /** 域管理员 */
    private Integer userId;
    /** 上级域 */
    private Integer pid;
    /** 单位 */
    private Integer comId;
    /** 联系电话 */
    private String telephone;

    /** 扩展属性 */

    /** 域管理员名称 */
    private String userName;

    /** 上级域名称 */
    private String pidName;

    /** 单位名称 */
    private String comName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }
}
