package com.fable.industry.bussiness.model.dynamicform;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 
 * @TABLE_NAME: ResAttributes
 * @Description:
 * @author: 武林
 * @Create at: Mon Mar 24 10:49:19 CST 2014
 * update bu duyang 2018 01 18 switch mybatis
 */
@Alias("resAttributesBean")
public class ResAttributesBean extends
		Entity {

	@NumberGenerator(name = "attributeId")
	private int attributeId;

	private String attributeName;

	private String columnName;

	private int isRecordChange;

	private int isTableDisplay;

	private int isDeletable;

	private int widgetId;

	private int dataInitValue;
	
	private String dataInitCondition;

	private Integer validateRule;
	
	private Byte required;

	private int isQuery;

	private int elementId;

	private int isAttributes;
	
	private ResWidgetTypeBean resWidgetType;

	private int pageControl;
	private String labelValue;
	private String tblName;
	private String tblLabel;
	private String tblValue;
	private int resTypeId;
	private String pageName;
	private List<ResDbInitAttributeBean> resDbInitLst;
	
	private String isAdd;
	private int isComp;

	private int catalogueId;

	private int index;

	public int getCatalogueId() {
		return catalogueId;
	}

	public void setCatalogueId(int catalogueId) {
		this.catalogueId = catalogueId;
	}

	public int getIsComp() {
		return isComp;
	}
	public void setIsComp(int isComp) {
		this.isComp = isComp;
	}
	public String getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}
	public ResAttributesBean(int attributeId) {
		super();
		this.attributeId = attributeId;
	}
	public List<ResDbInitAttributeBean> getResDbInitLst() {
		return resDbInitLst;
	}

	public void setResDbInitLst(List<ResDbInitAttributeBean> resDbInitLst) {
		this.resDbInitLst = resDbInitLst;
	}

	public int getDataInitValue() {
		return dataInitValue;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public void setDataInitValue(int dataInitValue) {
		this.dataInitValue = dataInitValue;
	}

	public int getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
	}

	public int getResTypeId() {
		return resTypeId;
	}

	public void setResTypeId(int resTypeId) {
		this.resTypeId = resTypeId;
	}

	public ResAttributesBean() {
		super();
	}

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
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

	public int getIsRecordChange() {
		return isRecordChange;
	}

	public void setIsRecordChange(int isRecordChange) {
		this.isRecordChange = isRecordChange;
	}

	public int getIsTableDisplay() {
		return isTableDisplay;
	}

	public void setIsTableDisplay(int isTableDisplay) {
		this.isTableDisplay = isTableDisplay;
	}

	public int getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(int isDeletable) {
		this.isDeletable = isDeletable;
	}

	public ResWidgetTypeBean getResWidgetType() {
		return resWidgetType;
	}

	public void setResWidgetType(ResWidgetTypeBean resWidgetType) {
		this.resWidgetType = resWidgetType;
	}

	public int getPageControl() {
		return pageControl;
	}

	public void setPageControl(int pageControl) {
		this.pageControl = pageControl;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public String getTblLabel() {
		return tblLabel;
	}

	public void setTblLabel(String tblLabel) {
		this.tblLabel = tblLabel;
	}

	public String getTblValue() {
		return tblValue;
	}

	public void setTblValue(String tblValue) {
		this.tblValue = tblValue;
	}

	public String getDataInitCondition() {
		return dataInitCondition;
	}

	public void setDataInitCondition(String dataInitCondition) {
		this.dataInitCondition = dataInitCondition;
	}

	public Integer getValidateRule() {
		return validateRule;
	}

	public void setValidateRule(Integer validateRule) {
		this.validateRule = validateRule;
	}

	public Byte getRequired() {
		return required;
	}

	public void setRequired(Byte required) {
		this.required = required;
	}

	public int getIsAttributes() {
		return isAttributes;
	}

	public void setIsAttributes(int isAttributes) {
		this.isAttributes = isAttributes;
	}

	public int getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(int isQuery) {
		this.isQuery = isQuery;
	}

	public int getElementId() {
		return elementId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
