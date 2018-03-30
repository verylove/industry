package com.fable.industry.bussiness.service.excel.impl;

import com.alibaba.fastjson.JSON;
import com.fable.industry.api.constant.CommonConstant;
import com.fable.industry.api.export.ExcelLogs;
import com.fable.industry.api.export.ExcelUtil;
import com.fable.industry.api.utils.DateTimeUtil;
import com.fable.industry.bussiness.mapper.dataElement.DataElementMapper;
import com.fable.industry.bussiness.mapper.dictionary.DictionaryItemMapper;
import com.fable.industry.bussiness.mapper.excel.ExcelSolveMapper;
import com.fable.industry.bussiness.mapper.resci.ResciCatalogMapper;
import com.fable.industry.bussiness.mapper.resciPublish.ResciPublishMapper;
import com.fable.industry.bussiness.mapper.resciRegister.ResciRegisterMapper;
import com.fable.industry.bussiness.mapper.systemManage.UserManageMapper;
import com.fable.industry.bussiness.model.bean.*;
import com.fable.industry.bussiness.service.dataElement.DataElementService;
import com.fable.industry.bussiness.service.dictionary.DictionaryItemService;
import com.fable.industry.bussiness.service.excel.ExcelSolveService;
import com.fable.industry.bussiness.service.resciRegister.ResciRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jiangmingjun
 * @create 2018/2/28
 */
@Service
@Transactional
public class ExcelSolveServiceImpl implements ExcelSolveService {

    @Autowired
    private ResciCatalogMapper resciCatalogMapper;

    @Autowired
    private ExcelSolveMapper excelSolveMapper;

    @Autowired
    private DictionaryItemMapper dictionaryItemMapper;

    @Autowired
    private DataElementMapper dataElementMapper;

    @Autowired
    private ResciRegisterService resciRegisterService;

    @Autowired
    private ResciRegisterMapper resciRegisterMapper;

    @Autowired
    private DataElementService dataElementService;

    @Autowired
    private DictionaryItemService dictionaryItemService;

    @Autowired
    private ResciPublishMapper resciPublishMapper;

    @Autowired
    private UserManageMapper userManageMapper;

