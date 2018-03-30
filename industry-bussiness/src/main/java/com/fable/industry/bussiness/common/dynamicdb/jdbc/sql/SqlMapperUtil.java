package com.fable.industry.bussiness.common.dynamicdb.jdbc.sql;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

/**
 * SQL语句工具类
 * 根据数据库类型返回相应的SQL语句
 * @author 郑自辉
 *
 */
public class SqlMapperUtil {

	private static SqlMapper sqlMapper;
	
	static
	{
		XStream xstream = new XStream();
		xstream.alias("sqlmap", SqlMapper.class);
		//这边会根据真实数据库类型自动加载相应的配置文件
		//String databaseType = CTD.judgeDateBase();
		String databaseType = "mysql";
		InputStream in = SqlMapperUtil.class.getResourceAsStream(databaseType + ".xml");
		sqlMapper = (SqlMapper) xstream.fromXML(in);
	}
	
	public static SqlMapper getSqlMapper()
	{
		return sqlMapper;
	}
}
