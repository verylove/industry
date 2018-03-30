package com.fable.industry.bussiness.mapper.resci;

import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.DataExamineBean;
import com.fable.industry.bussiness.model.bean.ResciCatalogUEBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResciCataUEMapper {

    List<TreeRsult> catalogingTree();

    List<Map<String,Object>> catalogingList(ResciCatalogUEBean bean);

    List<Map<String,Object>> examineCatalogList(ResciCatalogUEBean bean);

    int insertResciCataUE(ResciCatalogUEBean bean);

    int updateResciCataUE(ResciCatalogUEBean bean);

    ResciCatalogUEBean queryResciCataUE(ResciCatalogUEBean bean);

    Map<String,Object> queryResciMap(ResciCatalogUEBean bean);

    List<Map<String,Object>> queryDataByCUE(Integer id);

    List<String> queryCataUeState(@Param("ids") String ids);

    int deleteCatalogUE(@Param("ids") String ids);

    int queryDataExamine(DataExamineBean bean);


    int insertDataExamine(DataExamineBean bean);

    int updateDataExamine(DataExamineBean bean);

    Map<String,Object> getDataExamine(DataExamineBean bean);

    int checkOID(ResciCatalogUEBean bean);

    Map<String, Object> getoid(ResciCatalogUEBean bean);

    int getResciCode(String code);

}
