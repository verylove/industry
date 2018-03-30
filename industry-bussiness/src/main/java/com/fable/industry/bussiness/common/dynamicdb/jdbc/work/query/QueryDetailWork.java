package com.fable.industry.bussiness.common.dynamicdb.jdbc.work.query;


import com.fable.industry.bussiness.common.dynamicdb.Column;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.sql.SqlMapperUtil;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.work.BaseWork;

import java.util.List;

/**
 * 单行查询工作
 * 主要用于配置项详情展示
 * @author 郑自辉
 *
 */
public class QueryDetailWork extends BaseQueryWork{
	
	/**
	 * 配置项ID
	 */
	private String id;
	
	public QueryDetailWork(BaseWork.WorkType workType, String id, String[] tableName, List<Column> columns)
	{
		super(workType,tableName,columns);
		this.id = id;
	}

	@Override
	protected String selectSql() {
		return SqlMapperUtil.getSqlMapper().getSelect();
	}

	@Override
	protected String genWhere(String tableName) {
		return tableName + ".CiId=" + id;
	}
}
