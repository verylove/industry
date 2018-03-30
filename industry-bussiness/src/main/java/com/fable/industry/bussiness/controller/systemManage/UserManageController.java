package com.fable.industry.bussiness.controller.systemManage;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.SysUserBean;
import com.fable.industry.bussiness.service.systemManage.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-29 10:47
 * @Description 用户管理
 */
@RequestMapping("/userManager")
@Controller
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    /**
     * @Description: 跳转用户列表页面
     */
    @RequestMapping("/userView")
    public ModelAndView userView() {
        ModelAndView mv = new ModelAndView("systemManage/userManage/userManage");
        return mv;
    }

    /**
     * @Param: pageRequest
     * @Description: 查询用户列表
     */
    @RequestMapping("/listUser")
    @ResponseBody
    public PageResponse<SysUserBean> listUser(@RequestBody PageRequest<SysUserBean> pageRequest) {
        return userManageService.findUserList(pageRequest);
    }

    /**
     * @Description: 新增用户列表页面
     */
    @RequestMapping("/addUserView")
    public ModelAndView addUserView() {
        ModelAndView mv = new ModelAndView("systemManage/userManage/userManageAdd");
        return mv;
    }

    /**
     * @Param: sysUserBean request
     * @Description: 新增用户
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String, Object> addUser(@RequestBody SysUserBean sysUserBean, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUserBean bean = (SysUserBean) session.getAttribute("userInfo");
        sysUserBean.setCreatorId(bean.getUserId());
        return userManageService.addUser(sysUserBean);
    }

    /**
     * @Description: 跳转用户列表详情
     */
    @RequestMapping("/userDetailView")
    public ModelAndView userDetailView() {
        ModelAndView mv = new ModelAndView("systemManage/userManage/userManageDetail");
        return mv;
    }

    /**
     * @Param: map
     * @Description: 查看用户
     */
    @RequestMapping("/queryUserById")
    @ResponseBody
    public SysUserBean queryUserById(@RequestBody Map<String, Object> map) {
        Integer userId = Integer.parseInt(map.get("userId").toString());
        return userManageService.queryUserById(userId);
    }

    /**
     * @Description: 跳转用户列表编辑页面
     */
    @RequestMapping("/userEditView")
    public ModelAndView userEditView() {
        ModelAndView mv = new ModelAndView("systemManage/userManage/userManageEdit");
        return mv;
    }

    /**
     * @Param: sysUserBean
     * @Description: 修改用户
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public Map<String, Object> updateUser(@RequestBody SysUserBean sysUserBean, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUserBean userInfo = (SysUserBean) session.getAttribute("userInfo");
        sysUserBean.setUpdateId(userInfo.getUserId());
        return userManageService.updateUser(sysUserBean);
    }

    /**
     * @Param: map
     * @Description: 删除用户
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public Map<String, Object> deleteUser(@RequestBody Map<String, Object> map) {
        String userIds = map.get("userIds").toString();
        return userManageService.deleteUser(userIds);
    }

    /**
     * @Description: 跳转重置密码页面
     */
    @RequestMapping("/resetPassView")
    public ModelAndView resetPassView() {
        ModelAndView mv = new ModelAndView("systemManage/userManage/userResetPass");
        return mv;
    }

    /**
     * @Param: map
     * @Description: 重置密码
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public Map<String, Object> resetPassword(@RequestBody Map<String, Object> map) {
        String userId = map.get("userId").toString();
        String password = map.get("password").toString();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("password", password);
        return userManageService.resetPassword(paramMap);
    }

    /**
     * @Param: map
     * @Description: 登录名称的唯一性校验
     */
    @RequestMapping("/checkLoginName")
    @ResponseBody
    public Map<String, Object> checkLoginName(@RequestBody Map<String, Object> map) {
        String login = map.get("login").toString();
        return userManageService.checkLoginName(login);
    }

    /**
     * @Param: map
     * @Description: 根据公司Id查找用户
     */
    @RequestMapping("/queryByComId")
    @ResponseBody
    public List<Map<String, Object>> queryByComId(@RequestBody Map<String, Object> map) {
        Integer comId = Integer.parseInt(map.get("comId").toString());
        return userManageService.queryByComId(comId);
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/6
     * @description 通过对应的公司ID查找相应的角色用户
     */
    @RequestMapping("/queryRoleUserByComId")
    @ResponseBody
    public List<SysUserBean> queryRoleUserByComId(@RequestBody Map<String, Integer> map) {
        return userManageService.queryRoleUserByComId(map);
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/7
     * @description 获取当前登录用户单位下的角色信息
     */
    @RequestMapping("/sessionInfo")
    @ResponseBody
    public List<SysUserBean> sessionInfo(@RequestBody Map<String, Integer> map, HttpServletRequest request) {
        return userManageService.queryRoleUserByComId(map);
    }

    /**
     * @author Wangbaoan
     * @date 2018/3/7
     * @description 重置密码时验证旧密码
     */
    @RequestMapping("/checkOldPassword")
    @ResponseBody
    public Map<String, Object> checkOldPassword(@RequestBody Map<String, Object> map) {
        return userManageService.checkOldPassword(map);
    }

    /**
     * @author Wangbaoan
     * @date 2018/3/13
     * @description 更改用户状态
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestBody Map<String, Object> map) {
        return userManageService.updateStatus(map);
    }
}
