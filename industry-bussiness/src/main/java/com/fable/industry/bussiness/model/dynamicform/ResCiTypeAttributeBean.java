package com.fable.industry.bussiness.model.dynamicform;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import org.apache.ibatis.type.Alias;

/**
 * 
 * @TABLE_NAME: ResCiTypeAttribute
 * @Description:
 * @author: 武林
 * @Create at: Mon Mar 24 10:49:19 CST 2014
 * update bu duyang 2018 01 18 switch mybatis
 */
@Alias("resCiTypeAttributeBean")
public class ResCiTypeAttributeBean extends Entity {

	@NumberGenerator(name = "id", begin=10000)
	private int id;
	
	private int catalogueId;

	private int attributeId;

	private int attributeIndex;

	private String tableName;

	public ResCiTypeAttributeBean() {
		super();
	}

	public ResCiTypeAttributeBean(int attributeId) {
		super();
		this.attributeId = attributeId;
	}

	public ResCiTypeAttributeBean(int catalogueId, int attributeId,
			int attributeIndex, String tableName) {
		super();
		this.catalogueId = catalogueId;
		this.attributeId = attributeId;
		this.attributeIndex = attributeIndex;
		this.tableName = tableName;
	}

	public int getCatalogueId() {
		return catalogueId;
	}

	public void setCatalogueId(int catalogueId) {
		this.catalogueId = catalogueId;
	}

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public int getAttributeIndex() {
		return attributeIndex;
	}

	public void setAttributeIndex(int attributeIndex) {
		this.attributeIndex = attributeIndex;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
