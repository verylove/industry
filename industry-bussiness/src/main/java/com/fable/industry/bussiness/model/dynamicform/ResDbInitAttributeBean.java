package com.fable.industry.bussiness.model.dynamicform;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

/**
 * 
 * @TABLE_NAME: ResDbInitAttribute
 * @Description:
 * @author: 武林
 * @Create at: Fri Mar 28 10:00:47 CST 2014
 * @author duyang  20180119 update
 */
public class ResDbInitAttributeBean {

	@NumberGenerator(name = "id")
	private int id;

	private int attributeId;

	private String labelText;

	private int value;

	public ResDbInitAttributeBean() {
		super();
	}

	public ResDbInitAttributeBean(int attributeId, String labelText, int value) {
		super();
		this.attributeId = attributeId;
		this.labelText = labelText;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public String getlabelText() {
		return labelText;
	}

	public void setlabelText(String labelText) {
		this.labelText = labelText;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
