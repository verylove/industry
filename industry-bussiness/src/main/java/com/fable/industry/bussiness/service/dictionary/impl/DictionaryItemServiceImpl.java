package com.fable.industry.bussiness.service.dictionary.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.bussiness.mapper.dictionary.DictionaryItemMapper;
import com.fable.industry.bussiness.model.bean.DictionaryItemBean;
import com.fable.industry.bussiness.service.dictionary.DictionaryItemService;
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
public class DictionaryItemServiceImpl implements DictionaryItemService {

    @Autowired
    private DictionaryItemMapper dictionaryItemMapper;

    @Override
    public PageResponse listDictionar(PageRequest<DictionaryItemBean> pageRequest) {
        DictionaryItemBean dictionaryItemBean = pageRequest.getParam();
        Page<DictionaryItemBean> data = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        dictionaryItemMapper.listDictionar(dictionaryItemBean);
        return PageResponse.wrap(data, true, "0", "success");
    }

    @Override
    public Map<String, Object> addItemCheck(DictionaryItemBean bean) {
        int count = dictionaryItemMapper.getByNameAndDictId(bean);
        if (count > 0) {
            return Result.fail("该字典项已存在");
        }
        return Result.success("可以插入");
    }

    @Override
    public Map<String, Object> addItem(DictionaryItemBean bean) {
        int code = dictionaryItemMapper.insertItem(bean);
        if (code <= 0) {
            return Result.fail("插入字典项失败");
        }
        return Result.success("插入字典项成功");
    }

    @Override
    public Map<String, Object> updateItem(DictionaryItemBean bean) {
        int code = dictionaryItemMapper.updateItem(bean);
        if (code <= 0) {
            return Result.fail("修改字典项失败");
        }
        return Result.success("修改字典项成功");
    }

    @Override
    public Map<String, Object> itemDeleteCheck(String ids) {
        List<String> stateList = dictionaryItemMapper.getItemByState(ids);
        if (!stateList.isEmpty()) {
            StringBuilder itemName = new StringBuilder();
            for (String name : stateList) {
                itemName.append(name).append("、");
            }
            return Result.fail("字典项："+itemName.substring(0,itemName.length()-1)+" 属于系统默认字典项，" +
                    "不可操作");
        }
        return Result.success("可以操作");
    }

    @Override
    public Map<String, Object> deleteItem(String ids) {
        int code = dictionaryItemMapper.deleteItem(ids);
        if (code <= 0) {
            return Result.fail("删除字典项失败");
        }
        return Result.success("删除字典项成功");
    }

    @Override
    public Map<String, Object> queryItemById(Integer id) {
        return dictionaryItemMapper.queryItemById(id);
    }

    @Override
    public List<DictionaryItemBean> dict(Integer classId) {
        return dictionaryItemMapper.dict(classId);
    }

    @Override
    public DictionaryItemBean queryIdByName(Integer classId, String itemName) {
        return dictionaryItemMapper.queryIdByName(classId, itemName);
    }
}
