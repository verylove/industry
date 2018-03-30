package com.fable.industry.bussiness.interceptor.module;

import com.fable.industry.bussiness.model.bean.SysUserBean;
import com.fable.industry.bussiness.model.common.Constants;
import com.fable.industry.bussiness.model.syslogs.Module;
import com.fable.industry.bussiness.model.syslogs.ServiceLog;
import com.fable.industry.bussiness.model.syslogs.SysOperLog;
import com.fable.industry.bussiness.service.sysconf.SysConfigService;
import com.fable.industry.bussiness.service.syslog.sysmodule.SysModuleService;
import com.fable.industry.bussiness.service.syslog.sysoperlog.SysOperLogService;
import com.fable.industry.bussiness.service.syslog.sysservicelog.SysSericeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author duyang
 * 日志拦截处理
 */
public class ModuleLogInterceptor implements HandlerInterceptor {

    @Autowired
    private SysOperLogService sysOperLogService;

    @Autowired
    private SysSericeLogService sysSericeLogService;

    @Autowired
    private SysModuleService sysModuleService;

    @Autowired
    private SysConfigService sysConfigService;

    private boolean isLog = true;

    private SysUserBean sysUserBean;

    private Date requestTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        try {
            if (!isLog) {
                return true;
            }
            String path = request.getServletPath();
            //过滤资源文件
            if (path.startsWith("/js/") || path.endsWith(".js") || path.endsWith(".css") ||
                    path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".png") || path.endsWith("/noright.html")) {
                return true;
            }
            //不使用缓存
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            /* 当前登陆用户 */
            sysUserBean = (SysUserBean) request.getSession().getAttribute(Constants.USER.USERSESSION);
            requestTime = new Date();
        } catch (BadSqlGrammarException bade) {
            bade.printStackTrace();
            isLog = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        afterCompletionMethod(request, response, handler, ex);
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    private void afterCompletionMethod(HttpServletRequest request,
                                       HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String path = request.getServletPath();
        //获取URL、方法名
        String[] ss = null;
		if(handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
			ss = getReflectInfo(hm);
		}
        //用户已经登录
        String value = sysConfigService.getConfParamValue(1, "isLog");
        if (sysUserBean != null && Constants.ISORNOT.YES.equals(value)) {
            //记录系统URL日志
            Module module = sysModuleService.getModuleNameByCode(path);
            if (module != null) {
                //服务日志
                if (Constants.LOG_ADDRESS.OIDADDRESS.equals(path)) {
                    ServiceLog serviceLog = new ServiceLog();
                    serviceLog.setServiceName(module.getModuleName());
                    serviceLog.setMaintenance(sysUserBean.getUserName());
                    serviceLog.setRequestTime(sdf.format(requestTime));
                    serviceLog.setResponseTime(sdf.format(new Date()));
                    serviceLog.setUrl(ss[0]);
                    serviceLog.setResult(Constants.ISSUCCESS.SUCCESS);
                    serviceLog.setIp(getClientAddr(request));
                    if (ex != null) {
                        serviceLog.setResult(Constants.ISSUCCESS.ERROR);
                        serviceLog.setNote(ex.getMessage());
                    }
                    sysSericeLogService.insertSericeLog(serviceLog);
                } else { //操作日志
                    SysOperLog sysOperLog = new SysOperLog();
                    sysOperLog.setUserId(sysUserBean.getUserId());
                    sysOperLog.setUserName(sysUserBean.getUserName());
                    sysOperLog.setCreateTime(sdf.format(new Date()));
                    sysOperLog.setClientAgent(request.getRemoteHost());
                    sysOperLog.setModuleId(module.getId());
                    sysOperLog.setModuleName(module.getModuleName());
                    sysOperLog.setIp(getClientAddr(request));
                    sysOperLog.setResult(Constants.ISSUCCESS.SUCCESS);
                    if (ex != null) {
                        sysOperLog.setResult(Constants.ISSUCCESS.ERROR);
                        sysOperLog.setNote(ex.getMessage());
                    }
                    sysOperLogService.insertOperLog(sysOperLog);
                }
            }
        }
    }

    /**
     * 获取客户端IP地址
     *
     * @param request
     * @return
     */
    private String getClientAddr(HttpServletRequest request) {
        String client_ip = request.getHeader("x-forwarded-for");
        if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("Proxy-Client-IP");
        }
        if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getRemoteAddr();
            if(client_ip.equals("127.0.0.1") || client_ip.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                client_ip = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if(client_ip != null && client_ip.length() > 15){
            if(client_ip.indexOf(",") > 0){
                client_ip = client_ip.substring(0,client_ip.indexOf(","));
            }
        }
        return client_ip;
    }

    /**
     * 根据HandlerMethod对象获取反射信息
     *
     * @param handle HandlerMethod对象
     * @return URL、完整的方法名
     */
    private String[] getReflectInfo(HandlerMethod handle) {
        String[] ss = new String[2];
        try {
            String url = "";
            //类地址
            String urlCls = "";
            //方法地址
            String urlMtd = "";
            final Class<?> clazz = handle.getBeanType();
            Annotation cc = clazz.getAnnotation(Controller.class);
            if (cc != null) {
                //获取类的@RequestMapping
                Annotation rm = clazz.getAnnotation(RequestMapping.class);
                if (rm != null) {
                    urlCls = ((RequestMapping) rm).value()[0];
                }
            }
            Method method = handle.getMethod();
            //获取方法的@RequestMapping注解
            RequestMapping rm11 = method.getAnnotation(RequestMapping.class);
            if (rm11 != null) {
                urlMtd = rm11.value()[0];
            }
            if (!urlCls.endsWith("/") && !urlMtd.startsWith("/")) {
                url = urlCls + "/" + urlMtd;
            } else {
                url = urlCls + urlMtd;
            }
            ss[0] = url;
            ss[1] = clazz.getName() + "." + method.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ss;
    }

}
