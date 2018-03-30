package com.fable.industry.bussiness.service.resci.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.api.utils.DateTimeUtil;
import com.fable.industry.bussiness.common.dynamicdb.api.IDynamicDB;
import com.fable.industry.bussiness.mapper.dataElement.DataElementMapper;
import com.fable.industry.bussiness.mapper.dynamicform.resattributes.ResAttributesMapper;
import com.fable.industry.bussiness.mapper.dynamicform.restypeattr.ResTypeAttrMapper;
import com.fable.industry.bussiness.mapper.resci.ResciCataUEMapper;
import com.fable.industry.bussiness.mapper.resciRegister.ResciRegisterMapper;
import com.fable.industry.bussiness.model.bean.DataElementBean;
import com.fable.industry.bussiness.model.bean.DataExamineBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.model.bean.SysUserBean;
import com.fable.industry.bussiness.model.dynamicform.ResAttributesBean;
import com.fable.industry.bussiness.service.dynamicform.resattributes.IResAttributesService;
import com.fable.industry.bussiness.service.resci.ResciCataUEService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangmingjun
 * @create 2018/2/3
 */
@Service
@Transactional
public class ResciCataUEServiceImpl implements ResciCataUEService {

    @Autowired
    private ResciCataUEMapper resciCataUEMapper;

    @Autowired
    private DataElementMapper dataElementMapper;

    @Autowired
    private IResAttributesService resAttributesService;

    @Autowired
    public ResAttributesMapper resAttributesMapper;

    @Autowired
    private ResTypeAttrMapper resTypeAttrMapper;

    @Autowired
    private IDynamicDB iDynamicDB;

    @Autowired
    private ResciRegisterMapper resciRegisterMapper;

    @Override
    public List<TreeRsult> catalogingTree() {
        return resciCataUEMapper.catalogingTree();
    }

    @Override
    public PageResponse catalogingList(PageRequest<ResciCatalogUEBean> pageRequest, HttpServletRequest request) {
        ResciCatalogUEBean bean = pageRequest.getParam();
        Page<ResciCatalogUEBean> data = PageHelper.startPage(pageRequest.getPageNo(),pageRequest.getPageSize());
        resciCataUEMapper.catalogingList(bean);
        return PageResponse.wrap(data,true,"0","success");
    }

    @Override
    public PageResponse examineCatalogList(PageRequest<ResciCatalogUEBean> pageRequest, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUserBean userBean = (SysUserBean) session.getAttribute("userInfo");
        ResciCatalogUEBean bean = pageRequest.getParam();
        bean.setUserId(userBean.getUserId());
        bean.setRoleId(userBean.getRoleId());
        Page<ResciCatalogUEBean> data = PageHelper.startPage(pageRequest.getPageNo(),pageRequest.getPageSize());
        resciCataUEMapper.examineCatalogList(bean);
        return PageResponse.wrap(data,true,"0","success");
    }

    /**
     * 资源编目第一步（新增）和第二步（更新）
     * @param bean
     * @return
     */
    @Override
    public ResciCatalogUEBean cataloging(ResciCatalogUEBean bean) {
        int state = bean.getCatalogUEState();
        if (state == 0) {
            bean.setResciState(-1);
            resciCataUEMapper.insertResciCataUE(bean);
        }else if (state == 1) {
            resciCataUEMapper.updateResciCataUE(bean);
        }
        return resciCataUEMapper.queryResciCataUE(bean);
    }

    @Override
    public List<Map<String, Object>> catalogingStep3(ResciCatalogUEBean bean) {
        resciCataUEMapper.updateResciCataUE(bean);
        return resciCataUEMapper.queryDataByCUE(bean.getId());
    }

