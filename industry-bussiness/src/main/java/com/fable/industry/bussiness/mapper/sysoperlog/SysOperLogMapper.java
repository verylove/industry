package com.fable.industry.bussiness.mapper.sysoperlog;


import com.fable.industry.bussiness.model.syslogs.SysOperLog;

import java.util.List;

public interface SysOperLogMapper {

    /**
     * 查询操作日志分页列表
     * @return
     */
    public List<SysOperLog> queryOperLogs(SysOperLog sysOperLog);

    /**
     * 新增系统操作日志
     * @param sysOperLog
     */
    public int insertOperLog(SysOperLog sysOperLog);
}
