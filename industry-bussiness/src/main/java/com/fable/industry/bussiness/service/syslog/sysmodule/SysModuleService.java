package com.fable.industry.bussiness.service.syslog.sysmodule;

import com.fable.industry.bussiness.model.syslogs.Module;
import com.fable.industry.bussiness.model.syslogs.SysOperLog;

public interface SysModuleService {
    /**
     * 通过编码获取Module
     * @param code
     * @return
     */
    public Module getModuleNameByCode(String code);

}
