package com.fable.industry.bussiness.service.resci.impl;

import com.fable.industry.bussiness.mapper.resci.StatisticsMapper;
import com.fable.industry.bussiness.service.resci.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/3/13
 */
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public Map<String, Object> elementData(String year) {
        Map<String,Object> map = new HashMap<>(4);
        List<Map<String,Object>> industryData = statisticsMapper.getIndustryData();
        List<Map<String,Object>> topicData = statisticsMapper.getTopicData();
        List<Map<String,Object>> releaseData = statisticsMapper.getReleaseData(year);
        List<Map<String,Object>> revokeData = statisticsMapper.getRevokeData(year);
        map.put("industryData",industryData);
        map.put("topicData",topicData);
        map.put("releaseData",releaseData);
        map.put("revokeData",revokeData);
        return map;
    }
}
