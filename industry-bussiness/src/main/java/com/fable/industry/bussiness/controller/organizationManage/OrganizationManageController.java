package com.fable.industry.bussiness.controller.organizationManage;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.SysOrganizationBean;
import com.fable.industry.bussiness.service.organizationManage.OrganizationManageService;
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
 * @Date 2018-01-30 18:25
 * @Description 单位信息管理
 */
@RequestMapping("/organizationManager")
@Controller
public class OrganizationManageController {

    @Autowired
    private OrganizationManageService organizationManageService;

    /**
     * @Description: 跳转单位列表页面
     */
    @RequestMapping("/organizationView")
    public ModelAndView organizationView() {
        ModelAndView mv = new ModelAndView("systemManage/unitInforManage/unitInforManage");
        return mv;
    }

    /**
     * @Param: request
     * @Description: 单位信息列表查询
     */
    @RequestMapping("/listOrganization")
    @ResponseBody
    public PageResponse<SysOrganizationBean> listOrganization(@RequestBody PageRequest<SysOrganizationBean> request) {
        return organizationManageService.findOrganization(request);
    }

    /**
     * @Description: 单位信息树查询
     */
    @RequestMapping("/listOrganizationTree")
    @ResponseBody
    public List<TreeRsult> listOrganizationTree() {
        return organizationManageService.findOrganizationTree();
    }

    /**
     * @Description: 跳转单位新增页面
     */
    @RequestMapping("/addOrganizationView")
    public ModelAndView addOrganizationView() {
        ModelAndView mv = new ModelAndView("systemManage/unitInforManage/unitInforManageAdd");
        return mv;
    }

    /**
     * @Param: sysOrganizationBean
     * @Description: 新增单位
     */
    @RequestMapping("/addOrganization")
    @ResponseBody
    public Map<String, Object> addOrganization(@RequestBody SysOrganizationBean sysOrganizationBean) {
        return organizationManageService.addOrganization(sysOrganizationBean);
    }

    /**
     * @Description: 跳转单位修改页面
     */
    @RequestMapping("/editOrganizationView")
    public ModelAndView editOrganizationView() {
        ModelAndView mv = new ModelAndView("systemManage/unitInforManage/unitInforManageEdit");
        return mv;
    }

    /**
     * @Param: sysOrganizationBean
     * @Description: 修改单位
     */
    @RequestMapping("/updateOrganization")
    @ResponseBody
    public Map<String, Object> updateOrganization(@RequestBody SysOrganizationBean sysOrganizationBean) {
        return organizationManageService.updateOrganization(sysOrganizationBean);
    }

    /**
     * @Param: comId
     * @Description: 删除单位
     */
    @RequestMapping("/deleteOrganization")
    @ResponseBody
    public Map<String, Object> deleteOrganization(@RequestBody Map<String, Object> map) {
        String comId = map.get("comIds").toString();
        return organizationManageService.deleteOrganization(comId);
    }

    /**
     * @Param: comId
     * @Description: 单位是否可删除校验
     */
    @RequestMapping("/checkdeleteOrganization")
    @ResponseBody
    public Map<String, Object> checkdeleteOrganization(@RequestBody Map<String, Object> map) {
        String comId = map.get("comIds").toString();
        return organizationManageService.checkdeleteOrganization(comId);
    }

    /**
     * @Param: request
     * @Description: 单位唯一性校验
     */
    @RequestMapping("/checkOrganization")
    @ResponseBody
    public Map<String, Object> checkOrganization(@RequestBody Map<String, Object> map) {
        String comName = map.get("comName").toString();
        String pid = map.get("pid").toString();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("comName", comName);
        paramMap.put("pid", pid);
        return organizationManageService.checkOrganization(paramMap);
    }

    /**
     * @Description: 跳转单位查看页面
     */
    @RequestMapping("/queryOrganizationView")
    public ModelAndView queryOrganizationView() {
        ModelAndView mv = new ModelAndView("systemManage/unitInforManage/unitInforManageView");
        return mv;
    }

    /**
     * @Param: sysOrganizationBean
     * @Description: 查看单位
     */
    @RequestMapping("/queryOrganizationById")
    @ResponseBody
    public SysOrganizationBean queryOrganizationById(@RequestBody Map<String, Object> map) {
        Integer comId = Integer.parseInt(map.get("comId").toString());
        return organizationManageService.queryOrganizationById(comId);
    }

    /**
     * @Description: 单位下拉列表查询
     */
    @RequestMapping("/listOrganizationComboBox")
    @ResponseBody
    public List<TreeRsult> listOrganizationComboBox() {
        return organizationManageService.findOrganizationComboBox();
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 域管理查询所有单位
     */
    @RequestMapping("/queryAllCom")
    @ResponseBody
    public List<SysOrganizationBean> queryAllCom() {
        return organizationManageService.queryAllCom();
    }
}
