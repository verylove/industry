package com.fable.industry.bussiness.controller.resci;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.ResciCatalogBean;
import com.fable.industry.bussiness.service.resci.ResciCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/2/2
 */
@RequestMapping("/catalogManager")
@Controller
public class ResciCatalogController {

    @Autowired
    private ResciCatalogService resciCatalogService;


    /**
     *@author jiangmingjun
     *@date 2018/2/8
     *@description 资源域树
     */
    @RequestMapping("/catalogTree")
    @ResponseBody
    public List<TreeRsult> catalogTree() {
        return resciCatalogService.catalogTree();
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 目录管理页面返回
     */
    @RequestMapping("/catalogManage")
    public ModelAndView catalogManager() {
        return new ModelAndView("metadataManage/catalogManage/catalogManage");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 目录新增页面返回
     */
    @RequestMapping("/catalogAdd")
    @ResponseBody
    public ModelAndView catalogAdd() {
        return new ModelAndView("metadataManage/catalogManage/catalogAdd");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 目录修改页面返回
     */
    @RequestMapping("/catalogModify")
    @ResponseBody
    public ModelAndView catalogModify() {
        return new ModelAndView("metadataManage/catalogManage/catalogModify");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 目录查看页面返回
     */
    @RequestMapping("/catalogView")
    @ResponseBody
    public ModelAndView catalogView() {
        return new ModelAndView("metadataManage/catalogManage/catalogView");
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/2
     *@description 目录列表查询
     */
    @RequestMapping("/listCatalog")
    @ResponseBody
    public PageResponse listCatalog(@RequestBody PageRequest<ResciCatalogBean> pageRequest) {
        return resciCatalogService.listCatalog(pageRequest);
    }


    /**
     *@author jiangmingjun
     *@date 2018/2/2
     *@description 新增目录
     */
    @RequestMapping("/addCatalog")
    @ResponseBody
    public Map<String,Object> addCatalog(@RequestBody ResciCatalogBean bean) {
        return resciCatalogService.addCatalog(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/2
     *@description 目录删除校验
     */
    @RequestMapping("/deleteCatalogCheck")
    @ResponseBody
    public Map<String,Object> deleteCatalogCheck(@RequestBody Map<String,Object> map) {
        return resciCatalogService.deleteCatalogCheck(map.get("ids").toString());
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/2
     *@description 修改目录
     */
    @RequestMapping("/updateCatalog")
    @ResponseBody
    public Map<String,Object> updateCatalog(@RequestBody ResciCatalogBean bean) {
        return resciCatalogService.updateCatalog(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/2
     *@description 目录删除
     */
    @RequestMapping("/deleteCatalog")
    @ResponseBody
    public Map<String,Object> deleteCatalog(@RequestBody Map<String,Object> map) {
        return resciCatalogService.deleteCatalog(map.get("ids").toString());
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/2
     *@description 查看和修改的数据带入目录
     */
    @RequestMapping("/queryCatalogById")
    @ResponseBody
    public Map<String,Object> queryCatalogById(@RequestBody Map<String,Object> map) {
        return resciCatalogService.queryCatalogById(Integer.valueOf(map.get("id").toString()));
    }
}
