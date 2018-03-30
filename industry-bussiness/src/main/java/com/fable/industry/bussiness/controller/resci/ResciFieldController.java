package com.fable.industry.bussiness.controller.resci;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.ResciFieldBean;
import com.fable.industry.bussiness.service.resci.ResciFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/26
 * @description 资源域管理
 */
@RequestMapping("/fieldManager")
@Controller
public class ResciFieldController {

    @Autowired
    private ResciFieldService resciFieldService;

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 返回域管理页面
     */
    @RequestMapping("/regionManage")
    public ModelAndView regionManage(){
        return new ModelAndView("interconnectManage/regionManage/regionManage");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 返回域管理新增页面
     */
    @RequestMapping("/regionManageAdd")
    public ModelAndView regionManageAdd(){
        return new ModelAndView("interconnectManage/regionManage/regionManageAdd");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 返回域管理修改页面
     */
    @RequestMapping("/regionManageEdit")
    public ModelAndView regionManageEdit(){
        return new ModelAndView("interconnectManage/regionManage/regionManageEdit");
    }
    
    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 返回域管理查看页面
     */
    @RequestMapping("/regionManageView")
    public ModelAndView regionManageView(){
        return new ModelAndView("interconnectManage/regionManage/regionManageView");
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/26
     *@description 资源域列表查询
     */
    @RequestMapping("/listResciField")
    @ResponseBody
    public PageResponse listResciField(@RequestBody PageRequest<ResciFieldBean> pageRequest) {
        return resciFieldService.findResciFieldList(pageRequest);
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/29
     *@description 域名称的唯一性校验
     */
    @RequestMapping("/checkFieldName")
    @ResponseBody
    public Map<String,Object> checkFieldName(@RequestBody Map<String,Object> map) {
        return resciFieldService.checkFieldName(map.get("fieldName").toString());
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/26
     *@description 新增域
     */
    @RequestMapping("/addResciField")
    @ResponseBody
    public Map<String, Object> addResciField(@RequestBody ResciFieldBean bean) {
        return resciFieldService.addResciField(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/29
     *@description 修改域
     */
    @RequestMapping("/updateResciField")
    @ResponseBody
    public Map<String,Object> updateResciField(@RequestBody ResciFieldBean bean) {
        return resciFieldService.updateResciField(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/29
     *@description 查看域（窗口）
     */
    @RequestMapping("/queryResciFieldById")
    @ResponseBody
    public Map<String,Object> queryResciFieldById(@RequestBody Map<String,Object> map) {
        return resciFieldService.queryResciFieldById(Integer.valueOf(map.get("resciFieldId").toString()));
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/29
     *@description 域可删除校验
     */
    @RequestMapping("/checkDelField")
    @ResponseBody
    public Map<String,Object> checkDelField(@RequestBody Map<String,Object> map) {
        return resciFieldService.checkDelField(map.get("resciFieldIds").toString());
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/29
     *@description 删除资源域
     */
    @RequestMapping("/delResciField")
    @ResponseBody
    public Map<String,Object> delResciField(@RequestBody Map<String,Object> map) {
        return resciFieldService.delResciField(map.get("resciFieldIds").toString());
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 返回根域
     */
    @RequestMapping("/rootField")
    @ResponseBody
    public Map<String,String> rootField() {
        return resciFieldService.rootField();
    }

}
