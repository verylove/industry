package com.fable.industry.bussiness.service.dictionary;

import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.DictionaryClassBean;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/29
 */
public interface DictionaryClassService {

    /**
     * 查询字典树
     */
    PageResponse<DictionaryClassBean> queryDictionarTree();

    /**
     *
     */
    List<DictionaryClassBean> queryDictionarSelect();

    /**
     * 字典分类新增校验
     */
    Map<String, Object> addDictionarCheck(DictionaryClassBean bean);

    /**
     * 新增字典分类
     */
    Map<String,Object> addDictionar(DictionaryClassBean bean);

    /**
     * 修改字典分类
     */
    Map<String,Object> updateDictionar(DictionaryClassBean bean);

    /**
     * 字典分类可删除校验
     */
    Map<String,Object> checkDelDictionar(String ids);

    /**
     * 删除字典分类
     */
    Map<String,Object> deleteDictionar (String ids);

    /**
     * 查询字典分类
     */
    Map<String,Object> queryDictionarById(Integer id);
}