    /**
     * 1.资源编目第四步点下一步时，配置项类型_属性关联表里是否有此编目ID，如果有则清空配置项表里该编目相关信息
     * 2.新增新的数据元信息到配置项表里
     * 3.返回第五步的页面数据
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> catalogingStep4(Map<String,Object> map) {
        int catalogueId = Integer.valueOf(map.get("catalogueId").toString());
        List<Map<String,String>> list = (List<Map<String,String>>) map.get("DataElementList");
        int count = resTypeAttrMapper.queryByCatalogueId(catalogueId);
        String tableName = System.currentTimeMillis()+"";
        if (count > 0) {
            deleteUEAbout(catalogueId);
        }
          for (Map<String,String> dataMap : list
             ) {

            DataElementBean bean = new DataElementBean();
            bean.setId(Integer.valueOf(dataMap.get("id")));
            bean.setDataName(dataMap.get("dataName"));
            bean.setDataEname(dataMap.get("dataEname"));
            bean.setDataLength(dataMap.get("dataLength"));
            if (null != dataMap.get("dictionaryId") &&
                    !"null".equals(dataMap.get("dictionaryId")) && !"undefined".equals(dataMap.get("dictionaryId"))) {
                bean.setDictionaryId(Integer.valueOf(dataMap.get("dictionaryId")));
            }
            bean.setDataCode(dataMap.get("dataCode"));
            bean.setIsTbaleDisplay(Integer.valueOf(dataMap.get("isTbaleDisplay")));
            bean.setIsQuery(Integer.valueOf(dataMap.get("isQuery")));
            bean.setDataTypeId(Integer.valueOf(dataMap.get("dataTypeId")));
            bean.setEstablishType(dataMap.get("establishType"));
            if ("1".equals(bean.getEstablishType())) {
                dataElementMapper.insertData(bean);
            }
            ResAttributesBean resAttributesBean = new ResAttributesBean();
            resAttributesBean.setTblName(tableName);
            resAttributesBean.setAttributeName(bean.getDataName());
            resAttributesBean.setColumnName(bean.getDataCode());
            resAttributesBean.setIsTableDisplay(bean.getIsTbaleDisplay());
            resAttributesBean.setIsQuery(bean.getIsQuery());
            resAttributesBean.setElementId(bean.getId());
            resAttributesBean.setWidgetId(bean.getDataTypeId());
            resAttributesBean.setCatalogueId(catalogueId);
            resAttributesBean.setWidgetId(bean.getDataTypeId());
            resAttributesBean.setIndex(Integer.valueOf(String.valueOf(dataMap.get("index"))));
            if (null == dataMap.get("constraint") || dataMap.get("constraint").equals("O")) {
                // 非必填
                resAttributesBean.setRequired((byte) 0);
            }else if (dataMap.get("constraint").equals("M")){
                //必填
                resAttributesBean.setRequired((byte) 1);
            }
            resAttributesService.addResAttributes(resAttributesBean);
        }
        map.clear();
        ResciCatalogUEBean bean = new ResciCatalogUEBean();
        bean.setId(catalogueId);
        bean.setCatalogUEState(3);
        resciCataUEMapper.updateResciCataUE(bean);
        List<Map<String,Object>> dataElmentList = resciCataUEMapper.queryDataByCUE(catalogueId);
        ResciCatalogUEBean catalogUEBean = new ResciCatalogUEBean();
        catalogUEBean.setId(catalogueId);
        Map<String,Object> catalogUEMap = resciCataUEMapper.queryResciMap(catalogUEBean);
        map.put("dataElmentList",dataElmentList);
        map.put("catalogUEMap",catalogUEMap);
        return map;
    }

    @Override
    public Map<String, Object> catalogingSave(ResciCatalogUEBean bean) {
        resciCataUEMapper.updateResciCataUE(bean);
        return Result.success("保存成功");
    }

    /**
     * 创建动态表单
     * @param bean
     * @return
     */
    @Override
    public Map<String, Object> createDynamicDB(ResciCatalogUEBean bean) {
        List<Map<String,Object>> dataElmentList = resciCataUEMapper.queryDataByCUE(bean.getId());
        String tableName = dataElmentList.get(0).get("tableName").toString();
        try{
            iDynamicDB.createTable(tableName);
            for (Map<String,Object> map : dataElmentList
                    ) {
                iDynamicDB.addColumn(tableName, map.get("columnName").toString(),"String");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
        return Result.success("创建动态表单成功");
    }

    @Override
    public Map<String, Object> deleteDynamicDB(ResciCatalogUEBean bean) {
        List<Map<String,Object>> dataElmentList = resciCataUEMapper.queryDataByCUE(bean.getId());
        String tableName = dataElmentList.get(0).get("tableName").toString();
        try{
            iDynamicDB.dropTable(tableName);
//            for (Map<String,Object> map : dataElmentList
//                    ) {
//                iDynamicDB.removeColumn(tableName, map.get("columnName").toString(),false);
//            }
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
        return Result.success("删除动态表单成功");
    }

    @Override
    public ResciCatalogUEBean catalogingUpdate(ResciCatalogUEBean bean) {
        if (bean.getCatalogUEState() != 4 && bean.getCatalogUEState() != null) {
            bean = resciCataUEMapper.queryResciCataUE(bean);
        }
        return bean;
    }

    @Override
    public List<Map<String, Object>> catalogingUpdateStep4(ResciCatalogUEBean bean) {
        return resciCataUEMapper.queryDataByCUE(bean.getId());
    }

    @Override
    public ResciCatalogUEBean queryResciCataUE(ResciCatalogUEBean bean) {
        return resciCataUEMapper.queryResciCataUE(bean);
    }

    @Override
    public List<Map<String, Object>> queryDataByCUE(Integer id) {
        return resciCataUEMapper.queryDataByCUE(id);
    }

    /**
     *  查询资源编目所有信息（返回第5步页面数据）
     * @param bean
     * @return
     */
    @Override
    public Map<String, Object> queryCatalogUEAll(ResciCatalogUEBean bean) {
        Map<String,Object> map = new HashMap<>(2);
        List<Map<String,Object>> dataElmentList = resciCataUEMapper.queryDataByCUE(bean.getId());
        Map<String,Object> catalogUEMap = resciCataUEMapper.queryResciMap(bean);
        map.put("dataElmentList",dataElmentList);
        map.put("catalogUEMap",catalogUEMap);
        return map;
    }

    @Override
    public Map<String, Object> checkCatalogUE(String ids) {
        List<String> nameList = resciCataUEMapper.queryCataUeState(ids);
        if (!nameList.isEmpty()) {
            StringBuilder names = new StringBuilder();
            for (String name : nameList
                    ) {
                names.append(name).append(",");
            }
            return Result.fail("资源编目："+names.substring(0,names.length()-1)
                    +" 已发布，不能删除");
        }
        return Result.success("成功");
    }

    /**
     *  资源编目删除
     * @param ids
     * @return
     */
    @Override
    public Map<String, Object> deleteCatalogUE(String ids) {
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            deleteUEAbout(Integer.valueOf(idArr[i]));
        }
        int code = resciCataUEMapper.deleteCatalogUE(ids);
        if (code <= 0) {
            return Result.fail("资源编目删除失败");
        }
        return Result.success("资源编目删除成功");
    }

    @Override
    public Map<String, String> stateChange(ResciCatalogUEBean bean) {
        Map<String,String> map = new HashMap<>(2);
        map.put("message","成功");
        switch (bean.getResciState()) {
            case 1:
                bean.setSubmitTime(DateTimeUtil.getNow());
                resciCataUEMapper.updateResciCataUE(bean);
                break;
            case 4:
                bean.setReleaseTime(DateTimeUtil.getNow());
                // 发布之后创建动态表单
                createDynamicDB(bean);
                resciCataUEMapper.updateResciCataUE(bean);
                break;
            case 5:
                bean.setRevokeTime(DateTimeUtil.getNow());
                //撤销发布之后删除动态表单
                resciCataUEMapper.updateResciCataUE(bean);
                resciRegisterMapper.deleteByCataLogUEId(bean.getId());
                deleteDynamicDB(bean);
                break;
            default:
                break;

        }
        return map;
    }

    @Override
    public Map<String, Object> review(DataExamineBean bean,HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>(2);
        ResciCatalogUEBean uebean = new ResciCatalogUEBean();
        HttpSession session = request.getSession();
        SysUserBean sysUserBean = (SysUserBean) session.getAttribute("userInfo");
        bean.setUserId(sysUserBean.getUserId());
        uebean.setId(bean.getCataUEId());
        int count = resciCataUEMapper.queryDataExamine(bean);
        if (count > 0) {
            resciCataUEMapper.updateDataExamine(bean);
        }
        else {
            resciCataUEMapper.insertDataExamine(bean);
        }
        switch (bean.getExamineStep()) {
            case "0":
            case "1":
                resciCataUEMapper.updateDataExamine(bean);
                bean.setUpdateTime(DateTimeUtil.getNow());
                map = queryCatalogUEAll(uebean);
                break;
            case "3":
                resciCataUEMapper.updateDataExamine(bean);
                if ("0".equals(bean.getExamineState())) {
                    uebean.setResciState(6);
                }else if ("1".equals(bean.getExamineState())) {
                    uebean.setResciState(3);
                }
                bean.setUpdateTime(DateTimeUtil.getNow());
                resciCataUEMapper.updateDataExamine(bean);
                resciCataUEMapper.updateResciCataUE(uebean);
                break;
            default:
                break;
        }
        return map;
    }

    @Override
    public Map<String, Object> getReleaseProcess(ResciCatalogUEBean bean) {
        Map<String,Object> map = new HashMap<>(2);
        Map<String,Object> catalogUEMap = resciCataUEMapper.queryResciMap(bean);
        DataExamineBean examineBean = new DataExamineBean();
        examineBean.setCataUEId(bean.getId());
        Map<String,Object> dataExamineMap = resciCataUEMapper.getDataExamine(examineBean);
        map.put("catalogUEMap",catalogUEMap);
        map.put("dataExamineMap",dataExamineMap);
        return map;
    }

    @Override
    public Map<String, Object> oidCheck(ResciCatalogUEBean bean) {
        int oid = resciCataUEMapper.checkOID(bean);
        if (0 >= oid) {
            return Result.fail("OID错误");
        }
        return Result.success("成功");
    }

    @Override
    public Map<String, Object> getoid(ResciCatalogUEBean bean) {
        Map<String,Object> map = resciCataUEMapper.getoid(bean);
        if (null == map) {
            return new HashMap<>();
        }
        return map;
    }

    @Override
    public Map<String, Object> resciCodeCheck(String code) {
        int count = resciCataUEMapper.getResciCode(code);
        if (count > 0) {
            return Result.fail("资源编码已存在");
        }
        return Result.success("可以插入");
    }

    /**
     *  删除配置项表里数据
     * @param catalogueId
     */
    private void deleteUEAbout(int catalogueId) {
        List<Map<String,Object>> dataElmentList = resciCataUEMapper.queryDataByCUE(catalogueId);
        StringBuilder citypeIds = new StringBuilder("");
        StringBuilder attrIds = new StringBuilder("");
        StringBuilder dataElementIds = new StringBuilder("");
        for (Map<String,Object> map : dataElmentList
                ) {
            citypeIds.append(String.valueOf(map.get("citypeId"))).append(",");
            attrIds.append(String.valueOf(map.get("attrId"))).append(",");
            if ("1".equals(map.get("establishType"))) {
                dataElementIds.append(String.valueOf(map.get("dataElementId"))).append(",");
            }
        }
        resTypeAttrMapper.deleteBatchIds(new StringBuilder(citypeIds.substring(0,citypeIds.length()-1)));
        resAttributesMapper.deleteBatchIds(new StringBuilder(attrIds.substring(0,attrIds.length()-1)));
        if (!"".equals(dataElementIds.toString())) {
            dataElementMapper.deleteData(dataElementIds.substring(0, dataElementIds.length() - 1));

        }
    }
}
