package com.fable.industry.bussiness.mapper.main;

import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-28 15:36
 * @description 行业分类
 */
public interface MainMapper {
    /**
     * 查询首页行业分类
     */
    List<Map<String, Object>> queryClassify();

    /**
     * 查询行业分类下面的资源名称
     */
    List<ResciCatalogUEBean> queryResciById(Integer id);

    /**
     * 首页左侧菜单查询
     */
    List<Map<String, Object>> queryMenuByRoleId(Integer roleId);
}
