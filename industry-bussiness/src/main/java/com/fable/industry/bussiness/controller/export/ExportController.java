package com.fable.industry.bussiness.controller.export;

import com.fable.industry.bussiness.service.excel.ExcelSolveService;
import com.fable.industry.bussiness.service.resci.ResciCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/2/27
 */
@RequestMapping("/exportController")
@Controller
public class ExportController {
    @Autowired
    private ExcelSolveService excelSolveService;

    /**
     * @author jiangmingjun
     * @date 2018/3/1
     * @description 目录导入页面
     */
    @RequestMapping("/catalogExportPage")
    public String catalogExportPage() {
        return "metadataManage/catalogManage/catalogImport";
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/27
     * @description 目录导出
     */
    @RequestMapping("/catalogExport")
    public void catalogExport(HttpServletResponse response, @RequestParam Map<String, Object> map) {
        excelSolveService.catalogExport(response, map);
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/27
     * @description 目录导入
     */
    @RequestMapping("/catalogImport")
    @ResponseBody
    public Map<String, Object> catalogImport(@RequestParam("file") CommonsMultipartFile file,
                                             HttpServletRequest request, @RequestParam("fieldId") String field) {
        return excelSolveService.catalogImport(file, request, field);
    }

    /**
     *@author jiangmingjun
     *@date 2018/2/27
     *@description 编目导出
     */

    /**
     *@author jiangmingjun
     *@date 2018/2/27
     *@description 编目导入
     */

    /**
     * @author jiangmingjun
     * @date 2018/2/27
     * @description 数据元导出
     */
    @RequestMapping("/dataElementExport")
    @ResponseBody
    public void dataElementExport(HttpServletResponse response, @RequestParam Map<String, Object> map) {
        excelSolveService.dataElementExport(response, map);
    }

    /**
     * @author jiangmingjun
     * @date 2018/2/27
     * @description 数据元导入页面
     */
    @RequestMapping("/dataElementImportPage")
    public String dataElementImportPage() {
        return "metadataManage/dataElementManage/dataElementImport";
    }

    /**
     * @author jiangmingjun
     * @date 2018/3/2
     * @description 数据元导入
     */
    @RequestMapping("/dataElementImport")
    @ResponseBody
    public Map<String, Object> dataElementImport(@RequestParam("file") CommonsMultipartFile file,
                                                 HttpServletRequest request) {
        return excelSolveService.dataElementImport(file, request);
    }

    /**
     * @author Wangbaoan
     * @date 2018/3/2
     * @description 服务日志导出
     */
    @RequestMapping("/serviceLogExport")
    public void serviceLogExport(HttpServletResponse response, @RequestParam Map<String, Object> map) {
        excelSolveService.serviceLogExport(response, map);
    }

    /**
     * @author Wangbaoan
     * @date 2018/3/6
     * @description 资源登记导入页面
     */
    @RequestMapping("/resciRegisterImportPage")
    public ModelAndView resciRegisterImport(@RequestParam Map<String, Object> map) {
        ModelAndView mv = new ModelAndView();
        Integer catalogueId = Integer.parseInt(map.get("catalogueId").toString());
        mv.addObject("catalogueId", catalogueId);
        mv.setViewName("interconnectManage/resourceRegister/resourceRegisterImport");
        return mv;
    }

    /**
     * @author Wangbaoan
     * @date 2018/3/6
     * @description 资源登记导入
     */
    @RequestMapping("/resciRegisterImport")
    @ResponseBody
    public Map<String, Object> resciRegisterImport(@RequestParam("file") CommonsMultipartFile file,
                                                   HttpServletRequest request, @RequestParam("catalogueId") String catalogueId) {
        return excelSolveService.resciRegisterImport(file, request, catalogueId);
    }

    /**
     * @author Wangbaoan
     * @date 2018/3/6
     * @description 资源登记导出
     */
    @RequestMapping("/resciRegisterExport")
    public void resciRegisterExport(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String, Object> map) {
        excelSolveService.resciRegisterExport(response, request, map);
    }
}
