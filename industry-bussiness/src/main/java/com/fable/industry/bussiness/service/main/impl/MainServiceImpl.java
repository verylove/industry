package com.fable.industry.bussiness.service.main.impl;

import com.fable.industry.bussiness.mapper.main.MainMapper;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.service.main.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-28 15:33
 */
@Service
@Transactional
public class MainServiceImpl implements MainService {
    @Autowired
    private MainMapper mainMapper;

    @Override
    public List<Map<String, Object>> queryClassify() {
        return mainMapper.queryClassify();
    }

    @Override
    public Map<Integer, List<ResciCatalogUEBean>> queryResci() {
        List<Map<String, Object>> classify = mainMapper.queryClassify();
        Map<Integer, List<ResciCatalogUEBean>> map = new HashMap<>();
        for (int i = 0; i < classify.size(); i++) {
            if (classify != null) {
                Integer id = Integer.valueOf(String.valueOf(classify.get(i).get("id")));
                List<ResciCatalogUEBean> list = mainMapper.queryResciById(id);
                map.put(id, list);
            }
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> queryMenuByRoleId(Integer roleId) {
        return mainMapper.queryMenuByRoleId(roleId);
    }
}
