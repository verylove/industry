package com.fable.industry.bussiness.mapper.resci;

import java.util.List;
import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/3/13
 */
public interface StatisticsMapper {
    /**
     * 获取各个行业分类占比数据
     * @return
     */
    List<Map<String,Object>> getIndustryData();

    /**
     * 获取各个主题分类占比数据
     * @return
     */
    List<Map<String,Object>> getTopicData();

    /**
     * 根据年份获取发布数据
     * @param year
     * @return
     */
    List<Map<String,Object>> getReleaseData(String year);

    /**
     * 根据年份获取撤销数据
     * @param year
     * @return
     */
    List<Map<String,Object>> getRevokeData(String year);
}
