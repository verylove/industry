package com.fable.industry.bussiness.service.sysconf;


import java.util.Map;

public interface SysConfigService {
	
	public Map<String,String> getConfByTypeID(int typeID);
	
	public String getConfParamValue(int typeID, String paramKey);
}