package com.fable.industry.bussiness.mapper.roleManage;

import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.SysMenuBean;
import com.fable.industry.bussiness.model.bean.SysRoleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-30 11:24
 * @Description 角色管理
 */
public interface RoleManageMapper {
    /**
     * 查询角色列表
     */
    List<SysRoleBean> findRoleList(SysRoleBean sysRoleBean);

    /**
     * 新增角色
     */
    int addRole(SysRoleBean sysRoleBean);

    /**
     * 修改角色
     */
    int updateRole(SysRoleBean sysRoleBean);

    /**
     * 查看角色
     */
    SysRoleBean queryRoleById(Integer roleId);

    /**
     * 删除角色
     */
    int deleteRole(@Param("roleIds") List<String> roleIds);

    /**
     * 删除角色菜单关系
     */
    int deleteMenuRole(@Param("roleIds") List<String> roleIds);

    /**
     * 角色是否可删除校验
     */
    int checkDeleteRole(@Param("roleIds") List<String> roleIds);

    /**
     * 角色唯一性校验
     */
    int checkRole(String roleName);

    /**
     * 角色授权
     */
    int authorization(@Param("roleId") String roleId, @Param("menuId") String menuId);

    /**
     * 菜单树的查询
     */
    List<TreeRsult> queryMenu();

    /**
     * 查询当前角色下的菜单ID
     */
    List<Integer> queryMenuById(String roleId);

    /**
     * 角色下拉查询接口
     */
    List<SysRoleBean> queryRole();
}
