package com.fable.industry.bussiness.service.main;

import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-28 15:33
 * @description 首页行业分类
 */
public interface MainService {
    /**
     * 查询首页行业分类
     */
    List<Map<String, Object>> queryClassify();

    /**
     * 查询行业分类下面的资源名称
     */
    Map<Integer, List<ResciCatalogUEBean>> queryResci();

    /**
     * 首页左侧菜单查询
     */
    List<Map<String, Object>> queryMenuByRoleId(Integer roleId);
}
