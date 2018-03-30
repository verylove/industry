package com.fable.industry.bussiness.service.dataElement;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.DataElementBean;

import java.util.List;
import java.util.Map;

public interface DataElementService {

    /**
     * 数据元列表查询
     */
    PageResponse listData(PageRequest<DataElementBean> pageRequest);

    /**
     * 新增数据元校验
     */
    Map<String,Object> addDataCheck(DataElementBean bean);

    /**
     * 新增数据元
     */
    Map<String,Object> addData(DataElementBean bean);

    /**
     * 修改数据元
     */
    Map<String,Object> updateData(DataElementBean bean);

    /**
     * 数据元可删除校验
     */
    Map<String,Object> deleteDataCheck(String ids);

    /**
     * 删除数据元
     */
    Map<String,Object> deleteData(String ids);

    /**
     * 查看数据元
     */
    Map<String,String> queryDataById(Integer id);

    /**
     * 查询必选数据元
     */
    PageResponse queryDataByM(PageRequest pageRequest);

    /**
     * 更新启用停用状态
     */
    Map<String,Object> updateState(DataElementBean bean);
}
