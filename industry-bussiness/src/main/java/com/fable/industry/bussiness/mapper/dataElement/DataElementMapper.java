package com.fable.industry.bussiness.mapper.dataElement;

import com.fable.industry.bussiness.model.bean.DataElementBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DataElementMapper {

    List<Map<String,Object>> listData(DataElementBean bean);

    int getDataByNameAndVersion(DataElementBean bean);

    int insertData(DataElementBean bean);

    int updateData(DataElementBean bean);

    int deleteData(@Param("ids") String ids);

    Map<String,String> queryDataById(Integer id);

    List<Map<String, Object>> queryDataByM();

    int updateState(DataElementBean bean);
}
