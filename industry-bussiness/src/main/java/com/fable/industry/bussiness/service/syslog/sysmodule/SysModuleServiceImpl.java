package com.fable.industry.bussiness.service.syslog.sysmodule;

import com.fable.industry.bussiness.mapper.sysmodule.SysModuleMapper;
import com.fable.industry.bussiness.mapper.sysoperlog.SysOperLogMapper;
import com.fable.industry.bussiness.model.syslogs.Module;
import com.fable.industry.bussiness.model.syslogs.SysOperLog;
import com.fable.industry.bussiness.service.syslog.sysoperlog.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysModuleServiceImpl implements SysModuleService {

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Override
    public Module getModuleNameByCode(String code) {
        return sysModuleMapper.queryModuleNameByCode(code);
    }
}
