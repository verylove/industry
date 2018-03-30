package com.fable.industry.bussiness.mapper.dictionary;

import com.fable.industry.bussiness.model.bean.DictionaryItemBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/29
 */
public interface DictionaryItemMapper {

    List<DictionaryItemBean> listDictionar(DictionaryItemBean bean);

    int getByNameAndDictId(DictionaryItemBean bean);

    int insertItem(DictionaryItemBean bean);

    int updateItem(DictionaryItemBean bean);

    List<String> getItemByState(@Param("ids") String ids);

    int deleteItem(@Param("ids") String ids);

    Map<String, Object> queryItemById(Integer id);

    List<DictionaryItemBean> dict(Integer id);

    DictionaryItemBean queryIdByName(@Param("classId") Integer classId, @Param("itemName") String itemName);
}
