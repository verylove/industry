package com.fable.industry.bussiness.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fable.industry.bussiness.model.bean.SysUserBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;


public class LoginIntercepotor implements HandlerInterceptor{

	/** 
	  * Handler执行完成之后调用这个方法 
	  */
	@Override
	 public void afterCompletion(HttpServletRequest request, 
	   HttpServletResponse response, Object handler, Exception exc) 
	   throws Exception {
		 
	 } 
	  
	 /** 
	  * Handler执行之后，ModelAndView返回之前调用这个方法 
	  */
	 @Override
	 public void postHandle(HttpServletRequest request, HttpServletResponse response, 
		  Object handler, ModelAndView modelAndView) throws Exception {
		 
	 } 
	  
	 /** 
	  * Handler执行之前调用这个方法 
	  */
	 @Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
	   Object handler) throws Exception {
		  //获取请求的URL 
		  String url = request.getRequestURI(); 
		  //URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制 
		  if(url.indexOf("/login")>=0 || url.indexOf("/code")>=0){
			  return true;
		  } 
		  //获取Session 
		  HttpSession session = request.getSession(); 
		  SysUserBean sysUserBean = (SysUserBean)session.getAttribute("userInfo");
		  if(sysUserBean != null){
			  return true;
		  } 
		  //不符合条件的，跳转到登录界面
		  PrintWriter out = response.getWriter();
		  out.write("<script>window.parent.location.href='" + request.getContextPath() + "/index/toMain'</script>");
//		  request.getRequestDispatcher("/WEB-Ilogin.jsp").forward(request, response);
		  return false;
	  }

}
