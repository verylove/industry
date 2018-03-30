package com.fable.industry.bussiness.service.syslog.sysservicelog;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.bussiness.mapper.sysservicelog.SysServiceLogMapper;
import com.fable.industry.bussiness.model.syslogs.ServiceLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysSericeLogServiceImpl implements SysSericeLogService {

    @Autowired
    private SysServiceLogMapper sysServiceLogMapper;


    @Override
    public PageResponse<ServiceLog> getPageSericeLogs(PageRequest<ServiceLog> request) {
        ServiceLog serviceLog = request.getParam();
        if (!"".equals(serviceLog.getCreateTimeStart())&&serviceLog.getCreateTimeStart()!=null){
            serviceLog.setCreateTimeStart(serviceLog.getCreateTimeStart()+" 00:00:00");
        }
        if (!"".equals(serviceLog.getCreateTimeEnd())&&serviceLog.getCreateTimeEnd()!=null){
            serviceLog.setCreateTimeEnd(serviceLog.getCreateTimeEnd()+" 23:59:59");
        }
        Page<ServiceLog> data = PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<ServiceLog> list = sysServiceLogMapper.queryServiceLogs(serviceLog);
        for (int i = 0; i < list.size(); i++) {
            if ("1".equals(list.get(i).getResult())) {
                list.get(i).setResult("成功");
            }
            if ("2".equals(list.get(i).getResult())) {
                list.get(i).setResult("失败");
            }
        }
        return PageResponse.wrap(data, true, "0", "success");
    }

    @Override
    public Map<String, Object> insertSericeLog(ServiceLog serviceLog) {
        int code = sysServiceLogMapper.insertServiceLog(serviceLog);
        if (code <= 0) {
            return Result.fail("插入服务日志失败");
        }
        return Result.success("插入服务日志成功");
    }
}
