package com.fable.industry.bussiness.service.dictionary.impl;

import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.bussiness.mapper.dictionary.DictionaryClassMapper;
import com.fable.industry.bussiness.model.bean.DictionaryClassBean;
import com.fable.industry.bussiness.service.dictionary.DictionaryClassService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/1/29
 */
@Service
@Transactional
public class DictionaryClassServiceImpl implements DictionaryClassService {

    @Autowired
    private DictionaryClassMapper dictionaryClassMapper;

    @Override
    public PageResponse<DictionaryClassBean> queryDictionarTree() {
        Page<DictionaryClassBean> data = PageHelper.startPage(1,0);
        dictionaryClassMapper.queryDictionarTree();
        return PageResponse.wrap(data,true,"0","success");
    }

    @Override
    public List<DictionaryClassBean> queryDictionarSelect() {
        return dictionaryClassMapper.queryDictionarSelect();
    }

    @Override
    public Map<String, Object> addDictionarCheck(DictionaryClassBean bean) {
        int count = dictionaryClassMapper.getDictionarByName(bean);
        if (count > 0) {
            return Result.fail("该字典类型已存在");
        }
        return Result.success("可以新增");
    }

    @Override
    public Map<String, Object> addDictionar(DictionaryClassBean bean) {
        int code = dictionaryClassMapper.insertDictionar(bean);
        if (code <= 0) {
            return Result.fail("新增字典分类失败");
        }
        return Result.success("新增字典分类成功");
    }

    @Override
    public Map<String, Object> updateDictionar(DictionaryClassBean bean) {
        int code = dictionaryClassMapper.updateDictionar(bean);
        if (code <= 0) {
            return Result.fail("修改字典分类失败");
        }
        return Result.success("修改字典分类成功");
    }

    @Override
    public Map<String, Object> checkDelDictionar(String ids) {
        int type = dictionaryClassMapper.getDictType(ids);
        if (type == 0) {
            return Result.fail("系统字典不可操作");
        }
        List<String> nameList = dictionaryClassMapper.getItemByClassId(ids);
        if (!nameList.isEmpty()) {
            StringBuilder dictionaryName = new StringBuilder();
            for (String name:
                    nameList) {
                dictionaryName.append(name).append("、");
            }
            return Result.fail(dictionaryName.substring(0,dictionaryName.length()-1)
                    +" 中已有字典项，不能删除");
        }
        return Result.success("可以删除");
    }

    @Override
    public Map<String, Object> deleteDictionar(String ids) {
        int code = dictionaryClassMapper.deleteDictionar(ids);
        if (code <= 0) {
            return Result.fail("删除字典项分类失败");
        }
        return Result.success("删除字典项分类成功");
    }

    @Override
    public Map<String, Object> queryDictionarById(Integer id) {
        return dictionaryClassMapper.queryDictionarById(id);
    }
}
