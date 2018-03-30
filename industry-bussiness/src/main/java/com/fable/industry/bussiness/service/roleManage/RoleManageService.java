package com.fable.industry.bussiness.service.roleManage;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.SysMenuBean;
import com.fable.industry.bussiness.model.bean.SysRoleBean;

import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-30 11:21
 * @Description 角色管理
 */
public interface RoleManageService {
    /**
     * 角色列表查询
     */
    PageResponse<SysRoleBean> findRoleList(PageRequest<SysRoleBean> pageRequest);

    /**
     * 新增角色
     */
    Map<String, Object> addRole(SysRoleBean sysRoleBean);

    /**
     * 修改角色
     */
    Map<String, Object> updateRole(SysRoleBean sysRoleBean);

    /**
     * 查看角色
     */
    SysRoleBean queryRoleById(Integer roleId);

    /**
     * 删除角色
     */
    Map<String, Object> deleteRole(String roleIds);

    /**
     * 角色是否可删除校验
     */
    Map<String, Object> checkDeleteRole(String roleIds);

    /**
     * 角色唯一性校验
     */
    Map<String, Object> checkRole(String roleName);

    /**
     * 角色授权
     */
    Map<String, Object> authorization(Map<String, String> map);

    /**
     * 菜单树的查询
     */
    List<TreeRsult> queryMenu(String roleId);

    /**
     * 角色下拉查询接口
     */
    List<SysRoleBean> queryRole();
}
