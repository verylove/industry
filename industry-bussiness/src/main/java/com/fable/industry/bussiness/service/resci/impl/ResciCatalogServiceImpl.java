package com.fable.industry.bussiness.service.resci.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.mapper.resci.ResciCatalogMapper;
import com.fable.industry.bussiness.model.bean.ResciCatalogBean;
import com.fable.industry.bussiness.service.resci.ResciCatalogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/2/2
 */
@Service
@Transactional
public class ResciCatalogServiceImpl implements ResciCatalogService {

    @Autowired
    private ResciCatalogMapper resciCatalogMapper;

    @Override
    public PageResponse listCatalog(PageRequest<ResciCatalogBean> pageRequest) {
        ResciCatalogBean bean = pageRequest.getParam();
        Page<ResciCatalogBean> data = PageHelper.startPage(pageRequest.getPageNo(),pageRequest.getPageSize());
        resciCatalogMapper.queryListCatalog(bean);
        return PageResponse.wrap(data,true,"0","success");
    }

    @Override
    public Map<String, Object> addCatalog(ResciCatalogBean bean) {
        int code = resciCatalogMapper.insertCatalog(bean);
        if (code <= 0) {
            return Result.fail("插入目录失败");
        }
        return Result.success("插入目录成功");
    }

    @Override
    public Map<String, Object> updateCatalog(ResciCatalogBean bean) {
        int code = resciCatalogMapper.updateCatalog(bean);
        if (code <= 0) {
            return Result.fail("修改目录失败");
        }
        return Result.success("修改目录成功");
    }

    @Override
    public Map<String, Object> deleteCatalogCheck(String ids) {
        List<String> cataLogNameList = resciCatalogMapper.getCataLogName(ids);
        if (!cataLogNameList.isEmpty()) {
            StringBuilder cataLogNames = new StringBuilder();
            for (String name :
                    cataLogNameList) {
                cataLogNames.append(name).append("、");
            }
            return Result.fail("资源目录："+cataLogNames.toString().substring
                    (0,cataLogNames.length()-1)+"下已有编目不能删除");
        }
        return Result.success("目录可删除");
    }

    @Override
    public Map<String, Object> deleteCatalog(String ids) {
        int code = resciCatalogMapper.deleteCatalog(ids);
        if (code <= 0) {
            return Result.fail("删除目录失败");
        }
        return Result.success("删除目录成功");
    }

    @Override
    public Map<String, Object> queryCatalogById(Integer id) {
        return resciCatalogMapper.queryCatalogById(id);
    }

    @Override
    public List<TreeRsult> catalogTree() {
        return resciCatalogMapper.catalogTree();
    }


}
