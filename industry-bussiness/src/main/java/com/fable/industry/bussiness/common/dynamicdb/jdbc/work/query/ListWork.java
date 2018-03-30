package com.fable.industry.bussiness.common.dynamicdb.jdbc.work.query;

import com.fable.industry.bussiness.common.dynamicdb.Column;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.sql.SqlMapperUtil;
import com.fable.industry.bussiness.common.dynamicdb.jdbc.work.BaseWork;
import com.fable.industry.api.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 列表查询工作，包含搜索
 * 
 * @author 郑自辉
 *
 */
public class ListWork extends BaseQueryWork {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * 查询出的列表数据
	 */
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();

	/**
	 * 要查询的配置项类型ID
	 */
	private String curTypeId;

	/**
	 * 查询条件
	 */
	private Map<String, String> condition;

	/**
	 * 开始条数
	 */
	private int start;

	/**
	 * 每页显示条数
	 */
	private int pageSize;

	public ListWork(BaseWork.WorkType workType, String[] tableName, String curTypeId, List<Column> columns,
					Map<String, String> condition, int start, int pageSize) {
		super(workType, tableName, columns);
		this.curTypeId = curTypeId;
		this.condition = condition;
		this.start = start;
		this.pageSize = pageSize;
	}

	public List<Map<String, String>> getListData() {
		return listData;
	}

