package com.fable.industry.bussiness.service.roleManage.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.mapper.roleManage.RoleManageMapper;
import com.fable.industry.bussiness.model.bean.SysMenuBean;
import com.fable.industry.bussiness.model.bean.SysRoleBean;
import com.fable.industry.bussiness.service.roleManage.RoleManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-30 11:22
 */
@Service
@Transactional
public class RoleManageServiceImpl implements RoleManageService {

    @Autowired
    private RoleManageMapper roleManageMapper;

    @Override
    public PageResponse<SysRoleBean> findRoleList(PageRequest<SysRoleBean> pageRequest) {
        SysRoleBean sysRoleBean = pageRequest.getParam();
        Page<SysRoleBean> data = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        roleManageMapper.findRoleList(sysRoleBean);
        return PageResponse.wrap(data, true, "0", "success");
    }

    @Override
    public Map<String, Object> addRole(SysRoleBean sysRoleBean) {
        int count = roleManageMapper.addRole(sysRoleBean);
        if (count > 0) {
            return Result.success("插入角色信息成功！");
        }
        return Result.fail("插入角色信息失败！");
    }

    @Override
    public Map<String, Object> updateRole(SysRoleBean sysRoleBean) {
        int count = roleManageMapper.updateRole(sysRoleBean);
        if (count > 0) {
            return Result.success("修改角色信息成功！");
        }
        return Result.fail("修改角色信息失败！");
    }

    @Override
    public SysRoleBean queryRoleById(Integer roleId) {
        return roleManageMapper.queryRoleById(roleId);
    }

    @Override
    public Map<String, Object> deleteRole(String roleIds) {
        String[] ids = roleIds.split(",");
        List<String> list = Arrays.asList(ids);
        int count = roleManageMapper.deleteRole(list);
        int menuRole = roleManageMapper.deleteMenuRole(list);
        if (count > 0 && menuRole > 0) {
            return Result.success("删除角色信息成功！");
        }
        return Result.fail("删除角色信息失败！");
    }

    @Override
    public Map<String, Object> checkDeleteRole(String roleIds) {
        String[] ids = roleIds.split(",");
        List<String> list = Arrays.asList(ids);
        int count = roleManageMapper.checkDeleteRole(list);
        if (count > 0) {
            return Result.fail("角色已经被使用，无法删除，请重新选择数据！");
        }
        return Result.success("角色未被使用！");
    }

    @Override
    public Map<String, Object> checkRole(String roleName) {
        int count = roleManageMapper.checkRole(roleName);
        if (count > 0) {
            return Result.fail("角色名已存在！");
        }
        return Result.success("角色名未重复！");
    }

    @Override
    public Map<String, Object> authorization(Map<String, String> map) {
        String menuId = map.get("menuId");
        String[] menuIds = menuId.split(",");
        //先删除菜单角色关系
        List<String> roleId = new ArrayList<>();
        roleId.add(map.get("roleId"));
        roleManageMapper.deleteMenuRole(roleId);
        //重新添加菜单角色关系
        List<String> list = Arrays.asList(menuIds);
        for (int i = 0; i < list.size(); i++) {
            int count = roleManageMapper.authorization(map.get("roleId"), list.get(i));
            if (count <= 0) {
                return Result.fail("角色授权失败！");
            }
        }
        return Result.success("角色授权成功！");
    }

    @Override
    public List<TreeRsult> queryMenu(String roleId) {
        List<TreeRsult> list = roleManageMapper.queryMenu();
        List<Integer> menuIds = roleManageMapper.queryMenuById(roleId);
        for (int i = 0; i < list.size(); i++) {
            TreeRsult treeRsult = list.get(i);
            for (int j = 0; j < menuIds.size(); j++) {
                if (menuIds.get(j) == treeRsult.getId()) {
                    treeRsult.setChecked(true);
                }
            }
        }
        return list;
    }

    @Override
    public List<SysRoleBean> queryRole() {
        List<SysRoleBean> sysRoleBeans = roleManageMapper.queryRole();
        return sysRoleBeans;
    }
}
