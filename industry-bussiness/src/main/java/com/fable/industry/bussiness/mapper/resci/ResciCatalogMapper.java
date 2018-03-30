package com.fable.industry.bussiness.mapper.resci;

import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.model.bean.ResciCatalogBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @auther jiangmingjun
 * @create 2018/2/2
 */
public interface ResciCatalogMapper {

    List<ResciCatalogBean> queryListCatalog(ResciCatalogBean bean);

    int insertCatalog(ResciCatalogBean bean);

    int updateCatalog(ResciCatalogBean bean) ;

    List<String> getCataLogName(@Param("ids") String ids);

    int deleteCatalog(@Param("ids") String ids);

    Map<String,Object> queryCatalogById(Integer id);

    List<TreeRsult> catalogTree();

}
