package com.fable.industry.bussiness.common.dynamicdb.jdbc;

import com.fable.industry.api.common.helper.SessionFTemplate;
import com.fable.industry.bussiness.common.dynamicdb.Column;
import com.fable.industry.bussiness.common.dynamicdb.api.AttrInitData;
import com.fable.industry.bussiness.common.dynamicdb.api.IDynamicDBQuery;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.work.BaseWork;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.work.InsertWork;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.work.QueryPrimaryIdWork;
import com.fable.industry.api.common.sysdict.SystemFinalValue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动态表结构数据操作JDBC实现 新增、修改、删除、更新 目前采用Hibernate执行原始SQL的方式
 * 
 * @author 郑自辉
 * 
 */
@Repository("jdbcDBQuery")
public class JdbcDBQueryImpl extends BaseJdbc implements IDynamicDBQuery {

	@Override
	public String save(Map<String, Object> data, String... tableNames) {
		String ciId = String.valueOf(data.get("CiId"));
		// 批量sql
		List<String> batchSqlList = new ArrayList<String>();
		// 字段名称
		List<String> columnNameList = new ArrayList<String>();
		String columnNames = null;
		// 字段值
		List<String> columnValList = new ArrayList<String>();
		String columnVals = null;
		// 根据表名称查询表字段
		List<Column> columns = listColumns(tableNames);
		InsertWork batchtWork = new InsertWork(BaseWork.WorkType.BATCH);
		for (String tableName : tableNames) {
			if (null == data.get("CiId")) {
				// 按照现在的设计，如果新增时CiId为空，则视为新增组件，需要生成其ID
				// mysql生成主键，通过调用函数生成主键
				/*if (CTD.isMySQL()) {
					QueryPrimaryIdWork work = new QueryPrimaryIdWork(
							WorkType.SELECT, SystemFinalValue.CMDB_TABLENAME_PREFIX);
					work.execute(sqlSessionFactory.openSession().getConnection());
					ciId = String.valueOf(work.getId());
				}*/
				QueryPrimaryIdWork work = new QueryPrimaryIdWork(
						BaseWork.WorkType.SELECT, SystemFinalValue.CMDB_TABLENAME_PREFIX);
				work.execute(SessionFTemplate.getSessionConnection());
				ciId = String.valueOf(work.getId());
			}
			columnNameList.clear();
			columnValList.clear();
			// 给主键赋值
			columnNameList.add("CiId");
			columnValList.add(ciId);
			for (Column cl : columns) {
				if (cl.getTableName().equals(tableName)
						&& data.containsKey(cl.getColumnName())) {
					columnNameList.add(cl.getColumnName());
					columnValList.add(String.valueOf(data.get(cl
							.getColumnName())));
				}
			}
			columnNames = this.castList(columnNameList);
			columnVals = this.castListToVal(columnValList);

			if (tableNames.length <= 1) {
				InsertWork insertWork = new InsertWork(BaseWork.WorkType.INSERT,
						tableName, columnNames, columnVals);
				insertWork.execute(SessionFTemplate.getSessionConnection());
			} else {
				batchSqlList.add(batchtWork.batchSql(tableName, columnNames,
						columnVals));
			}
		}
		if (tableNames.length > 1) {
			batchtWork.setBatchSql(batchSqlList);
			batchtWork.execute(SessionFTemplate.getSessionConnection());
		}
		return ciId;
	}

	@Override
	public void delete(String id, String... tableNames) {
		/*Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		// 批量sql
		List<String> batchSqlList = new ArrayList<String>();
		if (tableNames.length <= 1) {
			DeleteWork work = new DeleteWork(WorkType.DELETE, tableNames[0], id);
			work.execute(sqlSessionFactory.openSession().getConnection());
		} else {
			DeleteWork work = new DeleteWork(WorkType.BATCH);
			for (String tableName : tableNames) {
				batchSqlList.add(work.batchSql(tableName, id));
			}
			work.setBatchSql(batchSqlList);
			work.execute(sqlSessionFactory.openSession().getConnection());
		}*/
	}

