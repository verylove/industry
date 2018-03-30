package com.fable.industry.bussiness.service.syslog.sysoperlog;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.bussiness.mapper.sysoperlog.SysOperLogMapper;
import com.fable.industry.bussiness.model.syslogs.SysOperLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysOperLogServiceImpl implements SysOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;


    @Override
    public PageResponse<SysOperLog> getPageOpeLogs(PageRequest<SysOperLog> pageRequest) {
        SysOperLog sysOperLog = pageRequest.getParam();
        if (!"".equals(sysOperLog.getCreateTimeStart())&&sysOperLog.getCreateTimeStart()!=null){
            sysOperLog.setCreateTimeStart(sysOperLog.getCreateTimeStart()+" 00:00:00");
        }
        if (!"".equals(sysOperLog.getCreateTimeEnd())&&sysOperLog.getCreateTimeEnd()!=null){
            sysOperLog.setCreateTimeEnd(sysOperLog.getCreateTimeEnd()+" 23:59:59");
        }
        Page<SysOperLog> data = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        List<SysOperLog> list = sysOperLogMapper.queryOperLogs(sysOperLog);
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
    public Map<String, Object> insertOperLog(SysOperLog sysOperLog) {
        int code = sysOperLogMapper.insertOperLog(sysOperLog);
        if (code <= 0) {
            return Result.fail("插入域失败");
        }
        return Result.success("插入域成功");
    }
}
