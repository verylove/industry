package com.fable.industry.bussiness.controller.resciRegister;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.ResciWidgetBean;
import com.fable.industry.bussiness.model.bean.SysUserBean;
import com.fable.industry.bussiness.service.dataElement.DataElementService;
import com.fable.industry.bussiness.service.resciRegister.ResciRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-01 14:06
 * @description 资源登记
 */
@RequestMapping("/resciRegister")
@Controller
public class ResciRegisterController {
    @Autowired
    private ResciRegisterService resciRegisterService;

    @Autowired
    private DataElementService dataElementService;

    /**
     * @description 跳转资源登记页面
     */
    @RequestMapping("/resciRegisterView")
    public ModelAndView resciRegisterView() {
        ModelAndView mv = new ModelAndView("interconnectManage/resourceRegister/resourceRegister");
        return mv;
    }

    /**
     * @description 跳转资源登记新增页面
     */
    @RequestMapping("/addResciRegisterView")
    public ModelAndView addResciRegisterView() {
        ModelAndView mv = new ModelAndView("interconnectManage/resourceRegister/addResourceRegister");
        return mv;
    }

    /**
     * @description 跳转资源登记修改页面
     */
    @RequestMapping("/updateResciRegisterView")
    public ModelAndView updateResciRegisterView() {
        ModelAndView mv = new ModelAndView("interconnectManage/resourceRegister/updateResourceRegister");
        return mv;
    }

    /**
     * @description 跳转资源登记查看页面
     */
    @RequestMapping("/resciRegisterDetail")
    public ModelAndView resciRegisterDetail() {
        ModelAndView mv = new ModelAndView("interconnectManage/resourceRegister/resourceRegisterView");
        return mv;
    }

    /**
     * @param map
     * @description 根据编目Id查询该资源的控件
     */
    @RequestMapping("/initResciPage")
    @ResponseBody
    public Map<String, Object> initResciPage(@RequestBody Map<String, Object> map) {
        Map<String, Object> resciWidgetBeanMap = new HashMap<>();
        Integer catalogueId = Integer.parseInt(map.get("catalogueId").toString());
        List<ResciWidgetBean> resciWidgetBeanList = resciRegisterService.getResciWidgetById(catalogueId);
        List<ResciWidgetBean> query = new ArrayList<>();
        //页面查询条件
        for (int i = 0; i < resciWidgetBeanList.size(); i++) {
            if (resciWidgetBeanList.get(i).getIsQuery() == 1) {
                resciWidgetBeanList.get(i).setRequired(0);
                query.add(resciWidgetBeanList.get(i));
            }
        }
        for (int i = 0; i < query.size(); i++) {
            Map<String, String> dataMap = dataElementService.queryDataById(query.get(i).getElementId());
            if (dataMap.get("dictionaryId") != null) {
                Integer dictionaryId = Integer.valueOf(String.valueOf(dataMap.get("dictionaryId")));
                if (dictionaryId != 0 && dictionaryId != null) {
                    query.get(i).setSelectUrl("/dictionarManager/dict");
                    query.get(i).setDictionaryId(dictionaryId);
                }
            }
        }
        //显示的列
        List<ResciWidgetBean> resciWidgetBeanList2 = resciRegisterService.getResciWidgetById(catalogueId);
        List<ResciWidgetBean> show = new ArrayList<>();
        for (int i = 0; i < resciWidgetBeanList2.size(); i++) {
            if (resciWidgetBeanList2.get(i).getIsTableDisplay() == 1) {
                show.add(resciWidgetBeanList2.get(i));
            }
        }
        for (int i = 0; i < show.size(); i++) {
            Map<String, String> dataMap = dataElementService.queryDataById(show.get(i).getElementId());
            if (dataMap.get("dictionaryId") != null) {
                Integer dictionaryId = Integer.valueOf(String.valueOf(dataMap.get("dictionaryId")));
                if (dictionaryId != 0 && dictionaryId != null) {
                    show.get(i).setSelectUrl("/dictionarManager/dict");
                    show.get(i).setDictionaryId(dictionaryId);
                }
            }
        }
        resciWidgetBeanMap.put("query", query);
        resciWidgetBeanMap.put("show", show);
        return resciWidgetBeanMap;
    }

