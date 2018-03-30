package com.fable.industry.bussiness.common.dynamicdb.jdbc.work.query;

import com.fable.industry.bussiness.common.dynamicdb.jdbc.sql.SqlMapperUtil;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.work.BaseWork;
import com.fable.industry.api.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 列表查询总数
 * @author 郑自辉
 *
 */
public class ListTotalWork extends BaseWork {
	
	private static final Logger logger = LogManager.getLogger();
	
	private int total;
	
	private String curTypeId;
	
	private Map<String, String> condition;
	private String[] tableName;
	
	public ListTotalWork(BaseWork.WorkType workType, String curTypeId, Map<String, String> condition, String... tableName)
	{
		super(workType);
		this.curTypeId = curTypeId;
		this.condition = condition;
		this.tableName = tableName;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	protected String sql() {
		String totalSql = SqlMapperUtil.getSqlMapper().getListtotal();
		if (StringUtils.isEmpty(curTypeId) || "null".equals(curTypeId))
		{
			totalSql = totalSql.replace("#{curTypeId}","");
		}
		else
		{
			totalSql = totalSql.replace("#{curTypeId}"," and rc.CiTypeid in (" + curTypeId + ") ");
		}
		//拼装Condition
		StringBuilder conditions = new StringBuilder();
		StringBuilder conditions_query = new StringBuilder();
		if (null != condition && !condition.isEmpty())
		{
			Set<Entry<String, String>> entrySet = condition.entrySet();
			for (Iterator<Entry<String, String>> it = entrySet.iterator();it.hasNext();)
			{
				Entry<String, String> param = it.next();
				String key = param.getKey();
				StringBuilder join2 = new StringBuilder();
				if (null != tableName && tableName.length > 0)
				{
					for (String t : tableName)
					{
						if (StringUtils.isEmpty(t) || "ResCi".equals(t))
						{
							continue;
						}
						join2.append("LEFT JOIN")
							.append(" ")
							.append(t)
							.append(" ")
							.append(t)
							.append(" on ")
							.append("rc.CiId=")
							.append(t)
							.append(".CiId ");
					}
				}
				totalSql = totalSql.replace("#{left-join2}", join2.toString());
				//添加where条件
				if("ResAsset".equals(condition.get("queryTable")) && null != condition){
					totalSql = totalSql.replace("#{where}", " where ResAsset.AssetID in (select ResAsset.AssetID from ResAsset ResAsset where 1=1 #{curTypeId} #{condition} #{condition2})");
				}else{
					totalSql = totalSql.replace("#{where}", " where rc.CiId in (select rc.CiId from ResCi rc where 1=1 #{curTypeId} #{condition} #{condition2})");
				}
				if (StringUtils.isEmpty(curTypeId) || "null".equals(curTypeId))
				{
					totalSql = totalSql.replace("#{curTypeId}","");
				}
				else
				{	String myCondition;
					if("ResAsset".equals(condition.get("queryTable"))){
						myCondition = " and ResAsset.AssetTypeid in (" + curTypeId + ") ";
					}else{
						myCondition = " and rc.CiTypeid in (" + curTypeId + ") ";
					}
					totalSql = totalSql.replace("#{curTypeId}", myCondition);
				}
				/**
				 * 当前登录用户ID只用作查询资源的关注，并不属于资源数据本身的查询条件
				 * 所以此处要过滤掉
				 */
				if("bizSqlCondition".equals(key)) {
					conditions.append(" ").append(param.getValue()).append(" ");
				}
				else if("excludeSqlCondition".equals(key)) {
					conditions.append(" ").append(param.getValue()).append(" ");
				}
				else if ("userId".equals(key) || "queryTable".equals(key)) {
					continue;
				} 
				else if ("name".equals(key)) {
					conditions.append(" and rc.name like '%").append(param.getValue()).append("%' ");
				} 
				else if ("applicationSystemUserId".equals(key))
				{
					conditions.append(" and rc.userId = ").append(param.getValue());
				}else if ("applicationSystemMaintainerId".equals(key))
				{
					conditions.append(" and rc.maintainerId = ").append(param.getValue());
				} else if("str".equals(key)||"list_resAs".equals(key)){
					 if("list_resAs".equals(key)){
						 conditions_query.append(" and ResAsset.AssetTypeID is not null ");
						}
					List<Map<String,Object>> list = null;
					if(param.getValue()!=null){
						list= JsonUtil.toList(param.getValue());
					}
					if(null!=list&&list.size()!=0){
						for(int i=0;i<list.size();i++){
							Map map=list.get(i);
							String tableName_str=(map.get("tableName")).toString().trim();
							String Colname_str=(map.get("Colname")).toString().trim();
							String value_str=(map.get("value")).toString().trim();
							String widgetValue_str=(map.get("widgetValue")).toString().trim();
							int fdSid=0;
							if(null!=map.get("fdSid")){
								fdSid=Integer.valueOf((map.get("fdSid")).toString().trim());	
							}
							if(tableName_str.equalsIgnoreCase("ResCi")){
								tableName_str="rc";
							}
							if(widgetValue_str.equalsIgnoreCase("Datebox")){
									if(Colname_str.contains("_min")){
										conditions_query.append(" and DATE_FORMAT( "+tableName_str.substring(0,tableName_str.length()-4)+"."+Colname_str.substring(0,Colname_str.length()-4) +", '%Y-%m-%d') >= DATE_FORMAT('" ).append(value_str).append("','%Y-%m-%d')");	
									}
									if(Colname_str.contains("_max")){
										conditions_query.append(" and DATE_FORMAT( "+tableName_str.substring(0,tableName_str.length()-4)+"."+Colname_str.substring(0,Colname_str.length()-4) +", '%Y-%m-%d') <= DATE_FORMAT('").append(value_str).append("','%Y-%m-%d')");	
										
									}
								
							}else if(widgetValue_str.equalsIgnoreCase("Input")){
								if(fdSid!=1&&fdSid!=9){
									conditions_query.append(" and "+tableName_str+"."+Colname_str+" like '%").append(value_str).append("%' ");	
										
								}else{
									if(Colname_str.contains("_min")){
										conditions_query.append(" and "+tableName_str.substring(0,tableName_str.length()-4)+"."+Colname_str.substring(0,Colname_str.length()-4)+" >= ").append(value_str);	
									}
									if(Colname_str.contains("_max")){
										 conditions_query.append(" and "+tableName_str.substring(0,tableName_str.length()-4)+"."+Colname_str.substring(0,Colname_str.length()-4)+" <= ").append(value_str);	
									}
								}
								
								
							}else{
								if((tableName_str+"_"+Colname_str).equals("ResAsset_DepreciateMode")){
									if(Integer.valueOf(value_str.toString())!=0){
										conditions_query.append("  and  "+tableName_str+"."+Colname_str+" !=0 ");	
									}else{
										conditions_query.append("  and  "+tableName_str+"."+Colname_str+" = '").append(value_str).append("'");	
									}
								}else if((tableName_str+"_"+Colname_str).equals("rc_CiRight") || (tableName_str+"_"+Colname_str).equals("ResAsset_AssetRight")){
									if("1".equals(value_str.toString())){
										conditions_query.append(" AND rc.maintainerId = ").append(condition.get("userId")).append(" ");
									}else if("2".equals(value_str.toString())){
										conditions_query.append(" AND (rc.maintainerId is null OR rc.maintainerId != ").append(condition.get("userId")).append(") ");
									}
								}else{
									conditions_query.append("  and  "+tableName_str+"."+Colname_str+" = '").append(value_str).append("'");	
								}
							}
							
						}
					}
				
				}else
				{
					conditions.append(" and rc.")
					            .append(param.getKey())
					            .append("=")
					            .append(param.getValue())
					            .append(" ");
				}
			}
		}
		totalSql = totalSql.replace("#{condition2}", conditions_query.toString());
		totalSql = totalSql.replace("#{condition}", conditions.toString());
		
		return totalSql;
	}

	@Override
	protected void processData(ResultSet rs) {
		try {
			while (rs.next())
			{
				total = rs.getInt("total");
			}
		} catch (SQLException e) {
			logger.error("分页查询列表总条数失败，配置项类型：{}",curTypeId);
		}
	}
}
