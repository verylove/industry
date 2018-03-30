package com.fable.industry.bussiness.service.dynamicform.resattributes;

import com.fable.industry.api.common.sysdict.SystemFinalValue;
import com.fable.industry.bussiness.common.dynamicdb.api.IDynamicDB;
import com.fable.industry.bussiness.mapper.dynamicform.resattributes.ResAttributesMapper;
import com.fable.industry.bussiness.mapper.dynamicform.restypeattr.ResTypeAttrMapper;
import com.fable.industry.bussiness.model.dynamicform.ResAttributesBean;
import com.fable.industry.bussiness.model.dynamicform.ResCiTypeAttributeBean;
import com.fable.industry.bussiness.model.dynamicform.ResDbInitAttributeBean;
import com.fable.industry.bussiness.service.dynamicform.reswidgettypeService.ResWidgetTypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * 资源属性Service
 * 
 * @author wul
 * 
 */
@Service("resAttributesService")
@Transactional
public class ResAttributesServiceImpl implements IResAttributesService {
	
	private static final String DEFAULT_ATTRIBUTE_DATA_TYPE = "String";
	
	@Autowired
	private IDynamicDB dynamicDb;// 动态操作表dao

	@Autowired
	public ResAttributesMapper resAttributesMapper;

	@Autowired
	private ResTypeAttrMapper resTypeAttrMapper;

	@Autowired
	private ResWidgetTypeService resWidgetTypeService;
	

	/**
	 * 获取总记录数
	 * 
	 * @author wul
	 */
	public int getTotalCountByGroup(ResAttributesBean resAttributesBean) {
		//return resAttributesDao.getTotalCount(resAttributesBean);
		return 0;
	}

	public int chkResAttr(ResAttributesBean resAttributesBean) {
		resAttributesBean.setColumnName(SystemFinalValue.COL_PREFIX+resAttributesBean.getColumnName());
		//return resAttributesDao.chkAttrName(resAttributesBean);
		return  resAttributesMapper.chkAttrName(resAttributesBean);
	}

	/**
	 * 列表数据
	 * @author duyang  20180118
	 */
	public List<ResAttributesBean> queryResAttributes(
			ResAttributesBean resAttributesBean) {
		List<ResAttributesBean> resAttrLst = resAttributesMapper
				.queryResAttributes(resAttributesBean);
		for (ResAttributesBean resAttrBean : resAttrLst) {
			/*if (5 == resAttrBean.getWidgetId()) {
				List<ResDbInitAttributeBean> resDbAttrLst = resDbInitAttributeDao
						.getRedDbInitByAttributeId(resAttrBean.getAttributeId());
				resAttrBean.setResDbInitLst(resDbAttrLst);
			}*/
		}
		return resAttributesMapper.queryResAttributes(resAttributesBean);
	}

	private String setTableName(String tableName) {
		String firstUpper;
		if (!StringUtils.isEmpty(tableName)) {
			firstUpper = tableName.toUpperCase().charAt(0)
					+ tableName.substring(1);
			return SystemFinalValue.CMDB_TABLENAME_PREFIX + firstUpper;
		}
		return "";
	}


	/**
	 * 新增资源属性
	 * 
	 * @author wul
	 */
	public ResAttributesBean addResAttributes(
			ResAttributesBean resAttributesBean) {
			//设置资源可以删除(默认可以删除)
			String tblName = this.setTableName(resAttributesBean.getTblName());
			resAttributesBean.setColumnName(SystemFinalValue.COL_PREFIX+resAttributesBean.getColumnName());
			resAttributesMapper.insertResAttributes(resAttributesBean);
			ResCiTypeAttributeBean resCiTypeAttributeBean = new ResCiTypeAttributeBean(
					resAttributesBean.getCatalogueId(),
					resAttributesBean.getAttributeId(), resAttributesBean.getIndex(), tblName);
			resTypeAttrMapper.insert(resCiTypeAttributeBean);
			/*//获取属性字段类型
			ResWidgetTypeBean widgetTypeBean = new ResWidgetTypeBean(resAttributesBean.getWidgetId());
			widgetTypeBean = resWidgetTypeService.queryWidgetTypeById(widgetTypeBean);
			dynamicDb.addColumn(tblName, resAttributesBean.getColumnName(),
					null == widgetTypeBean ? DEFAULT_ATTRIBUTE_DATA_TYPE : widgetTypeBean.getDataType());*/
		return resAttributesBean;
	}

	/**
	 * 修改特征属性
	 * 
	 * @author wul
	 */
	public boolean updateResAttributes(ResAttributesBean resAttributesBean) {
		resAttributesBean.setColumnName(SystemFinalValue.COL_PREFIX+resAttributesBean.getColumnName());
		/*if (5 == resAttributesBean.getWidgetId()) {
			String[] lblTextAttr = resAttributesBean.getLabelValue().split(
					",");
			this.resDbInitAttributeDao.deleteByAttributeId(resAttributesBean.getAttributeId());;
			for (String lblText : lblTextAttr) {
				ResDbInitAttributeBean resDbInitAttributeBean = new ResDbInitAttributeBean(
						resAttributesBean.getAttributeId(), lblText, 0);
				resDbInitAttributeDao.insert(resDbInitAttributeBean);
			}
		}*/
		resAttributesMapper.update(resAttributesBean);
		return true;
	}

	public boolean delResDbInitAttr(
			ResDbInitAttributeBean resDbInitAttributeBean) {
		/*return resDbInitAttributeDao.delresDbAttrByCond(resDbInitAttributeBean) == 0 ? true
				: false;*/
		return true;
	}

	/**
	 * 删除特征属性
	 * 
	 * @author duyang  20180118 update
	 */
	public String delResAttributes(ResAttributesBean resAttributesBean) {
		int isComp = resAttributesBean.getIsComp();
		resAttributesBean = resAttributesMapper.queryResAttributes(resAttributesBean).get(0);
		if (resAttributesBean.getIsDeletable() == SystemFinalValue.DELETE_UP_SCOPE) {
			return "init";
		}
		/*ResCiTypeAttributeBean resTypeAttr =resCiTypeAttributeDao.getBeanByAttrId(resAttributesBean.getAttributeId());
		ResDbInitAttributeBean resDbInitAttributeBean = new ResDbInitAttributeBean();
		resDbInitAttributeBean.setAttributeId(resAttributesBean
				.getAttributeId());
		ResCiTypeAttributeBean resCiTypeAttributeBean = new ResCiTypeAttributeBean(
				resAttributesBean.getAttributeId());

		resCiTypeAttributeDao
				.deleteResCiTypeAttrByAttrId(resCiTypeAttributeBean);
		int count = resCiTypeAttributeDao.getCountsByTypeId(resTypeAttr.getTypeId());
		resDbInitAttributeDao.delresDbAttrByCond(resDbInitAttributeBean);
		resAttributesMapper.deleteById(resAttributesBean.getAttributeId());
		dynamicDb.removeColumn(resTypeAttr.getTableName(), resAttributesBean.getColumnName(), false);
		
		if(0 == count && 1 == isComp){
			resAttributesMapper.delByTblName(resTypeAttr.getTableName());
		}*/
		return "true";
	}

	/**
	 * 删除特征属性
	 * 
	 * @author wul
	 */
	public List<ResDbInitAttributeBean> queryResDbInitByAttrId(
			Integer attributeId) {
		//return resDbInitAttributeDao.getRedDbInitByAttributeId(attributeId);
		return null;
	}
	
}
