package com.fable.industry.bussiness.controller.systemLog;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.syslogs.ServiceLog;
import com.fable.industry.bussiness.service.syslog.sysservicelog.SysSericeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther Wangbaoan
 * @Date 2018-02-26 10:10
 * @Description 系统服务日志
 */
@Controller
@RequestMapping("/serviceManager")
public class SysServiceLogController {

    @Autowired
    private SysSericeLogService sysSericeLogService;

    /**
     * @Description: 跳转服务日志列表页面
     */
    @RequestMapping("/serviceLogView")
    public ModelAndView userView() {
        ModelAndView mv = new ModelAndView("logAudit/serviceLog/serviceLog");
        return mv;
    }

    /**
     * @Param: pageRequest
     * @Description: 服务日志列表
     */
    @RequestMapping("/listServiceLog")
    @ResponseBody
    public PageResponse<ServiceLog> listServiceLog(@RequestBody PageRequest<ServiceLog> pageRequest) {
        return sysSericeLogService.getPageSericeLogs(pageRequest);
    }
}
