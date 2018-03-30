package com.fable.industry.bussiness.service.resciRegister.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.mapper.dataElement.DataElementMapper;
import com.fable.industry.bussiness.mapper.dictionary.DictionaryItemMapper;
import com.fable.industry.bussiness.mapper.resciPublish.ResciPublishMapper;
import com.fable.industry.bussiness.mapper.resciRegister.ResciRegisterMapper;
import com.fable.industry.bussiness.mapper.systemManage.UserManageMapper;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import com.fable.industry.bussiness.model.bean.ResciWidgetBean;
import com.fable.industry.bussiness.model.bean.SysResciBean;
import com.fable.industry.bussiness.model.bean.SysUserBean;
import com.fable.industry.bussiness.service.resciRegister.ResciRegisterService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-01 14:08
 * @description 资源登记
 */
@Service
@Transactional
public class ResciRegisterServiceImpl implements ResciRegisterService {

    @Autowired
    private ResciRegisterMapper resciRegisterMapper;

    @Autowired
    private DataElementMapper dataElementMapper;

    @Autowired
    private DictionaryItemMapper dictionaryItemMapper;

    @Autowired
    private ResciPublishMapper resciPublishMapper;

    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public List<ResciWidgetBean> getResciWidgetById(Integer catalogueId) {
        return resciRegisterMapper.getResciWidgetById(catalogueId);
    }

