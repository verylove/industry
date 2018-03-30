package com.fable.industry.bussiness.mapper.dynamicform.restypeattr;

import com.fable.industry.bussiness.model.dynamicform.ResCiTypeAttributeBean;
import org.apache.ibatis.annotations.Param;

public interface ResTypeAttrMapper {

    /** 添加类型属性关系 **/
    void insert(ResCiTypeAttributeBean resCiTypeAttributeBean);

    /**
     * 删除关系属性
     * @param resCiTypeAttributeBean
     * @return
     */
    int deleteResCiTypeAttrByAttrId(
            ResCiTypeAttributeBean resCiTypeAttributeBean);

    int getCountsByTypeId(Integer resTypeId);

    /**
     * 获取关系数据
     * @param attrId
     * @return
     */
    ResCiTypeAttributeBean getBeanByAttrId(Integer attrId);

    /**
     * 修改attr的index
     * made by lsy 2015年4月23日 下午1:25:01
     * duyang  20180118 update note:change posotion
     */
    void updateIndex(String attrId, String attrIndex);

    /**
     * 批量删除
     */
    int deleteBatchIds(@Param("citypeIds") StringBuilder citypeIds);

    int queryByCatalogueId(Integer id);

}
