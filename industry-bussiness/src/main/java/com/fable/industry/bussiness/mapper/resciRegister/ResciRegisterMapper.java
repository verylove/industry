package com.fable.industry.bussiness.mapper.resciRegister;

import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.ResciWidgetBean;
import com.fable.industry.bussiness.model.bean.SysResciBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbaoan
 * @date 2018-02-01 14:09
 * @description 资源登记
 */
public interface ResciRegisterMapper {
    /**
     * 根据资源编目Id查出对应的资源控件
     */
    List<ResciWidgetBean> getResciWidgetById(Integer catalogueId);

    /**
     * 查询资源登记列表完全共享
     */
    List<Map<String, String>> findResciRegisterAll(@Param("ids") String ids,@Param("tableName") String tableName, @Param("list") List<String> list);

    /**
     * 查询资源登记列表部分共享
     */
    List<Map<String, String>> findResciRegister(@Param("ids") String ids,@Param("tableName") String tableName, @Param("list") List<String> list, @Param("comId") Integer comId);

    /**
     * 新增资源登记表数据
     */
    int addSysResci(SysResciBean sysResciBean);

    /**
     * 查询动态表名
     */
    String getTableName(Integer catalogueId);

    /**
     * 新增资源登记到动态表
     */
    int addResciRegister(@Param("tableName") String tableName, @Param("names") String names, @Param("values") String values);

    /**
     * 根据id查看资源登记
     */
    Map<String, String> queryResciRegisterById(@Param("id") Integer id, @Param("tableName") String tableName);

    /**
     * 修改资源登记表数据
     */
    int updateSysResci(SysResciBean sysResciBean);

    /**
     * 修改动态表
     */
    int updateResciRegister(@Param("tableName") String tableName, @Param("sets") String sets, @Param("id") Integer id);

    /**
     * 删除资源登记表数据
     */
    int deleteSysResci(@Param("list") List<String> list);

    /**
     * 删除动态表
     */
    int deleteResciRegister(@Param("tableName") String tableName, @Param("list") List<String> list);

    /**
     * 当前选择的资源的单位Id
     */
    List<Integer> queryComId(@Param("list") List<String> list);

    /**
     * 资源登记树
     */
    List<TreeRsult> queryResciRegisteTree();

    /**
     * 根据编目ID删除资源
     */
    int deleteByCataLogUEId(int catoLogUEId);

    /**
     * 根据ColName查询该列是什么控件
     */
    Map<String, Object> queryWidgetValueByColName(@Param("colName") String ColName);

    /**
     * 根据编目ID查找编目联系人
     */
    Integer queryByCatalogueID(@Param("catalogueId") Integer catalogueId);
}
