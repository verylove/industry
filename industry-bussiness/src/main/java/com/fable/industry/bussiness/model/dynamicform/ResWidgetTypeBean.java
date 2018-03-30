package com.fable.industry.bussiness.model.dynamicform;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import org.apache.ibatis.type.Alias;

/**
 * 
 * @TABLE_NAME: ResWidgetType
 * @Description:
 * @author: 武林
 * @Create at: Mon Mar 24 10:49:19 CST 2014
 * switch mybatis  update bu duyang 20180118
 */
@Alias("resWidgetType")
public class ResWidgetTypeBean extends Entity {

	@NumberGenerator(name = "WidgetId")
	private int widgetId;

	private String widgetValue;

	private String widgetName;

	private String dataType;

	public ResWidgetTypeBean() {
	}

	public ResWidgetTypeBean(int widgetId) {
		super();
		this.widgetId = widgetId;
	}

	public int getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
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

}
