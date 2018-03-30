package com.fable.industry.bussiness.controller.systemLog;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.syslogs.SysOperLog;
import com.fable.industry.bussiness.service.syslog.sysoperlog.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther Wangbaoan
 * @Date 2018-02-26 09:56
 * @Description 系统操作日志
 */
@Controller
@RequestMapping("/operationManager")
public class SysOperationLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * @Description: 跳转操作日志列表页面
     */
    @RequestMapping("/operationLogView")
    public ModelAndView userView() {
        ModelAndView mv = new ModelAndView("logAudit/operationLog/operationLog");
        return mv;
    }

    /**
     * @Param: pageRequest
     * @Description: 操作日志列表查询
     */
    @RequestMapping("/listOperationLog")
    @ResponseBody
    public PageResponse<SysOperLog> listOperationLog(@RequestBody PageRequest<SysOperLog> pageRequest) {
        return sysOperLogService.getPageOpeLogs(pageRequest);
    }
}
