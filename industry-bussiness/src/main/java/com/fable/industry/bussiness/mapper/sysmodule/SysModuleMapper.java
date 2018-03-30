package com.fable.industry.bussiness.mapper.sysmodule;

import com.fable.industry.bussiness.model.syslogs.Module;

public interface SysModuleMapper {
    /**
     * 通过编码获取Module
     * @param code
     * @return
     */
    public Module queryModuleNameByCode(String code);

}
