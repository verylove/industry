package com.fable.industry.bussiness.mapper.resci;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.ResciFieldBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/26
 */
public interface ResciFieldMapper {

    List<ResciFieldBean> findResciFieldList(ResciFieldBean bean);

    Integer getFieldByName(String fieldName);

    int insertResciField(ResciFieldBean bean);

    int updateResciField(ResciFieldBean bean);

    Map<String,Object> queryResciFieldById(Integer resciFieldId);

    List<String> getFieldByIds(@Param("resciFieldIds") String resciFieldIds);

    int delResciField(@Param("resciFieldIds")String resciFieldIds);

    Map<String,String> rootField();

}
