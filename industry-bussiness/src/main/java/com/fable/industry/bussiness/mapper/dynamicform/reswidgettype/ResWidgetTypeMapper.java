package com.fable.industry.bussiness.mapper.dynamicform.reswidgettype;


import com.fable.industry.bussiness.model.dynamicform.ResWidgetTypeBean;

import java.util.List;

public interface ResWidgetTypeMapper {

    /* 查询所有控件类型 */
    List<ResWidgetTypeBean> queryWidgetType(ResWidgetTypeBean widgetTypeBean);

}
