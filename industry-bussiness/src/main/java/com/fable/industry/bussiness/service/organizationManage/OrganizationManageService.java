package com.fable.industry.bussiness.service.organizationManage;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.SysOrganizationBean;

import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-30 18:27
 * @Description 单位信息管理
 */
public interface OrganizationManageService {
    /**
     * 单位信息列表查询
     */
    PageResponse<SysOrganizationBean> findOrganization(PageRequest<SysOrganizationBean> request);

    /**
     * 单位信息树查询
     */
    List<TreeRsult> findOrganizationTree();

    /**
     * 新增单位
     */
    Map<String, Object> addOrganization(SysOrganizationBean sysOrganizationBean);

    /**
     * 修改单位
     */
    Map<String, Object> updateOrganization(SysOrganizationBean sysOrganizationBean);

    /**
     * 删除单位
     */
    Map<String, Object> deleteOrganization(String comId);

    /**
     * 单位是否可删除校验
     */
    Map<String, Object> checkdeleteOrganization(String comId);

    /**
     * 单位唯一性校验
     */
    Map<String, Object> checkOrganization(Map<String, String> map);

    /**
     * 查看单位
     */
    SysOrganizationBean queryOrganizationById(Integer comId);

    /**
     * 单位下拉列表查询
     */
    List<TreeRsult> findOrganizationComboBox();

    List<SysOrganizationBean> queryAllCom();
}