    @Override
    public PageResponse<Map<String, String>> findResciRegister(PageRequest<Map<String, String>> pageRequest, HttpServletRequest request) {
        Map<String, String> map = pageRequest.getParam();
        SysUserBean userInfo = (SysUserBean) request.getSession().getAttribute("userInfo");
        Integer catalogueId = Integer.parseInt(map.get("catalogueId"));
        String tableName = resciRegisterMapper.getTableName(catalogueId);
        ResciCatalogUEBean resciCatalogUEBean = resciPublishMapper.queryResciCatalogueByCatalogueId(catalogueId);
        //查询哪些列是字典项
        List<ResciWidgetBean> resciWidgetBeanList = resciRegisterMapper.getResciWidgetById(catalogueId);
        List<String> colList = new ArrayList<>();
        for (int i = 0; i < resciWidgetBeanList.size(); i++) {
            String colName = resciWidgetBeanList.get(i).getColumnName();
            Integer elementId = resciWidgetBeanList.get(i).getElementId();
            Map<String, String> dataMap = dataElementMapper.queryDataById(elementId);
            if (dataMap.get("dictionaryId") != null) {
                colList.add(colName);
            }
        }
        //将查询条件封装成需要的格式
        map.remove("catalogueId");
        List<String> list = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String widgetValue = resciRegisterMapper.queryWidgetValueByColName(entry.getKey()).get("widgetValue").toString();
                if ("Input".equals(widgetValue)) {
                    list.add(entry.getKey() + " LIKE " + "\'%" + entry.getValue() + "%\'");
                }
                if ("DataBox".equals(widgetValue)) {
                    String[] date = entry.getValue().split(" - ");
                    list.add("date_format(`" + entry.getKey() + "`, '%Y-%m-%d') >= date_format('" + date[0] + "', '%Y-%m-%d') " +
                            "AND date_format(`" + entry.getKey() + "`, '%Y-%m-%d') <= date_format('" + date[1] + "', '%Y-%m-%d') ");
                }
                if ("Select".equals(widgetValue) && entry.getValue() != null && !"".equals(entry.getValue())) {
                    list.add(entry.getKey() + " = " + "\'" + entry.getValue() + "\'");
                }
            }
        }
        List<Map<String, String>> dataList = new ArrayList<>();
        //查找编目联系人及其公司Id
        Integer userId = resciRegisterMapper.queryByCatalogueID(catalogueId);
        SysUserBean sysUserBean = userManageMapper.queryUserById(userId);
        Page<Map<String, String>> data = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        if ("0".equals(resciCatalogUEBean.getResciLevel()) || userInfo.getRoleId() == 1
                || userId == userInfo.getUserId() || sysUserBean.getComId().equals(userInfo.getComId())) {
            dataList = resciRegisterMapper.findResciRegisterAll("", tableName, list);
        } else {
            dataList = resciRegisterMapper.findResciRegister("", tableName, list, userInfo.getComId());
        }
        //将含有字典项的列转化为具体的名称
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < colList.size(); j++) {
                String colName = colList.get(j);
                String value = dataList.get(i).get(colName);
                if (value != null && value != "") {
                    Map<String, Object> item = dictionaryItemMapper.queryItemById(Integer.valueOf(value));
                    dataList.get(i).put(colName, item.get("itemName").toString());
                }
            }
        }
        return PageResponse.wrap(data, true, "0", "success");
    }

    @Override
    public Map<String, Object> addResciRegister(Map<String, String> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUserBean sysUserBean = (SysUserBean) session.getAttribute("userInfo");
        String names = "";
        String values = "";
        String catalogueId = map.get("catalogueId");
        map.remove("catalogueId");
        SysResciBean sysResciBean = new SysResciBean();
        sysResciBean.setCatalogueId(Integer.parseInt(catalogueId));
        sysResciBean.setUserId(sysUserBean.getUserId());
        sysResciBean.setComId(sysUserBean.getComId());
        int sysResci = resciRegisterMapper.addSysResci(sysResciBean);
        String tableName = resciRegisterMapper.getTableName(sysResciBean.getCatalogueId());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue() != "") {
                names = names + "," + entry.getKey();
                values = values + "," + "\'" + entry.getValue() + "\'";
            }
        }
        names = "CiId" + names;
        values = sysResciBean.getID() + values;
        int count = resciRegisterMapper.addResciRegister(tableName, names, values);
        if (count > 0 && sysResci > 0) {
            return Result.success("新增资源成功！");
        }
        return Result.fail("新增资源失败！");
    }

    @Override
    public Map<String, String> queryResciRegisterById(Integer id, Integer catalogueId) {
        String tableName = resciRegisterMapper.getTableName(catalogueId);
        return resciRegisterMapper.queryResciRegisterById(id, tableName);
    }

    @Override
    public Map<String, Object> updateResciRegister(Map<String, String> map) {
        String sets = "";
        String id = map.get("ID");
        String catalogueId = map.get("catalogueId");
        map.remove("ID");
        map.remove("catalogueId");
        SysResciBean sysResciBean = new SysResciBean();
        sysResciBean.setID(Integer.parseInt(id));
        sysResciBean.setCatalogueId(Integer.parseInt(catalogueId));
        int sysResci = resciRegisterMapper.updateSysResci(sysResciBean);
        String tableName = resciRegisterMapper.getTableName(sysResciBean.getCatalogueId());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue() != "") {
                sets = sets + "," + entry.getKey() + "=" + "\'" + entry.getValue() + "\'";
            }
        }
        sets = sets.substring(1, sets.length());
        int count = resciRegisterMapper.updateResciRegister(tableName, sets, Integer.parseInt(id));
        if (count > 0 && sysResci > 0) {
            return Result.success("修改资源成功！");
        }
        return Result.fail("修改资源失败！");
    }

    @Override
    public Map<String, Object> checkUpdateResciRegister(SysUserBean userInfo, Map<String, Object> map) {
        //登录用户所属单位
        Integer user_comId = userInfo.getComId();
        //资源所属单位
        String id = map.get("CiId").toString();
        Integer catalogueId = Integer.valueOf(map.get("catalogueId").toString());
        String[] ids = id.split(",");
        List<String> list = Arrays.asList(ids);
        List<Integer> comId = resciRegisterMapper.queryComId(list);
        Integer userId = resciRegisterMapper.queryByCatalogueID(catalogueId);
        SysUserBean sysUserBean = userManageMapper.queryUserById(userId);
        if (comId.get(0).equals(user_comId) || userInfo.getUserId() == 1
                || userId == userInfo.getUserId() || sysUserBean.getComId().equals(userInfo.getComId())) {
            return Result.success("该资源可修改！");
        }
        return Result.fail("没有权限修改该资源！");
    }

    @Override
    public Map<String, Object> deleteResciRegister(String id, Integer catalogueId) {
        String[] ids = id.split(",");
        List<String> list = Arrays.asList(ids);
        String tableName = resciRegisterMapper.getTableName(catalogueId);
        int sysResci = resciRegisterMapper.deleteSysResci(list);
        int count = resciRegisterMapper.deleteResciRegister(tableName, list);
        if (count > 0 && sysResci > 0) {
            return Result.success("删除资源成功！");
        }
        return Result.fail("删除资源失败！");
    }

    @Override
    public Map<String, Object> checkDeleteResciRegister(SysUserBean userInfo, Map<String, Object> map) {
        //登录用户所属单位
        Integer user_comId = userInfo.getComId();
        //资源所属单位
        String id = map.get("CiId").toString();
        Integer catalogueId = Integer.valueOf(map.get("catalogueId").toString());
        String[] ids = id.split(",");
        List<String> list = Arrays.asList(ids);
        List<Integer> comId = resciRegisterMapper.queryComId(list);
        Integer userId = resciRegisterMapper.queryByCatalogueID(catalogueId);
        SysUserBean sysUserBean = userManageMapper.queryUserById(userId);
        if (userInfo.getUserId() == 1 || userId == userInfo.getUserId()
                || sysUserBean.getComId().equals(userInfo.getComId())) {
            return Result.success("该资源可删除！");
        }
        if (comId.size() == 1 && comId.get(0).equals(user_comId)) {
            return Result.success("该资源可删除！");
        }
        return Result.fail("无法删除，选择的资源中包含没有权限的！");
    }

    @Override
    public List<TreeRsult> queryResciRegisteTree() {
        return resciRegisterMapper.queryResciRegisteTree();
    }
}
