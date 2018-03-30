package com.fable.industry.bussiness.controller.main;

import com.fable.industry.api.page.Result;
import com.fable.industry.api.utils.CaptchaUtil;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.model.bean.SysUserBean;
import com.fable.industry.bussiness.service.main.MainService;
import com.fable.industry.bussiness.service.systemManage.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-28 14:24
 * @description 首页行业分类
 */
@Controller
@RequestMapping("/index")
public class MainController {
    @Autowired
    private UserManageService userManageService;

    @Autowired
    private MainService mainService;

    /**
     * @description 行业分类
     */
    @RequestMapping("/classify")
    @ResponseBody
    public List<Map<String, Object>> queryClassify() {
        return mainService.queryClassify();
    }

    /**
     * @description 查询行业分类下面的资源名称
     */
    @RequestMapping("/queryResci")
    @ResponseBody
    public Map<Integer, List<ResciCatalogUEBean>> queryResci() {
        return mainService.queryResci();
    }

    /**
     * @date 2018/3/1
     * @description 首页左侧菜单查询
     */
    @RequestMapping("/queryMenu")
    @ResponseBody
    public List<Map<String, Object>> queryMenuByRoleId(HttpSession session) {
        SysUserBean userInfo = (SysUserBean) session.getAttribute("userInfo");
        return mainService.queryMenuByRoleId(userInfo.getRoleId());
    }

    /**
     * @author Wangbaoan
     * @date 2018/2/9
     * @description 用户登录
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody SysUserBean sysUserBean, HttpSession session, HttpServletResponse response) throws Exception {
        //校验验证码
        if (!session.getAttribute("code").toString().equalsIgnoreCase(sysUserBean.getCode())) {
            return Result.fail("验证码错误！");
        }
        SysUserBean userInfo = userManageService.queryUserByName(sysUserBean.getLogin());
        if (userInfo != null) {
            //校验用户是否锁定
            if ("1".equals(userInfo.getLockStatus())) {
                return Result.fail("该用户已经锁定！");
            }
            if (!sysUserBean.getPassword().equals(userInfo.getPassword())) {
                return Result.fail("用户名或密码错误！");
            }
            userManageService.updateLoginTime(userInfo);
            //在Session里保存信息
            session.setAttribute("userInfo", userInfo);
            return Result.success("登录成功！");
        }
        return Result.fail("没有找到此用户！");
    }

    /**
     * @param session
     * @author Wangbaoan
     * @date 2018/2/9
     * @description 退出登录
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index/goLogin";
    }

    /**
     * @author Wangbaoan
     * @date 2018/2/9
     * @description 跳转登录页面
     */
    @RequestMapping("/goLogin")
    public ModelAndView goLogin() {
        return new ModelAndView("login");
    }

    /**
     * @description 跳转首页
     */
    @RequestMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("main");
        HttpSession session=request.getSession();
        SysUserBean sysUserBean=(SysUserBean)session.getAttribute("userInfo");
        mv.addObject("login",sysUserBean.getUserName());
        return mv;
    }

    /**
     * @description 跳转首页
     */
    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home/businessCatalog");
        return mv;
    }

    /**
     * @author Wangbaoan
     * @date 2018/2/9
     * @description 验证码
     */
    @RequestMapping("/code")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        CaptchaUtil util = CaptchaUtil.Instance();
        // 将验证码输入到session中，用来验证
        String code = util.getString();
        request.getSession().setAttribute("code", code);
        // 输出打web页面
        ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
    }
}
