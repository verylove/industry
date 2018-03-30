package com.fable.industry.bussiness.controller.resciPublish;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.bussiness.model.bean.DataElementBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.service.resciPublish.ResciPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-05 11:24
 * @description 资源发布
 */
@RequestMapping("/resciPublish")
@Controller
public class ResciPublishController {
    @Autowired
    private ResciPublishService resciPublishService;

    /**
     * @description 跳转资源发布列表页面
     */
    @RequestMapping("/resciPublishView")
    public ModelAndView resciPublishView() {
        ModelAndView mv = new ModelAndView("interconnectManage/resourcePublish/resourcePublish");
        return mv;
    }

    /**
     * @description 跳转资源发布详情列表页面
     */
    @RequestMapping("/resciPublishDetailsView")
    public ModelAndView resciPublishDetailsView() {
        ModelAndView mv = new ModelAndView("interconnectManage/resourcePublish/resourcePublishView");
        return mv;
    }

    /**
     * @description 跳转资源发布数据项详情页面
     */
    @RequestMapping("/resciPublishDataElementView")
    public ModelAndView resciPublishDataElementView() {
        ModelAndView mv = new ModelAndView("interconnectManage/resourcePublish/resourceProductView");
        return mv;
    }

    /**
     * @param request
     * @description 资源发布列表查询
     */
    @RequestMapping("/listResciCatalogue")
    @ResponseBody
    public PageResponse<ResciCatalogUEBean> listResciCatalogue(@RequestBody PageRequest<ResciCatalogUEBean> request) {
        return resciPublishService.listResciCatalogue(request);
    }

    /**
     * @param map
     * @description 根据资源编目Id查询资源
     */
    @RequestMapping("/queryResciCatalogueByCatalogueId")
    @ResponseBody
    public ResciCatalogUEBean queryResciCatalogueByCatalogueId(@RequestBody Map<String, Object> map) {
        Integer id = Integer.parseInt(map.get("id").toString());
        return resciPublishService.queryResciCatalogueByCatalogueId(id);
    }

    /**
     * @param map
     * @description 根据资源编目Id查询数据项信息
     */
    @RequestMapping("/queryDataElementByCatalogueId")
    @ResponseBody
    public List<DataElementBean> queryDataElementByCatalogueId(@RequestBody Map<String, Object> map) {
        Integer id = Integer.parseInt(map.get("id").toString());
        return resciPublishService.queryDataElementByCatalogueId(id);
    }

    /**
     * @param
     * @description oid 编码查询
     */
    @RequestMapping("/queryOID")
    @ResponseBody
    public List<Map<String, Object>> queryOID() {
        return resciPublishService.queryOID();
    }

}
