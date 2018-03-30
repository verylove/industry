package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

import java.sql.Timestamp;

/**
 * @auther jiangmingjun
 * @create 2018/1/25
 * @description 菜单实体
 */
public class SysMenuBean {

    @NumberGenerator(name = "MenuPK")
    private int menuId;
    /** 父菜单ID */
    private Integer pid;
    /** 名称 */
    private String name;
    /** URL */
    private String url;
    /** 标识 */
    private String code;
    /** 图表 */
    private String icon;
    /** 排序 */
    private Integer sortOrder;
    /** 菜单类型 */
    private String type;
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

    //额外参数
    /** 菜单是否被选中*/
    private String checked;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
