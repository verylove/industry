package com.fable.industry.bussiness.service.resciPublish.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.mapper.resciPublish.ResciPublishMapper;
import com.fable.industry.bussiness.mapper.resciRegister.ResciRegisterMapper;
import com.fable.industry.bussiness.model.bean.DataElementBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.model.bean.ResciWidgetBean;
import com.fable.industry.bussiness.service.resciPublish.ResciPublishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-05 11:25
 * @description 资源发布
 */
@Service
@Transactional
public class ResciPublishServiceImpl implements ResciPublishService {
    @Autowired
    private ResciPublishMapper resciPublishMapper;

    @Override
    public PageResponse<ResciCatalogUEBean> listResciCatalogue(PageRequest<ResciCatalogUEBean> request) {
        ResciCatalogUEBean resciCatalogueBean = request.getParam();
        Page<ResciCatalogUEBean> data = PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<ResciCatalogUEBean> list = resciPublishMapper.listResciCatalogue(resciCatalogueBean);
        for (int i = 0; i < list.size(); i++) {
            String createTime = resciPublishMapper.queryCreateTime(list.get(i).getId());
            if (createTime != null && !createTime.equals("")) {
                list.get(i).setCreateTime(createTime);
            }
        }
        return PageResponse.wrap(data, true, "0", "success");
    }

    @Override
    public ResciCatalogUEBean queryResciCatalogueByCatalogueId(Integer id) {
        ResciCatalogUEBean resciCatalogUEBean = resciPublishMapper.queryResciCatalogueByCatalogueId(id);
        if ("0".equals(resciCatalogUEBean.getResciLevel())) {
            resciCatalogUEBean.setResciLevel("完全共享");
        }
        if ("1".equals(resciCatalogUEBean.getResciLevel())) {
            resciCatalogUEBean.setResciLevel("部分共享");
        }
        String createTime = resciPublishMapper.queryCreateTime(resciCatalogUEBean.getId());
        resciCatalogUEBean.setCreateTime(createTime);
        return resciCatalogUEBean;
    }

    @Override
    public List<DataElementBean> queryDataElementByCatalogueId(Integer id) {
        return resciPublishMapper.queryDataElementByCatalogueId(id);
    }

    @Override
    public List<Map<String, Object>> queryOID() {
        return resciPublishMapper.queryOID();
    }
}
