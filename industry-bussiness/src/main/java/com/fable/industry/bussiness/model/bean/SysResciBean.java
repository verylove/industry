package com.fable.industry.bussiness.model.bean;

import java.sql.Timestamp;

/**
 * @Auther Wangbaoan
 * @Date 2018-02-01 15:26
 * @Description 资源登记表
 */
public class SysResciBean{
    /** 主键ID */
    private Integer ID;
    /** 所属资源编目 */
    private Integer catalogueId;
    /** 资源创建人 */
    private Integer userId;
    /** 资源创建人所属单位 */
    private Integer comId;
    /** 资源创建时间 */
    private Timestamp createTime;
    /** 资源更新时间 */
    private  Timestamp updateTime;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(Integer catalogueId) {
        this.catalogueId = catalogueId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
