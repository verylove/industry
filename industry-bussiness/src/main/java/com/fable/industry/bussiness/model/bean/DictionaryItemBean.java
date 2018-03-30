package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

/**
 * @auther jiangmingjun
 * @create 2018/1/29
 * @description 字典项实体
 */
public class DictionaryItemBean {
    @NumberGenerator(name = "DictionaryItemPK")
    private int id;
    /** 序号 */
    private Integer index;
    /** 字典项名称 */
    private String itemName;
    /** 字典项描述 */
    private String itemContent;
    /** 所属字典 */
    private Integer classId;
    /** 字典项别名 */
    private String itemAlias;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getItemAlias() {
        return itemAlias;
    }

    public void setItemAlias(String itemAlias) {
        this.itemAlias = itemAlias;
    }
}
