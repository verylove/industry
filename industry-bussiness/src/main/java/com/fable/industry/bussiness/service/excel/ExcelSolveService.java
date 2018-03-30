package com.fable.industry.bussiness.service.excel;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/2/28
 */
public interface ExcelSolveService {

    /**
     * 目录导出
     *
     * @param response
     * @param map
     */
    void catalogExport(HttpServletResponse response, Map<String, Object> map);

    /**
     * 目录导入
     *
     * @param file
     * @return
     */
    Map<String, Object> catalogImport(CommonsMultipartFile file, HttpServletRequest request, String field);

    /**
     * 数据元导出
     *
     * @param response
     * @param map
     */
    void dataElementExport(HttpServletResponse response, Map<String, Object> map);

    /**
     * 数据元导入
     *
     * @param file
     * @param request
     * @return
     */
    Map<String, Object> dataElementImport(CommonsMultipartFile file, HttpServletRequest request);

    /**
     * 服务日志导出
     */
    void serviceLogExport(HttpServletResponse response, Map<String, Object> map);

    /**
     * 资源登记导入
     */

    Map<String, Object> resciRegisterImport(CommonsMultipartFile file, HttpServletRequest request, String catalogueId);

    /**
     * 资源登记导出
     */
    void resciRegisterExport(HttpServletResponse response, HttpServletRequest request, Map<String, Object> map);
}
