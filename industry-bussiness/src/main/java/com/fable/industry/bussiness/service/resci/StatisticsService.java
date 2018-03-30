package com.fable.industry.bussiness.service.resci;

import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/3/13
 */
public interface StatisticsService {
    /**
     *  元数据统计分析
     * @param year
     * @return
     */
    Map<String,Object> elementData(String year);
}