    private Map<String, String> titleMap = new LinkedHashMap<>(20);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public void catalogExport(HttpServletResponse response, Map<String, Object> map) {
        titleMap.clear();
        if (null != map.get("catalogName")) {
            try {
                map.put("catalogName", URLDecoder.decode(map.get("catalogName").toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Collection<Object> dataSet = new ArrayList<>();
        String fileName = "";
        if (CommonConstant.DATA_EXPORT.equals(map.get("type"))) {
            String titlesCN = "目录名称，目录审核单位，目录审核员，联系电话";
            String titlesEN = "catalogName,comName,userName,telephone";
            swithcTitleMap(titlesCN, titlesEN);
            dataSet = excelSolveMapper.getCatalogData(map);
            fileName = "目录数据导出" + sdf.format(System.currentTimeMillis());
        } else if (CommonConstant.MODULE_UPLOAD.equals(map.get("type"))) {
            titleMap.put("catalogName", "目录名称");
            fileName = "目录模板" + sdf.format(System.currentTimeMillis());
        }


        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xls");
            OutputStream out = response.getOutputStream();
            ExcelUtil.exportExcel(titleMap, dataSet, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> catalogImport(CommonsMultipartFile file, HttpServletRequest request, String field) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelLogs logs = new ExcelLogs();
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<>(10);
        SysUserBean userBean = (SysUserBean) session.getAttribute("userInfo");
        Collection<Map> importExcel = ExcelUtil.importExcel(1,Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs, 0);
        if (null != importExcel && !importExcel.isEmpty()) {
            for (Map m : importExcel) {
                ResciCatalogBean resciCatalogBean = new ResciCatalogBean();
                resciCatalogBean.setUserId(userBean.getUserId());
                if (null == m.get("catalogName")) {
                    map.put("code", 0);
                    map.put("message", "模板错误，导入失败");
                    return map;
                }
                if (50 < m.get("catalogName").toString().length()) {
                    map.put("code", 0);
                    map.put("message", "导入失败，目录名称字数不得超过50个");
                    return map;
                }
                resciCatalogBean.setCatalogName(m.get("catalogName").toString());
                resciCatalogBean.setTelephone(userBean.getTelePhone());
                resciCatalogBean.setComId(userBean.getComId());
                resciCatalogBean.setFieldId(Integer.valueOf(field));
                resciCatalogMapper.insertCatalog(resciCatalogBean);
            }
            map.put("code", 1);
            map.put("message", "导入成功，共导入 " + importExcel.size() + " 条数据！");
        } else {
            map.put("code", 0);
            map.put("message", "导入失败，导入数据为空");
        }
        return map;
    }

    @Override
    public void dataElementExport(HttpServletResponse response, Map<String, Object> map) {
        if (null != map.get("dataName")) {
            try {
                map.put("dataName", URLDecoder.decode(map.get("dataName").toString(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        titleMap.clear();
        Collection<Object> dataSet = new ArrayList<>();
        String fileName = "";
        if (CommonConstant.DATA_EXPORT.equals(map.get("type"))) {
            String titlesCN = "数据元名称，数据元英文名称，数据元缩写名，数据元定义，数据元类型，数据元分类，字典属性，最大出现次数" +
                    "，约束条件，提交人，提交单位，版本号，版本日期，备注";
            String titlesEN = "dataName,dataEname,dataAname,dataDefinition,dataType,itemName,dictionarName,frequencyMax" +
                    ",constraint,userName,comName,versionName,versionTime,note";
            swithcTitleMap(titlesCN, titlesEN);
            dataSet = excelSolveMapper.getDataElementData(map);
            fileName = "数据元数据导出" + sdf.format(System.currentTimeMillis());
        } else if (CommonConstant.MODULE_UPLOAD.equals(map.get("type"))) {
            String titlesCN = "数据元名称，数据元英文名称，数据元缩写名，数据元定义" +
                    "，备注";
            String titlesEN = "dataName,dataEname,dataAname,dataDefinition" +
                    ",note";
            fileName = "数据元模板" + sdf.format(System.currentTimeMillis());
            swithcTitleMap(titlesCN, titlesEN);
        }
        try {
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xls");
            OutputStream out = response.getOutputStream();
            ExcelUtil.exportExcel(titleMap, dataSet, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> dataElementImport(CommonsMultipartFile file, HttpServletRequest request) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelLogs logs = new ExcelLogs();
        HttpSession session = request.getSession();
        SysUserBean userBean = (SysUserBean) session.getAttribute("userInfo");
        Map<String, Object> map = new HashMap<>(10);
        Collection<Map> importExcel = ExcelUtil.importExcel(1,Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs, 0);
        if (null != importExcel && !importExcel.isEmpty()) {
            for (Map m : importExcel) {
                DataElementBean bean = new DataElementBean();
                if (null == m.get("dataName")) {
                    map.put("code", 0);
                    map.put("message", "导入失败，数据元名称不得为空");
                    return map;
                }
                if (null == m.get("dataEname")) {
                    map.put("code", 0);
                    map.put("message", "导入失败，数据元英文名称不得为空");
                    return map;
                }
                if (null == m.get("dataAname")) {
                    map.put("code", 0);
                    map.put("message", "导入失败，数据元缩写名不得为空");
                    return map;
                }
                if (null == m.get("dataDefinition")) {
                    map.put("code", 0);
                    map.put("message", "导入失败，数据元定义不得为空");
                    return map;
                }
                bean.setDataName(m.get("dataName").toString());
                bean.setDataEname(m.get("dataEname").toString());
                bean.setDataAname(m.get("dataAname").toString());
                bean.setDataDefinition(m.get("dataDefinition").toString());
                bean.setEstablishType("0");
                bean.setDataTypeId(1);
                bean.setItemId(20);
                bean.setFrequencyMax("N");
                bean.setConstraint("O");
                bean.setUserId(userBean.getUserId());
                bean.setComId(userBean.getComId());
                bean.setNote(m.get("note").toString());
                bean.setVersionId(6);
                bean.setVersionTime(DateTimeUtil.getNow());
                bean.setDataLength("255");
                bean.setEnabledState("0");
                bean.setEstablishType("0");
                int count = dataElementMapper.getDataByNameAndVersion(bean);
                if (count > 0) {
                    map.put("code", 0);
                    map.put("message", "导入失败，" + bean.getDataName() + " 数据元已存在");
                    return map;
                }
                dataElementMapper.insertData(bean);
            }
            map.put("code", 1);
            map.put("message", "导入成功，共导入 " + importExcel.size() + " 条数据");
        } else {
            map.put("code", 0);
            map.put("message","请勿导入空模板");
        }
        return map;
    }

    @Override
    public void serviceLogExport(HttpServletResponse response, Map<String, Object> map) {
        titleMap.clear();
        Collection<Map<String, String>> dataSet = new ArrayList<>();
        String fileName = "";
        if (CommonConstant.DATA_EXPORT.equals(map.get("type"))) {
            String titlesCN = "应用名称，访问IP，系统维护人员，请求时间，响应时间，操作结果";
            String titlesEN = "serviceName,ip,maintenance,requestTime,responseTime,result";
            swithcTitleMap(titlesCN, titlesEN);
            dataSet = excelSolveMapper.getServiceLogData(map);
            if (null != dataSet) {
                for (Map<String, String> m : dataSet) {
                    if ("1".equals(m.get("result"))) {
                        m.put("result", "成功");
                    } else if ("2".equals(m.get("result"))) {
                        m.put("result", "失败");
                    }
                }
            }
            fileName = "服务日志导出" + sdf.format(System.currentTimeMillis());
        }
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xls");
            OutputStream out = response.getOutputStream();
            ExcelUtil.exportExcel(titleMap, dataSet, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> resciRegisterImport(CommonsMultipartFile file, HttpServletRequest request, String catalogueId) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelLogs logs = new ExcelLogs();
        Map<String, Object> map = new HashMap<>(10);
        List<ResciWidgetBean> resciWidgetBeanList = resciRegisterService.getResciWidgetById(Integer.valueOf(catalogueId));
        Collection<Map> importExcel = ExcelUtil.importExcel(0,Map.class, inputStream, "yyyy-MM-dd", logs, 0);
        if (null != importExcel && !importExcel.isEmpty()) {
            Map<String, String> dataMap = new HashMap<>();
            for (Map m : importExcel) {
                String key = "";
                String value = "";
                Iterator it = m.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    key = (String) entry.getKey();
                    value = (String) entry.getValue();
                    if (value.length() > 50) {
                        map.put("code", 0);
                        map.put("message", "导入失败，模板数据超出长度限制！");
                        return map;
                    }
                    for (int i = 0; i < resciWidgetBeanList.size(); i++) {
                        String attributeName = resciWidgetBeanList.get(i).getAttributeName();
                        String colName = resciWidgetBeanList.get(i).getColumnName();
                        Integer elementId = resciWidgetBeanList.get(i).getElementId();
                        if (attributeName.equals(key)) {
                            Map<String, String> elementMap = dataElementService.queryDataById(elementId);
                            if (elementMap.get("dictionaryId") != null) {
                                Integer dictionaryId = Integer.valueOf(String.valueOf(elementMap.get("dictionaryId")));
                                DictionaryItemBean dictionaryItemBean = dictionaryItemService.queryIdByName(dictionaryId, value);
                                if (dictionaryItemBean != null) {
                                    value = String.valueOf(dictionaryItemBean.getId());
                                    //如果和字典中的数据匹配不上这说明填写的数据不对,默认第一个字典项
                                } else {
                                    value = String.valueOf(dictionaryItemMapper.dict(dictionaryId).get(0).getId());
                                }
                            }
                            dataMap.put(colName, value);
                        }
                    }
                }
                dataMap.put("catalogueId", catalogueId);
                resciRegisterService.addResciRegister(dataMap, request);
            }
            map.put("code", 1);
            map.put("message", "导入成功，共导入 " + importExcel.size() + " 条数据！");
        } else {
            map.put("code", 0);
            map.put("message", "导入失败，未选择模板或数据不对！");
        }
        return map;
    }

    @Override
    public void resciRegisterExport(HttpServletResponse response, HttpServletRequest request, Map<String, Object> map) {
        SysUserBean userInfo = (SysUserBean) request.getSession().getAttribute("userInfo");
        titleMap.clear();
        Collection<Map<String, String>> dataSet = new ArrayList<>();
        String fileName = "";
        String titlesCN = "";
        String titlesEN = "";
        String ids = "";
        if (null != map.get("ids")) {
            ids = map.get("ids").toString();
        }
        Integer catalogueId = Integer.parseInt(map.get("catalogueId").toString());
        List<ResciWidgetBean> resciWidgetBeanList = resciRegisterService.getResciWidgetById(catalogueId);
        //导出时导出所有列头
        for (int i = 0; i < resciWidgetBeanList.size(); i++) {
            titlesCN = titlesCN + "，" + resciWidgetBeanList.get(i).getAttributeName();
            titlesEN = titlesEN + "," + resciWidgetBeanList.get(i).getColumnName();
        }
        titlesCN = titlesCN.substring(1, titlesCN.length());
        titlesEN = titlesEN.substring(1, titlesEN.length());
        if (CommonConstant.DATA_EXPORT.equals(map.get("type"))) {
            //将查询条件封装成需要的格式
            List<String> list = new ArrayList<>();
            if (map.get("param") != null) {
                Map<String, String> maps = (Map) JSON.parse(map.get("param").toString());
                for (Map.Entry<String, String> entry : maps.entrySet()) {
                    String widgetValue = resciRegisterMapper.queryWidgetValueByColName(entry.getKey()).get("widgetValue").toString();
                    if ("Input".equals(widgetValue)) {
                        list.add(entry.getKey() + " LIKE " + "\'%" + entry.getValue() + "%\'");
                    }
                    if ("DataBox".equals(widgetValue)) {
                        String[] date = entry.getValue().split("-");
                        list.add("date_format(`" + entry.getKey() + "`, '%Y-%m-%d') >= date_format('" + date[0] + "', '%Y-%m-%d') " +
                                "AND date_format(`" + entry.getKey() + "`, '%Y-%m-%d') <= date_format('" + date[1] + "', '%Y-%m-%d') ");
                    }
                    if ("Select".equals(widgetValue) && entry.getValue() != null && !"".equals(entry.getValue())) {
                        list.add(entry.getKey() + " = " + "\'" + entry.getValue() + "\'");
                    }
                }
            }
            swithcTitleMap(titlesCN, titlesEN);
            String tableName = resciRegisterMapper.getTableName(catalogueId);
            //查找编目联系人及其公司Id
            Integer userId = resciRegisterMapper.queryByCatalogueID(catalogueId);
            SysUserBean sysUserBean = userManageMapper.queryUserById(userId);
            ResciCatalogUEBean resciCatalogUEBean = resciPublishMapper.queryResciCatalogueByCatalogueId(catalogueId);
            if ("0".equals(resciCatalogUEBean.getResciLevel()) || userInfo.getRoleId() == 1
                    || userId == userInfo.getUserId() || sysUserBean.getComId().equals(userInfo.getComId())) {
                dataSet = resciRegisterMapper.findResciRegisterAll(ids,tableName, list);
            } else {
                dataSet = resciRegisterMapper.findResciRegister(ids,tableName, list, userInfo.getComId());
            }
            fileName = "资源登记导出" + sdf.format(System.currentTimeMillis());
        } else if (CommonConstant.MODULE_UPLOAD.equals(map.get("type"))) {
            fileName = "资源登记模板" + sdf.format(System.currentTimeMillis());
            swithcTitleMap(titlesCN, titlesEN);
        }
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xls");
            OutputStream out = response.getOutputStream();
            ExcelUtil.exportExcel(titleMap, dataSet, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void swithcTitleMap(String titlesCN, String titlesEN) {
        String[] titlesCNArr = titlesCN.split("，");
        String[] titlesENArr = titlesEN.split(",");
        for (int i = 0; i < titlesCNArr.length; i++) {
            titleMap.put(titlesENArr[i], titlesCNArr[i]);
        }
    }
}
