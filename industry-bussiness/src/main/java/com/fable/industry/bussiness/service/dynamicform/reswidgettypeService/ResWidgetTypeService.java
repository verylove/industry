package com.fable.industry.bussiness.service.dynamicform.reswidgettypeService;


import com.fable.industry.bussiness.model.dynamicform.ResWidgetTypeBean;

import java.util.List;

public interface ResWidgetTypeService {
    /**
     * id查询
     * @param widgetTypeBean
     * @return
     */
    ResWidgetTypeBean queryWidgetTypeById(ResWidgetTypeBean widgetTypeBean);

    /* 查询所有控件类型 */
    List<ResWidgetTypeBean> queryResWidgetTypes();
}
