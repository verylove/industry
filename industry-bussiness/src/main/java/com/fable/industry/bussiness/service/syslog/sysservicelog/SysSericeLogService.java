package com.fable.industry.bussiness.service.syslog.sysservicelog;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.syslogs.ServiceLog;

import java.util.Map;

public interface SysSericeLogService {
    /**
     * 查询服务日志分页列表
     * @return
     */
    PageResponse<ServiceLog> getPageSericeLogs(PageRequest<ServiceLog> request);

    /**
     * 新增服务日志
     * @param serviceLog
     */
    Map<String,Object> insertSericeLog(ServiceLog serviceLog);
}
