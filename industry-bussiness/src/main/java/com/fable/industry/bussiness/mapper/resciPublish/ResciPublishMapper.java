package com.fable.industry.bussiness.mapper.resciPublish;

import com.fable.industry.bussiness.model.bean.DataElementBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.model.bean.ResciWidgetBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-05 11:27
 * @description 资源发布
 */
public interface ResciPublishMapper {
    /**
     * 资源发布列表查询
     */
    List<ResciCatalogUEBean> listResciCatalogue(ResciCatalogUEBean resciCatalogueBean);

    /**
     * 根据资源编目Id查询资源
     */
    ResciCatalogUEBean queryResciCatalogueByCatalogueId(Integer id);

    /**
     * 根据资源编目Id查询数据项信息
     */
    List<DataElementBean> queryDataElementByCatalogueId(@Param("catalogueId") Integer catalogueId);

    /**
     * 查询OID编码下拉框
     */
    List<Map<String, Object>> queryOID();

    /**
     * 根据资源编目Id查询资源登记时间
     */
    String queryCreateTime(@Param("catalogId") Integer catalogId);
}
