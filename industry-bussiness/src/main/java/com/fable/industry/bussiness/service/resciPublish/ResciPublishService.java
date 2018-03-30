package com.fable.industry.bussiness.service.resciPublish;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.DataElementBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.model.bean.SysUserBean;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-05 11:25
 * @description 资源发布
 */
public interface ResciPublishService {
    /**
     * 资源发布列表查询
     */
    PageResponse<ResciCatalogUEBean> listResciCatalogue(PageRequest<ResciCatalogUEBean> request);

    /**
     * 根据资源编目Id查询资源
     */
    ResciCatalogUEBean queryResciCatalogueByCatalogueId(Integer id);

    /**
     * 根据资源编目Id查询数据项信息
     */
    List<DataElementBean> queryDataElementByCatalogueId(Integer id);

    /**
     * 查询OID编码下拉框
     */
    List<Map<String, Object>> queryOID();
}
