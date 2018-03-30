package com.fable.industry.bussiness.mapper.sysservicelog;


import com.fable.industry.bussiness.model.syslogs.ServiceLog;

import java.util.List;

public interface SysServiceLogMapper {
    /**
     * 查询服务日志分页列表
     */
    List<ServiceLog> queryServiceLogs(ServiceLog serviceLog);

    /**
     * 新增系统服务日志
     */
    int insertServiceLog(ServiceLog serviceLog);
}
