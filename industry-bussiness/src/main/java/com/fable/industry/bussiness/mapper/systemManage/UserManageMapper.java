package com.fable.industry.bussiness.mapper.systemManage;

import com.fable.industry.bussiness.model.bean.SysUserBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-29 13:48
 * @Description 用户管理
 */
public interface UserManageMapper {

    /**
     * 查询用户列表
     */
    List<SysUserBean> findUserList(SysUserBean sysUserBean);

    /**
     * 新增用户
     */
    int addUser(SysUserBean sysUserBean);

    /**
     * 新增用户与角色关系
     */
    int addUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 查看用户
     */
    SysUserBean queryUserById(Integer userId);

    /**
     * 修改用户
     */
    int updateUser(SysUserBean sysUserBean);

    /**
     * 修改用户和角色关系
     */
    int updateUserRole(SysUserBean sysUserBean);

    /**
     * 删除用户
     */
    int deleteUser(@Param("userIds") List<String> userIds);

    /**
     * 删除用户与角色关系
     */
    int deleteUserRole(@Param("userIds") List<String> userIds);

    /**
     * 重置密码
     */
    int resetPassword(Map<String, String> map);

    /**
     * 登录名称的唯一性校验
     */
    int checkLoginName(String login);

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
     * 更新用户状态
     */
    int updateStatus(@Param("userId") Integer userId, @Param("lockStatus") String lockStatus);
}
