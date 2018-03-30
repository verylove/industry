package com.fable.industry.bussiness.service.systemManage;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.SysUserBean;

import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-29 10:52
 * @Description 用户管理
 */
public interface UserManageService {
    /**
     * 查询用户列表
     */
    PageResponse<SysUserBean> findUserList(PageRequest<SysUserBean> pageRequest);

    /**
     * 新增用户
     */
    Map<String, Object> addUser(SysUserBean sysUserBean);

    /**
     * 查看用户
     */
    SysUserBean queryUserById(Integer userId);

    /**
     * 修改用户
     */
    Map<String, Object> updateUser(SysUserBean sysUserBean);

    /**
     * 删除用户
     */
    Map<String, Object> deleteUser(String userIds);

    /**
     * 重置密码
     */
    Map<String, Object> resetPassword(Map<String, String> map);

    /**
     * 登录名称的唯一性校验
     */
    Map<String, Object> checkLoginName(String login);

    /**
     * 根据公司Id查找用户
     */
    List<Map<String, Object>> queryByComId(Integer comId);

    List<SysUserBean> queryRoleUserByComId(Map<String, Integer> map);

    /**
     * 修改用户登录时间
     */
    void updateLoginTime(SysUserBean sysUserBean);

    /**
     * 根据用户名查找用户
     */
    SysUserBean queryUserByName(String login);

    /**
     * 重置密码时验证旧密码
     */
    Map<String, Object> checkOldPassword(Map<String, Object> map);

    /**
     * 更新用户状态
     */
    Map<String, Object> updateStatus(Map<String, Object> map);
}
