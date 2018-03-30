package com.fable.industry.bussiness.controller.resci;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.api.utils.FileUtil;
import com.fable.industry.bussiness.model.bean.DataExamineBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.service.dataElement.DataElementService;
import com.fable.industry.bussiness.service.resci.ResciCataUEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/2/3
 * @description 资源编目
 */
@RequestMapping("/catalogingManager")
@Controller
public class ResciCatalogUEController {
    
    @Autowired
    private ResciCataUEService resciCataUEService;

    @Autowired
    private DataElementService dataElementService;
    

    /**
     *@author jiangmingjun
     *@date 2018/2/28
     *@description 资源编目新增数据项页面
     */
    @RequestMapping("/dataItem")
    public String dataItem() {
        return "metadataManage/resourceCatalog/dataItemAdd";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/28
     *@description 资源编目新增页面
     */
    @RequestMapping("/resourceAdd")
    public String resourceAdd() {
        return "metadataManage/resourceCatalog/resourceAdd";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/28
     *@description 资源编目主页面
     */
    @RequestMapping("/resourceCatalog")
    public String resourceCatalog() {
        return "metadataManage/resourceCatalog/resourceCatalog";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/28
     *@description 资源编目查看页面
     */
    @RequestMapping("/resourceView")
    public String resourceView() {
        return "metadataManage/resourceCatalog/resourceView";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/28
     *@description 资源编目查看流程页面
     */
    @RequestMapping("/resourceProcess")
    public String resourceProcess() {
        return "metadataManage/resourceCatalog/resourceProcess";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/28
     *@description 资源编目树
     */
    @RequestMapping("/catalogingTree")
    @ResponseBody
    public List<TreeRsult> catalogingTree() {
        return resciCataUEService.catalogingTree();
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 资源编目列表
     */
    @RequestMapping("/catalogingList")
    @ResponseBody
    public PageResponse catalogingList(@RequestBody PageRequest<ResciCatalogUEBean> pageRequest,
                                       HttpServletRequest request) {
        return resciCataUEService.catalogingList(pageRequest,request);
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/6
     *@description 审核编目列表
     */
    @RequestMapping("/examineCatalogList")
    @ResponseBody
    public PageResponse examineCatalogList(@RequestBody PageRequest<ResciCatalogUEBean> pageRequest,
                                           HttpServletRequest request) {
        return resciCataUEService.examineCatalogList(pageRequest,request);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/3
     *@description 保存资源编目(1,2步)
     */
    @RequestMapping("/catalogingApplication")
    @ResponseBody
    public ResciCatalogUEBean catalogingApplication(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.cataloging(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 保存资源编目(第3步)
     */
    @RequestMapping("/catalogingSaveStep3")
    @ResponseBody
    public List<Map<String,Object>> catalogingSaveStep3(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.catalogingStep3(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/4
     *@description 返回第四步必选数据元
     */
    @RequestMapping("/queryDataElementM")
    @ResponseBody
    public PageResponse queryDataElementM(@RequestBody PageRequest pageRequest) {
        return dataElementService.queryDataByM(pageRequest);
    }


    /**
     *@author jiangmingjun
     *@date 2018/2/5
     *@description 保存资源编目(第4步编辑数据项)
     */
    @RequestMapping("/catalogingSaveStep4")
    @ResponseBody
    public Map<String,Object> catalogingSaveStep4(@RequestBody Map<String,Object> map) {
        return resciCataUEService.catalogingStep4(map);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 资源编目最终保存（即第5步保存）
     */
    @RequestMapping("/catalogingSave")
    @ResponseBody
    public Map<String,Object> catalogingSave(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.catalogingSave(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/26
     *@description 创建动态表单
     */
    @RequestMapping("/createDynamicDB")
    @ResponseBody
    public Map<String,Object> createDynamicDB(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.createDynamicDB(bean);
    }
    /**
     *@author jiangmingjun
     *@date 2018/3/7
     *@description 修改资源编目页面
     */
    @RequestMapping("/updatePage")
    public String updatePage() {
        return "metadataManage/resourceCatalog/dataItemModify";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/5
     *@description 修改资源编目即（2,3,4步返回上一步）
     */
    @RequestMapping("/catalogingUpdate")
    @ResponseBody
    public ResciCatalogUEBean catalogingUpdate(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.catalogingUpdate(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/5
     *@description 修改资源编目（即第5步返回上一步）
     */
    @RequestMapping("/catalogingUpdateStep4")
    @ResponseBody
    public List<Map<String,Object>> catalogingUpdateStep4(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.catalogingUpdateStep4(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 点击修改资源编目按钮跳到当前修改状态(1,2,3步)
     */
    @RequestMapping("/queryResciCataUE")
    @ResponseBody
    public ResciCatalogUEBean queryResciCataUE(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.queryResciCataUE(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 点击修改资源编目按钮跳到当前修改状态(第4步)
     */
    @RequestMapping("/queryDataByCUE")
    @ResponseBody
    public List<Map<String, Object>> queryDataByCUE(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.queryDataByCUE(bean.getId());
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/6
     *@description 点击修改资源编目按钮跳到当前修改状态(第5步)，资源编目查看也可以调用此接口
     */
    @RequestMapping("/queryCatalogUEAll")
    @ResponseBody
    public Map<String,Object> queryCatalogUEAll(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.queryCatalogUEAll(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 资源编目可删除校验(已发布的不可删)
     */
    @RequestMapping("/checkCatalogUE")
    @ResponseBody
    public Map<String,Object> checkCatalogUE(@RequestBody Map<String,String> map) {
        return resciCataUEService.checkCatalogUE(map.get("ids"));
    }
    
    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 资源编目删除
     */
    @RequestMapping("/deleteCatalogUE")
    @ResponseBody
    public Map<String,Object> deleteCatalogUE(@RequestBody Map<String,String> map) {
        return resciCataUEService.deleteCatalogUE(map.get("ids"));
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 图片上传
     */
    @RequestMapping("/uploadPic")
    @ResponseBody
    public Map<String,Object> uploadPic(@RequestParam("file") CommonsMultipartFile picture, HttpServletRequest request) {
        return FileUtil.upload(picture,request);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/22
     *@description 资源编目状态变化（提交、发布、撤销）
     */
    @RequestMapping("/stateChange")
    @ResponseBody
    public Map<String,String> stateChange(@RequestBody List<ResciCatalogUEBean> list) {
        Map<String,String> map = new HashMap<>();
        for (ResciCatalogUEBean bean : list) {
            map =  resciCataUEService.stateChange(bean);

        }
        return map;
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/22
     *@description 单个资源编目状态变化（提交、发布、撤销）
     */
    @RequestMapping("/stateChangeSingle")
    @ResponseBody
    public Map<String,String> stateChangeSingle(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.stateChange(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/3
     *@description 编目审核页面
     */
    @RequestMapping("/catalogExamine")
    public String catalogExamine() {
        return "metadataManage/examineCatalog/catalogExamine";
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/3
     *@description 编目审核查看页面
     */
    @RequestMapping("/catalogView")
    public String catalogView() {
        return "metadataManage/examineCatalog/catalogView";
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/3
     *@description 审核页面
     */
    @RequestMapping("/examineCatalog")
    public String examineCatalog() {
        return "metadataManage/examineCatalog/examineCatalog";
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/23
     *@description 资源编目审核
     */
    @RequestMapping("/review")
    @ResponseBody
    public Map<String,Object> review(@RequestBody DataExamineBean bean,HttpServletRequest request) {
        return resciCataUEService.review(bean,request);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/26
     *@description 查看发布流程
     */
    @RequestMapping("/viewReleaseProcess")
    @ResponseBody
    public Map<String,Object> viewReleaseProcess(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.getReleaseProcess(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/1
     *@description oid假校验
     */
    @RequestMapping("/oidCheck")
    @ResponseBody
    public Map<String,Object> oidCheck(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.oidCheck(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/1
     *@description 获取oid
     */
    @RequestMapping("/getoid")
    @ResponseBody
    public Map<String,Object> getoid(@RequestBody ResciCatalogUEBean bean) {
        return resciCataUEService.getoid(bean);
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/5
     *@description 关联数据元页面
     */
    @RequestMapping("/dataElementRelevance")
    public String dataElementRelevance() {
        return "metadataManage/resourceCatalog/dataItemRelevance";
    }
    
    /**
     *@author jiangmingjun
     *@date 2018/3/13
     *@description 资源编码的唯一性校验
     */
    @RequestMapping("/resciCodeCheck")
    @ResponseBody
    public Map<String,Object> resciCodeCheck(@RequestBody Map<String,Object> map) {
        return resciCataUEService.resciCodeCheck(map.get("resciCode").toString());
    }

}