    /**
     * @param pageRequest
     * @description 查询资源登记列表
     */
    @RequestMapping("/listResci")
    @ResponseBody
    public PageResponse<Map<String, String>> listResci(@RequestBody PageRequest<Map<String, String>> pageRequest, HttpServletRequest request) {
        return resciRegisterService.findResciRegister(pageRequest, request);
    }

    /**
     * @param map
     * @description 新增资源登记初始化组件
     */
    @RequestMapping("/addResciRegisterInit")
    @ResponseBody
    public List<ResciWidgetBean> addResciRegisterInit(@RequestBody Map<String, String> map) {
        Integer catalogueId = Integer.parseInt(map.get("catalogueId").toString());
        List<ResciWidgetBean> resciWidgetBeanList = resciRegisterService.getResciWidgetById(catalogueId);
        for (int i = 0; i < resciWidgetBeanList.size(); i++) {
            Map<String, String> dataMap = dataElementService.queryDataById(resciWidgetBeanList.get(i).getElementId());
            if (dataMap.get("dictionaryId") != null) {
                Integer dictionaryId = Integer.valueOf(String.valueOf(dataMap.get("dictionaryId")));
                if (dictionaryId != 0 && dictionaryId != null) {
                    resciWidgetBeanList.get(i).setSelectUrl("/dictionarManager/dict");
                    resciWidgetBeanList.get(i).setDictionaryId(dictionaryId);
                }
            }
        }
        return resciWidgetBeanList;
    }

    /**
     * @param map
     * @description 新增资源登记
     */
    @RequestMapping("/addResciRegister")
    @ResponseBody
    public Map<String, Object> addResciRegister(@RequestBody Map<String, String> map, HttpServletRequest request) {
        return resciRegisterService.addResciRegister(map, request);
    }

    /**
     * @param map
     * @description 根据id查看资源登记
     */
    @RequestMapping("/queryResciRegisterById")
    @ResponseBody
    public Map<String, String> queryResciRegisterById(@RequestBody Map<String, Object> map) {
        //获取选中的列的id也就是动态表的主键
        Integer id = Integer.parseInt(map.get("id").toString());
        //获取编目Id用来查询动态表名
        Integer catalogueId = Integer.parseInt(map.get("catalogueId").toString());
        return resciRegisterService.queryResciRegisterById(id, catalogueId);
    }


    /**
     * @param map
     * @description 修改资源登记
     */
    @RequestMapping("/updateResciRegister")
    @ResponseBody
    public Map<String, Object> updateResciRegister(@RequestBody Map<String, String> map) {
        return resciRegisterService.updateResciRegister(map);
    }

    /**
     * @param map
     * @description 检验资源是否可被修改
     */
    @RequestMapping("/checkUpdateResciRegister")
    @ResponseBody
    public Map<String, Object> checkUpdateResciRegister(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUserBean userInfo = (SysUserBean) session.getAttribute("userInfo");
        return resciRegisterService.checkUpdateResciRegister(userInfo, map);
    }

    /**
     * @param map
     * @description 删除资源登记
     */
    @RequestMapping("/deleteResciRegister")
    @ResponseBody
    public Map<String, Object> deleteResciRegister(@RequestBody Map<String, Object> map) {
        //获取选中的列的id也就是动态表的主键
        String id = map.get("id").toString();
        //获取编目Id用来查询动态表名
        Integer catalogueId = Integer.parseInt(map.get("catalogueId").toString());
        return resciRegisterService.deleteResciRegister(id, catalogueId);
    }

    /**
     * @param map
     * @description 检验资源是否可被删除
     */
    @RequestMapping("/checkDeleteResciRegister")
    @ResponseBody
    public Map<String, Object> checkDeleteResciRegister(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUserBean userInfo = (SysUserBean) session.getAttribute("userInfo");
        return resciRegisterService.checkDeleteResciRegister(userInfo, map);
    }

    /**
     * @description 资源登记树
     */
    @RequestMapping("/resciRegisteTree")
    @ResponseBody
    public List<TreeRsult> resciRegisteTree() {
        return resciRegisterService.queryResciRegisteTree();
    }
}
