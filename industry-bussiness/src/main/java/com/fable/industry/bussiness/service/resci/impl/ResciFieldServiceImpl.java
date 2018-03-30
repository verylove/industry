package com.fable.industry.bussiness.service.resci.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.bussiness.mapper.resci.ResciFieldMapper;
import com.fable.industry.bussiness.model.bean.ResciFieldBean;
import com.fable.industry.bussiness.service.resci.ResciFieldService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/26
 */
@Service
@Transactional
public class ResciFieldServiceImpl implements ResciFieldService {

    @Autowired
    private ResciFieldMapper resciFieldMapper;

    @Override
    public PageResponse<ResciFieldBean> findResciFieldList(PageRequest<ResciFieldBean> pageRequest) {
        ResciFieldBean bean = pageRequest.getParam();
        Page<ResciFieldBean> data = PageHelper.startPage(pageRequest.getPageNo(),pageRequest.getPageSize());
        resciFieldMapper.findResciFieldList(bean);
        return PageResponse.wrap(data,true,"0","success");
    }

    @Override
    public Map<String, Object> checkFieldName(String fieldName) {
        int count = resciFieldMapper.getFieldByName(fieldName);
        if (count > 0) {
            return Result.fail("域名称重复");
        }
        return Result.success("域名称唯一");
    }

    @Override
    public Map<String,Object> addResciField(ResciFieldBean bean) {
        int code = resciFieldMapper.insertResciField(bean);
        if (code <= 0 ) {
            return Result.fail("插入域失败");
        }
        return Result.success("插入域成功");
    }

    @Override
    public Map<String, Object> updateResciField(ResciFieldBean bean) {
        int code = resciFieldMapper.updateResciField(bean);
        if (code <= 0) {
            return Result.fail("修改域失败");
        }
        return Result.success("修改域成功");
    }

    @Override
    public Map<String, Object> queryResciFieldById(Integer resciFieldId) {
        return resciFieldMapper.queryResciFieldById(resciFieldId);
    }

    @Override
    public Map<String, Object> checkDelField(String resciFieldIds) {
        List<String> fieldNameList = resciFieldMapper.getFieldByIds(resciFieldIds);
        if (!fieldNameList.isEmpty()) {
            StringBuilder fieldNames = new StringBuilder();
            for (String name :
                 fieldNameList) {
                fieldNames.append(name).append("、");
            }
            return Result.fail("资源域："+fieldNames.toString().substring
                    (0,fieldNames.length()-1)+"下已有目录不能删除");
        }
        return Result.success("可以删除");
    }

    @Override
    public Map<String, Object> delResciField(String resciFieldIds) {
        int code = resciFieldMapper.delResciField(resciFieldIds);
        if (code <= 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Map<String,String> rootField() {
        return resciFieldMapper.rootField();
    }
}
