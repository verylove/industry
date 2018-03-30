package com.fable.industry.bussiness.service.dictionary;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.DictionaryItemBean;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/29
 */
public interface DictionaryItemService {
    /**
     * 字典项列表查询
     */
    PageResponse listDictionar(PageRequest<DictionaryItemBean> pageRequest);

    /**
     * 字典项可新增校验
     */
    Map<String, Object> addItemCheck(DictionaryItemBean bean);

    /**
     * 新增字典项
     */
    Map<String, Object> addItem(DictionaryItemBean bean);

    /**
     * 修改字典项
     */
    Map<String, Object> updateItem(DictionaryItemBean bean);

    /**
     * 字典项删除校验
     */
    Map<String,Object> itemDeleteCheck(String ids);

    /**
     * 删除字典项
     */
    Map<String, Object> deleteItem(String ids);

    /**
     * 查询字典项
     */
    Map<String, Object> queryItemById(Integer id);

    /**
     * 根据字典分类ID查询字典项具体信息
     */
    List<DictionaryItemBean> dict(Integer classId);

    /**
     * 根据字典分类ID和字典名称查询字典项具体信息
     */
    DictionaryItemBean queryIdByName(Integer classId, String itemName);
}
