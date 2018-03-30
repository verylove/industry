package com.fable.industry.bussiness.service.resci;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.DataExamineBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ResciCataUEService {

    /**
     * 资源编目树
     */
    List<TreeRsult> catalogingTree();

    /**
     * 资源编目列表
     */
    PageResponse catalogingList(PageRequest<ResciCatalogUEBean> pageRequest, HttpServletRequest request);

    /**
     * 审核编目列表
     */
    PageResponse examineCatalogList(PageRequest<ResciCatalogUEBean> pageRequest, HttpServletRequest request);

    /**
     * 填写编目申请(1,2步)
     */
    ResciCatalogUEBean cataloging(ResciCatalogUEBean bean);

    /**
     * 写编目申请(3步)
     */
    List<Map<String,Object>> catalogingStep3(ResciCatalogUEBean bean);

    /**
     * 保存资源编目(第4步编辑数据项)
     */
    Map<String,Object> catalogingStep4(Map<String,Object> map);

    /**
     * 资源编目最终保存（即第5步保存）
     */
    Map<String,Object> catalogingSave(ResciCatalogUEBean bean);

    /**
     * 创建动态表单
     */
    Map<String,Object> createDynamicDB(ResciCatalogUEBean bean);

    /**
     * 删除动态表单
     */
    Map<String,Object> deleteDynamicDB(ResciCatalogUEBean bean);

    /**
     * 修改资源编目即返回上一步（1,2,3,5步）
     */
    ResciCatalogUEBean catalogingUpdate(ResciCatalogUEBean bean);

    /**
     * 修改资源编目即返回上一步（第4步）
     */
    List<Map<String,Object>> catalogingUpdateStep4(ResciCatalogUEBean bean);

    /**
     * 点击修改资源编目按钮跳到当前修改状态(1,2,3步)
     */
    ResciCatalogUEBean queryResciCataUE(ResciCatalogUEBean bean);

    /**
     * 点击修改资源编目按钮跳到当前修改状态(第4步)
     */
    List<Map<String,Object>> queryDataByCUE(Integer id);

    /**
     * 点击修改资源编目按钮跳到当前修改状态(第5步)
     */
    Map<String,Object> queryCatalogUEAll(ResciCatalogUEBean bean);

    /**
     * 资源编目可删除校验(已发布的不可删)
     */
    Map<String,Object> checkCatalogUE(String ids);

    /**
     * 资源编目删除
     */
    Map<String,Object> deleteCatalogUE(String ids);

    /**
     * 资源编目状态变化
     */
    Map<String,String> stateChange(ResciCatalogUEBean bean);

    /**
     * 资源编目审核
     */
    Map<String,Object> review(DataExamineBean bean,HttpServletRequest request);

    /**
     * 查看发布流程
     */
    Map<String,Object> getReleaseProcess(ResciCatalogUEBean bean);

    /**
     * oid假校验
     */
    Map<String,Object> oidCheck(ResciCatalogUEBean bean);

    /**
     * 获取OID
     */
    Map<String, Object> getoid(ResciCatalogUEBean bean);

    /**
     * 资源编码唯一性校验
     */
    Map<String,Object> resciCodeCheck(String code);

}
