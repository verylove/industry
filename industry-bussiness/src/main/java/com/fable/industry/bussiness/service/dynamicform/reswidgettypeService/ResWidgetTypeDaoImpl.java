package com.fable.industry.bussiness.service.dynamicform.reswidgettypeService;

import com.fable.industry.bussiness.mapper.dynamicform.reswidgettype.ResWidgetTypeMapper;
import com.fable.industry.bussiness.model.dynamicform.ResWidgetTypeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 资产类型处理Dao实体类
 * 
 * @author wul
 */
@Repository
@Transactional
public class ResWidgetTypeDaoImpl implements ResWidgetTypeService {

	@Autowired
	private ResWidgetTypeMapper resWidgetTypeMapper;

	/**
	 * 查询控件通过id
	 * @param widgetTypeBean
	 * @return
	 * @author duyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResWidgetTypeBean queryWidgetTypeById(ResWidgetTypeBean widgetTypeBean) {
		List<ResWidgetTypeBean> resWidgetTypeBeans = resWidgetTypeMapper.queryWidgetType(new ResWidgetTypeBean());
		if (!CollectionUtils.isEmpty(resWidgetTypeBeans)) {
			widgetTypeBean = resWidgetTypeBeans.get(0);
		}
		return  widgetTypeBean;
	}

	/**
	 * 查询所有控件
	 * 
	 * @author duyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResWidgetTypeBean> queryResWidgetTypes() {
		return  resWidgetTypeMapper.queryWidgetType(new ResWidgetTypeBean());
	}
}