	@Override
	protected String sql() {
		String listSql = selectSql();
		//拼装Select
		StringBuilder selectColumns = new StringBuilder();
		if (null != columns && !columns.isEmpty()) {
			for (int i = 0, size = columns.size(); i < size; i++) {
				Column column = columns.get(i);
				if (null == column) {
					continue;
				}
				selectColumns.append(column.getTableName()).append(".").append(column.getColumnName());
				if (i != size - 1) {
					selectColumns.append(",");
				}
			}
		}
		if (StringUtils.isNotEmpty(selectColumns.toString()))
		{
			selectColumns.append(",");
		}
		selectColumns.append("ResCi.maintainerId,sui.UserName maintainerName,ResCi.CiId,ResCi.name,ResCi.status,"
				+ "ResCi.AssetId,ResCi.CiTypeid,ResTypeDefine.ResTypeName,ResAsset.AssetID assetId,"
				+ "ResAsset.AssetName assetName,ResAsset.DiscoverTime discoverTime,ResAsset.Address address,ResAsset.SerialNo serialNo,ResAsset.ConfigurationItemId configurationItemId,"
				+ "ResAsset.ReceiveMode receiveMode,ResAssetType.Title title,ResAsset.ResourceTypeId resTypeId,"
				+ "mfi.resManuFacturerName,rcd.resCategoryName,DATE_FORMAT(ResAsset.receiveTime,'%Y-%m-%d') receiveTime,ResAsset.cost,ResAsset.description,"
				+ "ResCi.maintenanceSteps,ResCi.note,DATE_FORMAT(ResAsset.WarrantyPeriod,'%Y-%m-%d') warrantDate");
		listSql = listSql.replace("#{select}", selectColumns.toString());

		//拼装left join
		StringBuilder join = new StringBuilder();
		if (null != tableName && tableName.length > 0) {
			for (String t : tableName) {
				if (StringUtils.isEmpty(t) || "ResCi".equals(t)) {
					continue;
				}
				join.append("LEFT JOIN").append(" ").append(t).append(" ").append(t).append(" on ")
						.append("ResCi.CiId=").append(t).append(".CiId ");
			}
		}
		join.append(" LEFT JOIN SysUserInfo sui ON ResCi.maintainerId = sui.UserID ");
		join.append(" LEFT JOIN ResCategoryDefine rcd ON ResAsset.ProductCatalogId = rcd.ResCategoryID "
				+ " LEFT JOIN ManufacturerInfo mfi ON rcd.resManufacturerID = mfi.resManufacturerID ");
		listSql = listSql.replace("#{left-join}", join.toString());
		if("ResAsset".equals(condition.get("queryTable")) && null != condition){
			listSql = listSql.replace("#{where}", " where ResAsset.AssetID in (select ResAsset.AssetID from ResAsset ResAsset where 1=1 #{curTypeId} #{condition})");
		}else{
			listSql = listSql.replace("#{where}", " where ResCi.CiId in (select ResCi.CiId from ResCi ResCi where 1=1 #{curTypeId} #{condition})");
		}
		
		//拼装Condition
		StringBuilder conditions = new StringBuilder();
		StringBuilder conditions_query = new StringBuilder();
		if (null != condition && !condition.isEmpty()){
			Set<Entry<String, String>> entrySet = condition.entrySet();
			for (Iterator<Entry<String, String>> it = entrySet.iterator(); it.hasNext();) {
				Entry<String, String> param = it.next();
				String key = param.getKey();
				/**
				 * 当前登录用户ID和queryTable只用作查询资源的关注，并不属于资源数据本身的查询条件
				 * 所以此处要过滤掉
				 */
				if("bizSqlCondition".equals(key)) {
					conditions.append(" ").append(param.getValue()).append(" ");
				} else if ("excludeSqlCondition".equals(key)) {
					conditions.append(" ").append(param.getValue()).append(" ");
				} else if ("userId".equals(key) || "queryTable".equals(key)) {
					continue;
				} else if ("name".equals(key)) {
					conditions.append(" and ResCi.name like '%").append(param.getValue()).append("%' ");
				} else if ("applicationSystemUserId".equals(key)) {
					conditions.append(" and (ResCi.MaintainerId = ").append(param.getValue())
					.append(" )");
				} else if ("applicationSystemMaintainerId".equals(key)) {
					conditions.append(" and ResCi.maintainerId = ").append(param.getValue());
				}
				//动态查询条件的拼接
				else if("str".equals(key)||"list_resAs".equals(key)){
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
								tableName_str="ResCi";
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
								}else if((tableName_str+"_"+Colname_str).equals("ResCi_CiRight") || (tableName_str+"_"+Colname_str).equals("ResAsset_AssetRight")){
									if("1".equals(value_str.toString())){
										conditions_query.append(" AND ResCi.maintainerId = ").append(condition.get("userId")).append(" ");
									}else if("2".equals(value_str.toString())){
										conditions_query.append(" AND (ResCi.maintainerId is null OR ResCi.maintainerId != ").append(condition.get("userId")).append(") ");
									}
								}else{
									conditions_query.append("  and  "+tableName_str+"."+Colname_str+" = '").append(value_str).append("'");	
								}
							}
							
						}
					}
				} else {
					conditions.append(" and ResCi.").append(param.getKey()).append("=").append(param.getValue())
							.append(" ");
				}
			}
		}
		listSql = listSql.replace("#{condition2}", conditions_query.toString());
		listSql = listSql.replace("#{condition}", conditions.toString());
		if(!"-1".equals(String.valueOf(start))){//添加分页
			listSql += " limit "+ String.valueOf(start) + ", " + String.valueOf(pageSize) + "";
		}
		/*listSql = listSql.replace("#{start}", String.valueOf(start))
							.replace("#{pageSize}", String.valueOf(pageSize));*/
		if (StringUtils.isNotEmpty(curTypeId) && !"null".equals(curTypeId))
		{	String myCondition;
			if("ResAsset".equals(condition.get("queryTable"))){
				myCondition = " and ResAsset.AssetTypeid in (" + curTypeId + ") ";
			}else{
				myCondition = " and ResCi.CiTypeid in (" + curTypeId + ") ";
			}
			listSql = listSql.replace("#{curTypeId}",myCondition);
		}else{
			listSql = listSql.replace("#{curTypeId}","");
		}
		//拼接用户ID
		listSql = listSql.replace("#{userId}", condition.get("userId"));
		logger.info("分页查询配置项类型：{}，最终组装的SQL语句：{}",curTypeId,listSql);
		return listSql;
	}

	@Override
	protected String selectSql() {
		return SqlMapperUtil.getSqlMapper().getList();
	}

	@Override
	protected String genWhere(String tableName) {
		return tableName + ".CiId ";
	}

	@Override
	protected void processData(ResultSet rs) {
		try {
			while (rs.next()) {
				Map<String, String> data = new HashMap<String, String>();
				for (Column c : columns) {
					data.put(c.getColumnName(), StringUtils.isEmpty(rs.getString(c.getColumnName())) ? ""
							: rs.getString(c.getColumnName()));
				}
				//配置项列表那边已经使用了ciID作为主键字段的名称，所以这边也设置下
				data.put("ciID", StringUtils.isEmpty(rs.getString("CiId")) ? "" : rs.getString("CiId"));
				data.put("maintainerId", StringUtils.isEmpty(rs.getString("maintainerId")) ? "" : rs.getString("maintainerId"));
				data.put("Name", StringUtils.isEmpty(rs.getString("Name")) ? "" : rs.getString("Name"));
				data.put("status", StringUtils.isEmpty(rs.getString("status")) ? "" : rs.getString("status"));
				data.put("assetId", rs.getString("AssetId"));
				data.put("ciTypeId", rs.getString("CiTypeid"));
				data.put("resTypeName", rs.getString("ResTypeName"));
				data.put("attention_count", rs.getString("attention_count"));
				//data.put("user_name", rs.getString("user_name"));
				data.put("maintainerName", rs.getString("maintainerName"));
				//data.put("maintainer_name", rs.getString("maintainer_name"));
				data.put("department_name", rs.getString("department_name"));
				//data.put("projectName", rs.getString("projectName"));
				//资产
				data.put("assetName", rs.getString("assetName"));
				data.put("serialNo", rs.getString("serialNo"));
				data.put("title", rs.getString("title"));
				data.put("receiveMode", rs.getString("receiveMode"));

				data.put("resManuFacturerName", rs.getString("resManuFacturerName"));
				data.put("resCategoryName", rs.getString("resCategoryName"));
				data.put("receiveTime", rs.getString("receiveTime"));
				data.put("receiveMode", rs.getString("receiveMode"));
				data.put("cost", rs.getString("cost"));
				data.put("description", rs.getString("description"));
				data.put("maintenanceSteps", rs.getString("maintenanceSteps"));
				data.put("note", rs.getString("note"));
				data.put("warrantDate", rs.getString("warrantDate"));
				data.put("address",rs.getString("address"));
				data.put("discoverTime",rs.getString("discoverTime"));

				listData.add(data);
			}
		} catch (SQLException e) {
			logger.error("查询结果转换失败,表名：{}", (Object[]) tableName);
		}
	}
}
