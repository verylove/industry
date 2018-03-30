package com.fable.industry.bussiness.mapper.dictionary;

import com.fable.industry.bussiness.model.bean.DictionaryClassBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DictionaryClassMapper {

    List<DictionaryClassBean> queryDictionarTree();

    List<DictionaryClassBean> queryDictionarSelect();

    int getDictionarByName(DictionaryClassBean bean);

    int insertDictionar(DictionaryClassBean bean);

    int updateDictionar(DictionaryClassBean bean);

    List<String> getItemByClassId(@Param("ids") String ids);

    int deleteDictionar(@Param("ids") String ids);

    Map<String,Object> queryDictionarById(Integer id);

    int getDictType(@Param("ids") String ids);
}