	@Override
	public void update(Map<String, Object> data, String... tableNames) {
		/*Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		// 批量sql
		List<String> batchSqlList = new ArrayList<String>();
		// 字段名称
		Map<String, Object> map = null;
		String ciId = String.valueOf(data.get("CiId"));
		data.remove("CiId");
		if (tableNames.length <= 1) {
			UpdateWork work = new UpdateWork(WorkType.UPDATE, tableNames[0],
					this.castMapToVal(data), ciId);
			work.execute(sqlSessionFactory.openSession().getConnection());
		}
		if (tableNames.length > 1) {
			UpdateWork workBatch = new UpdateWork(WorkType.BATCH);
			// 根据表名称查询表字段
			List<Column> columns = listColumns(tableNames);
			for (String tableName : tableNames) {
				map = new HashMap<String, Object>();
				for (Column cl : columns) {
					if (cl.getTableName().endsWith(tableName)
							&& data.containsKey(cl.getColumnName())) {
						map.put(cl.getColumnName(),
								data.get(cl.getColumnName()));
					}
				}
				if (map.isEmpty()) {
					continue;
				}
				batchSqlList.add(workBatch.batchSql(tableName,
						this.castMapToVal(map), ciId));
			}
			workBatch.setBatchSql(batchSqlList);
			workBatch.execute(sqlSessionFactory.openSession().getConnection());
		}*/
	}

	@Override
	public Integer queryPrimaryIdWork(String tableName) {
		/*Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		QueryPrimaryIdWork queryPrimaryIdWork = new QueryPrimaryIdWork(
				WorkType.SELECT, tableName);
		queryPrimaryIdWork.execute(sqlSessionFactory.openSession().getConnection());
		return queryPrimaryIdWork.getId();*/
		return null;
	}

	@Override
	public Map<String, String> query(String id, String... tableName) {
		/*List<Column> columns = listColumns(tableName);
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		QueryDetailWork work = new QueryDetailWork(WorkType.SELECT, id,
				tableName, columns);
		work.execute(sqlSessionFactory.openSession().getConnection());
		return work.getData();*/
		return null;
	}

	@Override
	public Map<String, Object> list(String typeId, int start, int pageSize,
			String... tableName) {
		return list(typeId, null, start, pageSize, tableName);
	}

	@Override
	public Map<String, Object> list(String typeId,
			Map<String, String> condition, int start, int pageSize,
			String... tableName) {
		/*List<Column> columns = listDisplayColumns(tableName);
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		ListWork work = new ListWork(WorkType.SELECT, tableName, typeId,
				columns, condition, start, pageSize);
		work.execute(sqlSessionFactory.openSession().getConnection());
		List<Map<String, String>> data = work.getListData();
		int total = listTotal(typeId, condition,tableName);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", data);
		return result;*/
		return null;
	}

	private String castList(List<String> list) {
		return list.toString().replace("[", "").replace("]", "");
	}

	private String castListToVal(List<String> list) {
		StringBuilder result = new StringBuilder();
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				String val = list.get(i);
				if (list.get(i).contains("\\")) {
					val = list.get(i).replace("\\", "\\\\");
				}
				result.append("'").append(val).append("'");
				if (i != list.size() - 1) {
					result.append(",");
				}
			}
		}
		return result.toString();
	}
	
	private String castMapToVal(Map<String, Object> data) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (String key : data.keySet()) {
			i++;
			sb.append(key).append("=").append("'").append(data.get(key))
					.append("'");
			if (i < data.size()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	@Override
	public List<AttrInitData> initByDB(String tableName, String labelColumn,
									   String valueColumn) {
		/*Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		DataInitByDBWork work = new DataInitByDBWork(WorkType.SELECT,
				tableName, labelColumn, valueColumn);
		work.execute(sqlSessionFactory.openSession().getConnection());
		return work.getData();*/
		return  null;
	}

	@Override
	public List<Map<String, String>> listComp(List<String> compIds,
			String tableName) {
		/*List<Column> columns = listColumns(tableName);
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		ListCompWork work = new ListCompWork(WorkType.SELECT, tableName,
				compIds, columns);
		work.execute(sqlSessionFactory.openSession().getConnection());
		return work.getData();*/
		return  null;
	}

	@Override
	public List<Column> listColumn(String... tableName) {
		return listDisplayColumns(tableName);
	}

}
