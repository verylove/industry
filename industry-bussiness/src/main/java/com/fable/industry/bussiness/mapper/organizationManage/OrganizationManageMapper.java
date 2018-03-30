package com.fable.industry.bussiness.mapper.organizationManage;

import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.SysOrganizationBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-30 18:29
 * @Description 单位信息管理
 */
public interface OrganizationManageMapper {
    /**
     * 单位信息列表查询
     */
    List<SysOrganizationBean> findOrganization(SysOrganizationBean sysOrganizationBean);

    /**
     * 单位信息树查询
     */
    List<TreeRsult> findOrganizationTree();

    /**
     * 新增单位
     */
    int addOrganization(SysOrganizationBean sysOrganizationBean);

    /**
     * 修改单位
     */
    int updateOrganization(SysOrganizationBean sysOrganizationBean);

    /**
     * 删除单位
     */
    int deleteOrganization(@Param("comIds") List<String> list);

    /**
     * 单位是否可删除校验(是否绑定用户)
     */
    int checkdeleteOrganization(@Param("comIds") List<String> list);

    /**
     * 单位是否可删除校验(是否有子单位)
     */
    int checkdeleteOrganize(@Param("comIds") List<String> list);

    /**
     * 单位唯一性校验
     */
    int checkOrganization(Map<String, String> map);

    /**
     * 查看单位
     */
    SysOrganizationBean queryOrganizationById(Integer comId);

    /**
     * 单位信息下拉列表查询
     */
    List<TreeRsult> findOrganizationComboBox();

    List<SysOrganizationBean> queryAllCom();

}
