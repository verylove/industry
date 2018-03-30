package com.fable.industry.bussiness.mapper.sysconf;

import com.fable.industry.bussiness.model.common.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysConfigMapper {

	public List<SysConfig> getConfByTypeID(int typeID);

	public String getConfParamValue(@Param("type") Integer typeID,
                                    @Param("paraKey") String paramKey);
}