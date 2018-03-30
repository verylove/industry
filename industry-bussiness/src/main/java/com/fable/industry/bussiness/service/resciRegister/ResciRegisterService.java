package com.fable.industry.bussiness.service.resciRegister;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.ResciWidgetBean;
import com.fable.industry.bussiness.model.bean.SysUserBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-01 14:07
 * @description 资源登记
 */
public interface ResciRegisterService {
    /**
     * 根据编目Id查询该资源的控件
     */
    List<ResciWidgetBean> getResciWidgetById(Integer catalogueId);

    /**
     * 查询资源登记列表
     */
    PageResponse<Map<String, String>> findResciRegister(PageRequest<Map<String, String>> pageRequest, HttpServletRequest request);

    /**
     * 新增资源登记
     */
    Map<String, Object> addResciRegister(Map<String, String> map, HttpServletRequest request);

    /**
     * 根据id查看资源登记
     */
    Map<String, String> queryResciRegisterById(Integer id, Integer catalogueId);

    /**
     * 修改资源登记
     */
    Map<String, Object> updateResciRegister(Map<String, String> map);

    /**
     * 检验资源是否可被修改
     */
    Map<String, Object> checkUpdateResciRegister(SysUserBean userInfo,Map<String, Object> map);

    /**
     * 修改资源登记
     */
    Map<String, Object> deleteResciRegister(String id, Integer catalogueId);

    /**
     * 检验资源是否可被删除
     */
    Map<String, Object> checkDeleteResciRegister(SysUserBean userInfo,Map<String, Object> map);

    /**
     * 资源登记树
     */
    List<TreeRsult> queryResciRegisteTree();
}
