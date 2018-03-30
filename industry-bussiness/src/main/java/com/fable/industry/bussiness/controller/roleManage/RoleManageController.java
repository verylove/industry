package com.fable.industry.bussiness.controller.roleManage;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.SysRoleBean;
import com.fable.industry.bussiness.service.roleManage.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-30 11:26
 * @Description 角色管理
 */
@RequestMapping("/roleManager")
@Controller
public class RoleManageController {

    @Autowired
    private RoleManageService roleManageService;

    /**
     * @Description: 跳转角色列表页面
     */
    @RequestMapping("/roleView")
    public ModelAndView userView() {
        ModelAndView mv = new ModelAndView("systemManage/roleManage/roleManage");
        return mv;
    }

    /**
     * @Param: pageRequest
     * @Description: 查询角色列表
     */
    @RequestMapping("/listRole")
    @ResponseBody
    public PageResponse<SysRoleBean> listRole(@RequestBody PageRequest<SysRoleBean> pageRequest) {
        return roleManageService.findRoleList(pageRequest);
    }

    /**
     * @Description: 跳转角色新增页面
     */
    @RequestMapping("/addRoleView")
    public ModelAndView addRoleView() {
        ModelAndView mv = new ModelAndView("systemManage/roleManage/roleManageAdd");
        return mv;
    }

    /**
     * @Param: sysRoleBean
     * @Description: 新增角色
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public Map<String, Object> addRole(@RequestBody SysRoleBean sysRoleBean) {
        return roleManageService.addRole(sysRoleBean);
    }

    /**
     * @Description: 跳转角色修改页面
     */
    @RequestMapping("/editRoleView")
    public ModelAndView editRoleView() {
        ModelAndView mv = new ModelAndView("systemManage/roleManage/roleManageEdit");
        return mv;
    }

    /**
     * @Param: sysRoleBean
     * @Description: 修改角色
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public Map<String, Object> updateRole(@RequestBody SysRoleBean sysRoleBean) {
        return roleManageService.updateRole(sysRoleBean);
    }

    /**
     * @Description: 跳转角色查看页面
     */
    @RequestMapping("/queryRoleView")
    public ModelAndView queryRoleView() {
        ModelAndView mv = new ModelAndView("systemManage/roleManage/roleManageView");
        return mv;
    }

    /**
     * @Param: map
     * @Description: 查看角色
     */
    @RequestMapping("/queryRoleById")
    @ResponseBody
    public SysRoleBean queryRoleById(@RequestBody Map<String, Object> map) {
        Integer roleId = Integer.parseInt(map.get("roleId").toString());
        return roleManageService.queryRoleById(roleId);
    }

    /**
     * @Param: map
     * @Description: 删除角色
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public Map<String, Object> deleteRole(@RequestBody Map<String, Object> map) {
        String roleIds = map.get("roleIds").toString();
        return roleManageService.deleteRole(roleIds);
    }

    /**
     * @Param: map
     * @Description: 角色是否可删除校验
     */
    @RequestMapping("/checkDeleteRole")
    @ResponseBody
    public Map<String, Object> checkDeleteRole(@RequestBody Map<String, Object> map) {
        String roleIds = map.get("roleIds").toString();
        return roleManageService.checkDeleteRole(roleIds);
    }

    /**
     * @Param: map
     * @Description: 角色唯一性校验
     */
    @RequestMapping("/checkRole")
    @ResponseBody
    public Map<String, Object> checkRole(@RequestBody Map<String, Object> map) {
        String roleName = map.get("roleName").toString();
        return roleManageService.checkRole(roleName);
    }

    /**
     * @Description: 跳转角色授权查看页面
     */
    @RequestMapping("/roleAuthorizationView")
    public ModelAndView roleAuthorizationView() {
        ModelAndView mv = new ModelAndView("systemManage/roleManage/roleViewAuthorization");
        return mv;
    }

    /**
     * @Description: 跳转角色授权页面
     */
    @RequestMapping("/authorizationView")
    public ModelAndView authorizationView() {
        ModelAndView mv = new ModelAndView("systemManage/roleManage/roleAuthorization");
        return mv;
    }

    /**
     * @Param: map
     * @Description: 角色授权
     */
    @RequestMapping("/authorization")
    @ResponseBody
    public Map<String, Object> authorization(@RequestBody Map<String, Object> map) {
        String roleId = map.get("roleId").toString();
        String menuId = map.get("menuId").toString();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("roleId", roleId);
        paramMap.put("menuId", menuId);
        return roleManageService.authorization(paramMap);
    }

    /**
     * @Param: map
     * @Description: 菜单树的查询
     */
    @RequestMapping("/queryMenu")
    @ResponseBody
    public List<TreeRsult> queryMenu(@RequestBody Map<String, Object> map) {
        String roleId = map.get("roleId").toString();
        return roleManageService.queryMenu(roleId);
    }

    /**
     * @Description: 角色下拉查询接口
     */
    @RequestMapping("/queryRole")
    @ResponseBody
    public List<SysRoleBean> queryRole() {
        return roleManageService.queryRole();
    }
}
