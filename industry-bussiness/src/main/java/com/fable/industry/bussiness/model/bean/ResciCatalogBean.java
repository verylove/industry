package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import com.fable.industry.bussiness.service.idgenerate.StringGenerator;

/**
 * @auther jiangmingjun
 * @create 2018/1/25
 * @description 资源目录实体
 */
public class ResciCatalogBean {
    @NumberGenerator(name = "ResciPK")
    private int id;
    /** 目录编码 */
    @StringGenerator(name = "ResciCatalogSK",format = "MLBM%X{6}")
    private String catalogCode;
    /** 目录名称 */
    private String catalogName;
    /** 目录审核单位 */
    private Integer comId;
    /** 目录审核员（具有目录审核权限的） */
    private Integer userId;
    /** 联系电话 */
    private String telephone;
    /** 上级目录 */
    private Integer parentId;
    /** 序号 */
    private Integer index;
    /** 资源域ID */
    private Integer fieldId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
}
