package com.fable.industry.bussiness.controller.dataElement;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.DataElementBean;
import com.fable.industry.bussiness.service.dataElement.DataElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/31
 */
@RequestMapping("/dataManager")
@Controller
public class DataElementController {

    @Autowired
    private DataElementService dataElementService;

    /**
     *@author jiangmingjun
     *@date 2018/2/23
     *@description 数据元管理页面
     */
    @RequestMapping("/dataElementManager")
    public ModelAndView dataElementManager() {
        return new ModelAndView("metadataManage/dataElementManage/dataElementManage");
    }

    @GetMapping("/test")
    public String test() {
        return "main";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/23
     *@description 数据元新增
     */
    @RequestMapping("/dataElementAdd")
    public ModelAndView dataElementAdd() {
        return new ModelAndView("../jsp/metadataManage/dataElementManage/dataElementAdd");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/23
     *@description 数据元修改
     */
    @RequestMapping("/dataElementEdit")
    public ModelAndView dataElementEdit() {
        return new ModelAndView("../jsp/metadataManage/dataElementManage/dataElementEdit");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/23
     *@description 数据元查看
     */
    @RequestMapping("/dataElementView")
    public ModelAndView dataElementView() {
        return new ModelAndView("../jsp/metadataManage/dataElementManage/dataElementView");
    }

    /**
     *@author jiangmingjun
     *@date 2018/1/31
     *@description 数据元列表查询
     */
    @RequestMapping("/listData")
    @ResponseBody
    public PageResponse listData(@RequestBody PageRequest<DataElementBean> pageRequest) {
        return dataElementService.listData(pageRequest);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/1
     *@description 新增数据元校验
     */
    @RequestMapping("/addDataCheck")
    @ResponseBody
    public Map<String,Object> addDataCheck(@RequestBody DataElementBean bean) {
        return dataElementService.addDataCheck(bean);
    }


    /**
     *@author jiangmingjun
     *@date 2018/2/1
     *@description 新增数据元
     */
    @RequestMapping("/addData")
    @ResponseBody
    public Map<String,Object> addData(@RequestBody DataElementBean bean) {
        return dataElementService.addData(bean);
    }
    
    /**
     *@author jiangmingjun
     *@date 2018/2/1
     *@description 修改数据元
     */
    @RequestMapping("/updateData")
    @ResponseBody
    public Map<String,Object> updateData(@RequestBody DataElementBean bean) {
        return dataElementService.updateData(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/1
     *@description 数据元可删除校验
     */
    @RequestMapping("/deleteDataCheck")
    @ResponseBody
    public Map<String,Object> deleteDataCheck(@RequestBody Map<String,Object> map) {
        return dataElementService.deleteDataCheck(map.get("ids").toString());
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/1
     *@description 删除数据元
     */
    @RequestMapping("/deleteData")
    @ResponseBody
    public Map<String,Object> deleteData(@RequestBody Map<String,Object> map) {
        return dataElementService.deleteData(map.get("ids").toString());
    }
    
    /**
     *@author jiangmingjun
     *@date 2018/2/1
     *@description 查看数据元
     */
    @RequestMapping("/queryDataById")
    @ResponseBody
    public Map<String,String> queryDataById(@RequestBody Map<String,Object> map) {
        return dataElementService.queryDataById(Integer.valueOf(map.get("id").toString()));
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/27
     *@description 数据元启用和停用 （0启用，1停用）
     */
    @RequestMapping("/stateChange")
    @ResponseBody
    public Map<String,Object> stateChange(@RequestBody DataElementBean bean) {
        return dataElementService.updateState(bean);
    }
}
