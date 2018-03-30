package com.fable.industry.bussiness.service.syslog.sysoperlog;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.syslogs.SysOperLog;

import java.util.Map;

public interface SysOperLogService {
    /**
     * 查询操作日志分页列表
     * @return
     */
    PageResponse<SysOperLog> getPageOpeLogs(PageRequest<SysOperLog> pageRequest);

    /**
     * 新增系统操作日志
     * @param sysOperLog
     */
    Map<String,Object> insertOperLog(SysOperLog sysOperLog);
}
