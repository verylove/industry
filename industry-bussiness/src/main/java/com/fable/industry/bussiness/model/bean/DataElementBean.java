package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import com.fable.industry.bussiness.service.idgenerate.StringGenerator;

import java.sql.Timestamp;

/**
 * @auther jiangmingjun
 * @create 2018/1/30
 * @description 数据元实体
 */
public class DataElementBean {
    @NumberGenerator(name = "DataElementPK")
    private int id ;
    /** 数据元名称 */
    private String dataName;
    /** 数据元英文名称 */
    private String dataEname;
    /** 数据元缩写名 */
    private String dataAname;
    /** 数据元定义 */
    private String dataDefinition;
    /** 数据元编码 */
    @StringGenerator(name = "DataElementSK",format = "SJYBM%X{6}")
    private String dataCode;
    /** 数据元分类ID 对应字典类型（数据元分类）、编码ZDLX000002*/
    private Integer itemId;
    /** 数据类型ID 对应字典类型（数据类型）、编码ZDLX000001 */
    private Integer dataTypeId;
    /** 数据长度 */
    private String dataLength;
    /** 约束条件 */
    private String constraint;
    /** 是否字典 */
    private String isDictionary;
    /** 字典ID */
    private Integer dictionaryId;
    /** 最大出现次数 */
    private String frequencyMax;
    /** 提交单位 */
    private Integer comId;
    /** 提交人 */
    private Integer userId;
    /** 版本号ID 对应字典类型（版本号）、编码ZDLX000003 */
    private Integer versionId;
    /** 版本日期 */
    private Timestamp versionTime;
    /** 修改日期 */
    private Timestamp updateTime;
    /** 启用状态 */
    private String enabledState;
    /** 数据源创建类型 */
    private String establishType;
    /** 备注 */
    private String note;

    /** 字典名称 */
    private String itemName;

    /** 扩展属性 */

    /** 是否显示在列表页 */
    private int isTbaleDisplay;

    /** 是否可查询 */
    private int isQuery;

    /** 提交单位名称 */
    private String comName;

    /** 数据项类型名称 */
    private String widgetName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataEname() {
        return dataEname;
    }

    public void setDataEname(String dataEname) {
        this.dataEname = dataEname;
    }

    public String getDataAname() {
        return dataAname;
    }

    public void setDataAname(String dataAname) {
        this.dataAname = dataAname;
    }

    public String getDataDefinition() {
        return dataDefinition;
    }

    public void setDataDefinition(String dataDefinition) {
        this.dataDefinition = dataDefinition;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getIsDictionary() {
        return isDictionary;
    }

    public void setIsDictionary(String isDictionary) {
        this.isDictionary = isDictionary;
    }

    public Integer getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Integer dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getFrequencyMax() {
        return frequencyMax;
    }

    public void setFrequencyMax(String frequencyMax) {
        this.frequencyMax = frequencyMax;
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

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Timestamp getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(Timestamp versionTime) {
        this.versionTime = versionTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getEnabledState() {
        return enabledState;
    }

    public void setEnabledState(String enabledState) {
        this.enabledState = enabledState;
    }

    public String getEstablishType() {
        return establishType;
    }

    public void setEstablishType(String establishType) {
        this.establishType = establishType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIsTbaleDisplay() {
        return isTbaleDisplay;
    }

    public void setIsTbaleDisplay(int isTbaleDisplay) {
        this.isTbaleDisplay = isTbaleDisplay;
    }

    public int getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(int isQuery) {
        this.isQuery = isQuery;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }
}
