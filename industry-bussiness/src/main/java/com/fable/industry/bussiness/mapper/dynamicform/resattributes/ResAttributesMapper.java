package com.fable.industry.bussiness.mapper.dynamicform.resattributes;


import com.fable.industry.bussiness.model.dynamicform.ResAttributesBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResAttributesMapper {

    /** 添加特征属性 **/
    void insertResAttributes(ResAttributesBean resAttributesBean);

    void delByTblName(String tableName);

    List<ResAttributesBean> queryResAttributes(
            ResAttributesBean resAttributesBean);

    int chkAttrName(ResAttributesBean resAttributesBean);

    void update(ResAttributesBean resAttributesBean);

    void deleteById(int attributeId);

    int deleteBatchIds(@Param("attrIds")StringBuilder attrIds);

    int getByDataElementId(@Param("ids") String ids);

    /** 分页查询资源属性 **/
    /*List<ResAttributesBean> queryByPage(ResAttributesBean resAttributesBean,
                                        FlexiGridPageInfo flexiGridPageInfo);*/

    /** 获取资源属性总记录数 **/
    //int getTotalCount(ResAttributesBean resAttributesBean);

}
