package com.fable.industry.bussiness.service.resci;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.ResciCatalogBean;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ResciCatalogService {

    /**
     * 目录列表查询
     */
    PageResponse listCatalog(PageRequest<ResciCatalogBean> pageRequest);

    /**
     * 新增目录
     */
    Map<String,Object> addCatalog(ResciCatalogBean bean);

    /**
     * 修改目录
     */
    Map<String,Object> updateCatalog(ResciCatalogBean bean);

    /**
     * 目录删除校验
     */
    Map<String,Object> deleteCatalogCheck(String ids);

    /**
     * 删除目录
     */
    Map<String,Object> deleteCatalog(String ids);

    /**
     * 查看和修改的数据带入目录
     */
    Map<String,Object> queryCatalogById(Integer id);

    /**
     * 目录树
     */
    List<TreeRsult> catalogTree();

}
