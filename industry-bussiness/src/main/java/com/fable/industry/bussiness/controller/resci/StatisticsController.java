package com.fable.industry.bussiness.controller.resci;

import com.fable.industry.bussiness.service.resci.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/3/13
 */
@RequestMapping("/statistics")
@Controller
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/elementDataPage")
    public String elementDataPage() {
        return "statisticalAnalysis/dataStatisticalAnalysis/dataStatisticalAnalysis";
    }

    @RequestMapping("/elementData")
    @ResponseBody
    public Map<String,Object> elementData(@RequestBody Map<String,String> map) {
        return statisticsService.elementData(map.get("year"));
    }

}
