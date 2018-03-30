package com.fable.industry.bussiness.controller.dictionary;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.DictionaryClassBean;
import com.fable.industry.bussiness.model.bean.DictionaryItemBean;
import com.fable.industry.bussiness.service.dictionary.DictionaryClassService;
import com.fable.industry.bussiness.service.dictionary.DictionaryItemService;
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
 * @create 2018/1/29
 * @description 数据字典管理
 */
@RequestMapping("/dictionarManager")
@Controller
public class DictionaryController {
    @Autowired
    private DictionaryItemService dictionaryItemService;
    @Autowired
    private DictionaryClassService dictionaryClassService;


    /**
     * @author jiangmingjun
     * @date 2018/2/7
     * @description 返回数据字典管理页面
     */
    @RequestMapping("/dataDictionary")
    @ResponseBody
    public ModelAndView dataDictionary() {
        return new ModelAndView("metadataManage/dataDictionary/dataDictionary");
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/7
     * @description 返回新增字典项页面
     */
    @RequestMapping("/dictionaryItemAdd")
    @ResponseBody
    public ModelAndView dictionaryItemAdd() {
        return new ModelAndView("metadataManage/dataDictionary/dictionaryItemAdd");
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/7
     * @description 返回修改字典项页面
     */
    @RequestMapping("/dictionaryItemEdit")
    @ResponseBody
    public ModelAndView dictionaryItemEdit() {
        return new ModelAndView("metadataManage/dataDictionary/dictionaryItemEdit");
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/7
     * @description 返回字典类型新增页面
     */
    @RequestMapping("/dictionaryTypeAdd")
    @ResponseBody
    public ModelAndView dictionaryTypeAdd() {
        return new ModelAndView("metadataManage/dataDictionary/dictionaryTypeAdd");
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/7
     * @description 返回字典类型修改页面
     */
    @RequestMapping("/dictionaryTypeEdit")
    @ResponseBody
    public ModelAndView dictionaryTypeEdit() {
        return new ModelAndView("metadataManage/dataDictionary/dictionaryTypeEdit");
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 字典树查询
     */
    @RequestMapping("/queryDictionarTree")
    @ResponseBody
    public PageResponse<DictionaryClassBean> queryDictionarTree() {
        return dictionaryClassService.queryDictionarTree();
    }


    /**
     * @author jiangmingjun
     * @date 2018/2/7
     * @description 字典类型下拉列表框
     */
    @RequestMapping("/queryDictionarSelect")
    @ResponseBody
    public List<DictionaryClassBean> queryDictionarSelect() {
        return dictionaryClassService.queryDictionarSelect();
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/29
     * @description 字典项列表查询
     */
    @RequestMapping("/listDictionar")
    @ResponseBody
    public PageResponse listDictionar(@RequestBody PageRequest<DictionaryItemBean> pageRequest) {
        return dictionaryItemService.listDictionar(pageRequest);
    }

    /**
     * @author jiangmingjun
     * @date 2018/3/6
     * @description 字典分类新增校验
     */
    @RequestMapping("/addDictionarCheck")
    @ResponseBody
    public Map<String, Object> addDictionarCheck(@RequestBody DictionaryClassBean bean) {
        return dictionaryClassService.addDictionarCheck(bean);
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/29
     * @description 新增字典分类
     */
    @RequestMapping("/addDictionar")
    @ResponseBody
    public Map<String, Object> addDictionar(@RequestBody DictionaryClassBean bean) {
        return dictionaryClassService.addDictionar(bean);
    }

    /**
     * @author jiangmingjun
     * @date 2018/3/5
     * @description 字典项新增校验
     */
    @RequestMapping("/addItemCheck")
    @ResponseBody
    public Map<String, Object> addItemCheck(@RequestBody DictionaryItemBean bean) {
        return dictionaryItemService.addItemCheck(bean);
    }


    /**
     * @author jiangmingjun
     * @date 2018/1/29
     * @description 新增字典项
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public Map<String, Object> addItem(@RequestBody DictionaryItemBean bean) {
        return dictionaryItemService.addItem(bean);
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 修改字典分类
     */
    @RequestMapping("/updateDictionar")
    @ResponseBody
    public Map<String, Object> updateDictionar(@RequestBody DictionaryClassBean bean) {
        return dictionaryClassService.updateDictionar(bean);
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 修改字典项
     */
    @RequestMapping("/updateItem")
    @ResponseBody
    public Map<String, Object> updateItem(@RequestBody DictionaryItemBean bean) {
        return dictionaryItemService.updateItem(bean);
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 字典分类可删除校验
     */
    @RequestMapping("/checkDelDictionar")
    @ResponseBody
    public Map<String, Object> checkDelDictionar(@RequestBody Map<String, Object> map) {
        return dictionaryClassService.checkDelDictionar(map.get("ids").toString());
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 删除字典分类
     */
    @RequestMapping("/deleteDictionar")
    @ResponseBody
    public Map<String, Object> deleteDictionar(@RequestBody Map<String, Object> map) {
        return dictionaryClassService.deleteDictionar(map.get("ids").toString());
    }

    /**
     *@author jiangmingjun
     *@date 2018/3/15
     *@description 字典项可删除校验
     */
    @RequestMapping("/itemDeleteCheck")
    @ResponseBody
    public Map<String,Object> itemDeleteCheck(@RequestBody Map<String, Object> map) {
        return dictionaryItemService.itemDeleteCheck(map.get("ids").toString());
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 删除字典项
     */
    @RequestMapping("/deleteItem")
    @ResponseBody
    public Map<String, Object> deleteItem(@RequestBody Map<String, Object> map) {
        return dictionaryItemService.deleteItem(map.get("ids").toString());
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 查询字典分类
     */
    @RequestMapping("/queryDictionarById")
    @ResponseBody
    public Map<String, Object> queryDictionarById(@RequestBody Map<String, Object> map) {
        return dictionaryClassService.queryDictionarById(Integer.valueOf(map.get("id").toString()));
    }

    /**
     * @author jiangmingjun
     * @date 2018/1/30
     * @description 查询字典项具体信息
     */
    @RequestMapping("/queryItemById")
    @ResponseBody
    public Map<String, Object> queryItemById(@RequestBody Map<String, Object> map) {
        return dictionaryItemService.queryItemById(Integer.valueOf(map.get("id").toString()));
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/5
     * @description 根据字典分类ID查询字典项具体信息
     */
    @RequestMapping("/dict")
    @ResponseBody
    public List<DictionaryItemBean> dict(@RequestBody Map<String, Object> map) {
        return dictionaryItemService.dict(Integer.valueOf(map.get("classId").toString()));
    }

    /**
     * @author Wangbaoan
     * @date 2018/3/7
     * @description 根据字典分类ID和字典名称查询字典项具体信息
     */
    @RequestMapping("/queryIdByName")
    @ResponseBody
    public DictionaryItemBean queryIdByName(Integer classId, String itemName) {
        return dictionaryItemService.queryIdByName(classId, itemName);
    }

}
