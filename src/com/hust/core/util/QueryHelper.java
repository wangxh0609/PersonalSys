package com.hust.core.util;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
	
	//from”Ôæ‰
	private String fromClause = "";
	//where”Ôæ‰
	private String whereClause = "";
	//order by”Ôæ‰
	private String orderByClause = "";
	
	private List<Object> parameters;
	//≈≈–Ú∑Ω Ω
	public static String ORDER_BY_DESC = "DESC";//ƒÊ–Ú
	public static String ORDER_BY_ASC = "ASC";//À≥–Ú ƒ¨»œ
		
	public QueryHelper(Class clazz, String alias){
		fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
		//System.out.println(clazz.getSimpleName());
	}
	public void addFromCon(Class clazz, String alias){
		fromClause+=","+clazz.getSimpleName() + " " + alias;
	}
	public void addCondition(String condition, Object... params){
		if (whereClause.length() > 1) {
			whereClause += " AND " + condition;
		} else {//where Ãıº˛
			whereClause += " WHERE " + condition;
		}
		
		
		if(parameters == null){
			parameters = new ArrayList<Object>();
		}
		if(params != null){
			for(Object param: params){
				parameters.add(param);
			}
		}
	}
	
	public void addOrderByProperty(String property, String order){
		if (orderByClause.length() > 1) {
			orderByClause += "," + property + " " + order;
		} else {
			orderByClause = " ORDER BY " + property + " " + order;
		}
	}

	public String getQueryListHql(){
		return fromClause + whereClause + orderByClause;
	}
	
	public String getQueryCountHql(){
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}
	
	public List<Object> getParameters(){
		return parameters;
	}
}
