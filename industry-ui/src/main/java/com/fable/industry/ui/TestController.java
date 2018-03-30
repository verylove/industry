package com.fable.industry.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jiangmingjun
 * @create 2018/3/28
 */
@Controller
@RequestMapping("/dataManager")
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello";
    }
}
