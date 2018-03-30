package com.fable.industry.bussiness.service.systemManage.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.bussiness.mapper.systemManage.UserManageMapper;
import com.fable.industry.bussiness.model.bean.SysUserBean;
import com.fable.industry.bussiness.service.systemManage.UserManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-29 13:29
 */
@Service
@Transactional
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public PageResponse<SysUserBean> findUserList(PageRequest<SysUserBean> pageRequest) {
        SysUserBean sysUserBean = pageRequest.getParam();
        Page<SysUserBean> data = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        userManageMapper.findUserList(sysUserBean);
        return PageResponse.wrap(data, true, "0", "success");
    }

    @Override
    public Map<String, Object> addUser(SysUserBean sysUserBean) {
        int sysUser = userManageMapper.addUser(sysUserBean);
        Integer userId = sysUserBean.getUserId();
        int sysUserRole = userManageMapper.addUserRole(userId, sysUserBean.getRoleId());
        if (sysUser > 0 && sysUserRole > 0) {
            return Result.success("插入用户数据成功！");
        }
        return Result.fail("插入用户数据失败！");
    }

    @Override
    public SysUserBean queryUserById(Integer userId) {
        SysUserBean sysUserBean = userManageMapper.queryUserById(userId);
        return sysUserBean;
    }

    @Override
    public Map<String, Object> updateUser(SysUserBean sysUserBean) {
        int count = userManageMapper.updateUser(sysUserBean);
        int userRole = userManageMapper.updateUserRole(sysUserBean);
        if (count > 0 && userRole > 0) {
            return Result.success("更新用户数据成功！");
        }
        return Result.fail("更新用户数据失败！");
    }

    @Override
    public Map<String, Object> deleteUser(String userIds) {
        String[] ids = userIds.split(",");
        List<String> list = Arrays.asList(ids);
        int sysUser = userManageMapper.deleteUser(list);
        int sysUserRole = userManageMapper.deleteUserRole(list);
        if (sysUser > 0 && sysUserRole > 0) {
            return Result.success("删除用户数据成功！");
        }
        return Result.fail("删除用户数据失败！");
    }

    @Override
    public Map<String, Object> resetPassword(Map<String, String> map) {
        int count = userManageMapper.resetPassword(map);
        if (count > 0) {
            return Result.success("重置密码成功！");
        }
        return Result.fail("重置密码失败！");
    }

    @Override
    public Map<String, Object> checkLoginName(String login) {
        int count = userManageMapper.checkLoginName(login);
        if (count > 0) {
            return Result.fail("用户名称重复！");
        }
        return Result.success("该名称是唯一的！");
    }

    @Override
    public List<Map<String, Object>> queryByComId(Integer comId) {
        List<Map<String, Object>> sysUser = userManageMapper.queryByComId(comId);
        return sysUser;
    }

    @Override
    public List<SysUserBean> queryRoleUserByComId(Map<String, Integer> map) {
        return userManageMapper.queryRoleUserByComId(map);
    }

    @Override
    public void updateLoginTime(SysUserBean sysUserBean) {
        userManageMapper.updateLoginTime(sysUserBean);
    }

    @Override
    public SysUserBean queryUserByName(String login) {
        return userManageMapper.queryUserByName(login);
    }

    @Override
    public Map<String, Object> checkOldPassword(Map<String, Object> map) {
        Integer userId = Integer.valueOf(map.get("userId").toString());
        String password = map.get("password").toString();
        SysUserBean sysUserBean = userManageMapper.queryUserById(userId);
        if (password.equals(sysUserBean.getPassword())) {
            return Result.success("密码正确！");
        }
        return Result.fail("旧密码不正确！");
    }

    @Override
    public Map<String, Object> updateStatus(Map<String, Object> map) {
        Integer userId = Integer.valueOf(map.get("userId").toString());
        String lockStatus = map.get("lockStatus").toString();
        int count = userManageMapper.updateStatus(userId, lockStatus);
        if (count > 0) {
            return Result.success("用户状态更改成功！");
        }
        return Result.fail("用户状态更改失败！");
    }
}
