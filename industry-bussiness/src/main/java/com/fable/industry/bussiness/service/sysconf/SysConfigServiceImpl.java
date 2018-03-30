package com.fable.industry.bussiness.service.sysconf;

import com.fable.industry.bussiness.mapper.sysconf.SysConfigMapper;
import com.fable.industry.bussiness.model.common.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("sysConfigServiceImpl")
@Transactional
public class SysConfigServiceImpl implements SysConfigService {
	
	@Autowired
	SysConfigMapper sysConfigMapper;

	@Override
	public Map<String, String> getConfByTypeID(int typeID) {
		Map<String, String> result = new HashMap<String, String>();
		for (SysConfig sys : sysConfigMapper.getConfByTypeID(typeID)) {
			result.put(sys.getParaKey(), sys.getParaValue());
		}
		return result;
	}

	@Override
	public String getConfParamValue(int typeID, String paramKey) {
		return sysConfigMapper.getConfParamValue(typeID, paramKey);
	}

}