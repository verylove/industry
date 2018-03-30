package com.fable.industry.bussiness.common.dynamicdb.jdbc;

import com.fable.industry.api.common.helper.SessionFTemplate;
import com.fable.industry.bussiness.common.dynamicdb.api.IDynamicDB;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.sqltype.SqlTypeUtil;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.work.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 动态表结构JDBC实现方式 目前先采用HibernaOte执行原始SQL的方式实现
 * 
 * @author 郑自辉
 * 
 */
@Repository("jdbcDB")
public class JdbcDBImpl extends BaseJdbc implements IDynamicDB {

//    @Autowired
//    SqlSessionFactory sqlSessionFactory;

	@Override
	public void createTable(final String tableName) {
		createTable(tableName, "CiId");
	}


	@Override
	public void addColumn(String tableName, String columnName, String javaType) {
		BaseWork work = new AddWork(BaseWork.WorkType.ADD, tableName, columnName,
				SqlTypeUtil.sqlType(javaType));
        work.execute(SessionFTemplate.getSessionConnection());
	}

	@Override
	public void removeColumn(String tableName, String colomnName,
			boolean isLogic) {
		if (!isLogic) {
			BaseWork work = new RemoveWork(BaseWork.WorkType.REMOVE, tableName, colomnName);
            work.execute(SessionFTemplate.getSessionConnection());
		}
	}

	@Override
	public void dropTable(String tableName) {
        BaseWork work = new DropWork(BaseWork.WorkType.DROP, tableName);
		// 如果是oracle数据库则删除sequence
		/*if (CTD.isOracle() && isSeqExist(tableName, sqlSessionFactory.openSession())) {
            sqlSessionFactory.openSession().delete("DROP SEQUENCE " + "seq" + tableName);
		}*/
        work.execute(SessionFTemplate.getSessionConnection());
	}

	@Override
	public void createTable(String tableName, String primaryKey) {
        BaseWork work = new CreateWork(BaseWork.WorkType.CREATE, tableName, primaryKey);
        work.execute(SessionFTemplate.getSessionConnection());
		/*if (CTD.isOracle()) {
			if (isSeqExist(tableName,session)) {
				// 创建sequence，因为每次的表名称都不同，所以不需要判断sequence是否会重复
				String sequence = "create sequence " + "seq" + tableName + " "
						+ " minvalue 1"
						+ " maxvalue 999999999999999999999999999"
						+ " start with 1" + "increment by 1" + " nocache";
				session.createSQLQuery(sequence).executeUpdate();
			}
		}*/
	}

	/**
	 * 判断sequence是否存在
	 * @param tableName
	 * @return
	 */
	/*private boolean isSeqExist(String tableName, Session session) {
		// 判断sequence是否存在
		String sql = "select 1 from all_sequences where sequence_name="
				+ ("'seq" + tableName).toUpperCase() + "'";
		String countSeq = String.valueOf(session.createSQLQuery(sql)
				.uniqueResult());
		if (!"1".equals(countSeq)) {
			return true;
		}else{
			return false;
		}
	}*/

	@Override
	public void addColumn(String tableName, String columnName, String javaType,
			int length) {
		String columnType = SqlTypeUtil.dataType(javaType);
		columnType += "(" + length + ")";
		BaseWork work = new AddWork(BaseWork.WorkType.ADD, tableName, columnName, columnType);
        work.execute(SessionFTemplate.getSessionConnection());
	}
}
