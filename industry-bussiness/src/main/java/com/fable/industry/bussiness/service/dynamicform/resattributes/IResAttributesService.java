package com.fable.industry.bussiness.service.dynamicform.resattributes;


import com.fable.industry.bussiness.model.dynamicform.ResAttributesBean;
import com.fable.industry.bussiness.model.dynamicform.ResDbInitAttributeBean;

import java.util.List;

/**
 * 资源特征属性管理
 * 
 * @author wul
 * 
 */
public interface IResAttributesService {
	boolean delResDbInitAttr(ResDbInitAttributeBean resDbInitAttributeBean);

	/** 列表数据 **/
	List<ResAttributesBean> queryResAttributes(
            ResAttributesBean resAttributesBean);

	List<ResDbInitAttributeBean> queryResDbInitByAttrId(Integer attributeId);

	int chkResAttr(ResAttributesBean resAttributesBean);

	/** 获取总记录数 **/
	int getTotalCountByGroup(ResAttributesBean resAttributesBean);

	/** 添加特征属性 **/
	ResAttributesBean addResAttributes(ResAttributesBean resAttributesBean);

	/** 修改特征属性 **/
	boolean updateResAttributes(ResAttributesBean resAttributesBean);

	/** 删除特征属性 **/
	String delResAttributes(ResAttributesBean resAttributesBean);
}
