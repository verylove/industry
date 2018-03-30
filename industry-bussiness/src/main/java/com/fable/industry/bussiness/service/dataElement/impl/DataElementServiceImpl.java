package com.fable.industry.bussiness.service.dataElement.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.bussiness.mapper.dataElement.DataElementMapper;
import com.fable.industry.bussiness.mapper.dynamicform.resattributes.ResAttributesMapper;
import com.fable.industry.bussiness.model.bean.DataElementBean;
import com.fable.industry.bussiness.service.dataElement.DataElementService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/1/31
 */
@Service
@Transactional
public class DataElementServiceImpl implements DataElementService {

    @Autowired
    private DataElementMapper dataElementMapper;

    @Autowired
    private ResAttributesMapper resAttributesMapper;

    @Override
    public PageResponse listData(PageRequest<DataElementBean> pageRequest) {
        DataElementBean bean = pageRequest.getParam();
        Page<Map<String,Object>> data = PageHelper.startPage(pageRequest.getPageNo(),pageRequest.getPageSize());
        dataElementMapper.listData(bean);
        return PageResponse.wrap(data,true,"0","success");
    }

    @Override
    public Map<String, Object> addDataCheck(DataElementBean bean) {
        int count = dataElementMapper.getDataByNameAndVersion(bean);
        if (count > 0) {
            return Result.fail("该数据元已存在");
        }
        return Result.success("可以添加");
    }

    @Override
    public Map<String, Object> addData(DataElementBean bean) {
        int code = dataElementMapper.insertData(bean);
        if (code <= 0) {
            return Result.fail("插入数据元失败");
        }
        return Result.success("插入数据元成功");
    }

    @Override
    public Map<String, Object> updateData(DataElementBean bean) {
        int code = dataElementMapper.updateData(bean);
        if (code <= 0) {
            return Result.fail("更新数据失败");
        }
        return Result.success("更新数据成功");
    }

    @Override
    public Map<String, Object> deleteDataCheck(String ids) {
        int count = resAttributesMapper.getByDataElementId(ids);
        if (count > 0) {
            return Result.fail("该数据元已被引用");
        }
        return Result.success("可以删除");
    }

    @Override
    public Map<String, Object> deleteData(String ids) {
        int code = dataElementMapper.deleteData(ids);
        if (code <= 0) {
            return Result.fail("删除数据失败");
        }
        return Result.success("删除数据成功");
    }

    @Override
    public Map<String, String> queryDataById(Integer id) {
        return dataElementMapper.queryDataById(id);
    }

    @Override
    public PageResponse queryDataByM(PageRequest pageRequest) {
        Page<Map<String,Object>> data = PageHelper.startPage(pageRequest.getPageNo(),pageRequest.getPageSize());
        dataElementMapper.queryDataByM();
        return PageResponse.wrap(data,true,"0","success");
    }

    @Override
    public Map<String, Object> updateState(DataElementBean bean) {
        int code = dataElementMapper.updateState(bean);
        if (code <= 0) {
            return Result.fail("更新启用状态失败");
        }
        if ("0".equals(bean.getEnabledState())) {
            return Result.success("已启用");
        }
        return Result.success("已停用");
    }

}
