package com.fable.industry.bussiness.service.resci;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.ResciFieldBean;

import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/26
 * @description 资源域
 */
public interface ResciFieldService {

    /**
     * 资源域列表查询
     */
    PageResponse<ResciFieldBean> findResciFieldList(PageRequest<ResciFieldBean> pageRequest);

    /**
     * 域名称唯一性
     */
    Map<String,Object> checkFieldName(String fieldName);

    /**
     * 新增域
     */
    Map<String,Object> addResciField(ResciFieldBean bean);

    /**
     * 修改域
     */
    Map<String,Object> updateResciField(ResciFieldBean bean);

    /**
     * 查看和修改的数据带入资源域
     */

    Map<String,Object> queryResciFieldById(Integer resciFieldId);

    /**
     * 域可删除校验
     */
    Map<String,Object> checkDelField(String resciFieldIds);

    /**
     * 删除资源域
     */
    Map<String,Object> delResciField(String resciFieldIds);

    /**
     * 返回根域
     */
    Map<String,String> rootField();

}
