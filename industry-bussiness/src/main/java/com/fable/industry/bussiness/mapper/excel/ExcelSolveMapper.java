package com.fable.industry.bussiness.mapper.excel;

import java.util.Collection;
import java.util.Map;

public interface ExcelSolveMapper {

    Collection<Object> getCatalogData(Map<String, Object> map);

    Collection<Object> getDataElementData(Map<String, Object> map);

    Collection<Map<String,String>> getServiceLogData(Map<String, Object> map);
}
