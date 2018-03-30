package com.fable.industry.bussiness.model.bean;

/**
 * @Auther Wangbaoan
 * @Date 2018-02-01 10:54
 * @Description 渲染资源登记页面控件字段
 */
public class ResciWidgetBean {
    /** 资源编目ID(资源编目表中的主键关联rescitypeattribute表中的CatalogueId) */
    private Integer id;
    /** 属性Id */
    private Integer attributeId;
    /** 属性中文名称 */
    private String attributeName;
    /** 字段名称 */
    private String columnName;
    /** 是否显示在列表页 */
    private Integer isTableDisplay;
    /** 是否可删除 */
    private Integer isDeletable;
    /** 控件ID */
    private Integer widgetId;
    /** 校验规则 */
    private Integer validateRule;
    /** 是否必填 */
    private Integer required;
    /** 是否可查询 */
    private Integer isQuery;
    /** 数据元Id */
    private Integer elementId;
    /** 属性位置索引 */
    private Integer attributeIndex;
    /** 数据库表名 */
    private String tableName;
    /** 控件值 */
    private String widgetValue;
    /** 控件名称 */
    private String widgetName;
    /** 控件对应字段类型 */
    private String dataType;
    /** 图标路径 */
    private String widgetIcon;
    /** 下拉框获取路径 */
    private String selectUrl;
    /** 字典项ID */
    private Integer dictionaryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getIsTableDisplay() {
        return isTableDisplay;
    }

    public void setIsTableDisplay(Integer isTableDisplay) {
        this.isTableDisplay = isTableDisplay;
    }

    public Integer getIsDeletable() {
        return isDeletable;
    }

    public void setIsDeletable(Integer isDeletable) {
        this.isDeletable = isDeletable;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public Integer getValidateRule() {
        return validateRule;
    }

    public void setValidateRule(Integer validateRule) {
        this.validateRule = validateRule;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(Integer isQuery) {
        this.isQuery = isQuery;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getAttributeIndex() {
        return attributeIndex;
    }

    public void setAttributeIndex(Integer attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    public String getWidgetValue() {
        return widgetValue;
    }

    public void setWidgetValue(String widgetValue) {
        this.widgetValue = widgetValue;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getWidgetIcon() {
        return widgetIcon;
    }

    public void setWidgetIcon(String widgetIcon) {
        this.widgetIcon = widgetIcon;
    }

    public String getSelectUrl() {
        return selectUrl;
    }

    public void setSelectUrl(String selectUrl) {
        this.selectUrl = selectUrl;
    }

    public Integer getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Integer dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
}
