package com.jeeplus.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 拼接SQL工具类
 * @author pumkin
 *
 */
public class EgSqlBuffer {
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	private String select = "";

	private String from = "";

	private String where = "";

	private String groupBy = "";

	private String orderBy = "";

	public EgSqlBuffer() {
		super();
	}
	
	public static EgSqlBuffer of() {
		
		return new EgSqlBuffer();
	}
		
	public EgSqlBuffer(String from) {
		super();
		this.from = from;
	}

	public EgSqlBuffer(String select, String from, String where, String groupBy, String orderBy) {
		super();
		this.select = select;
		this.from = from;
		this.where = where;
		this.groupBy = groupBy;
		this.orderBy = orderBy;
	}

	public EgSqlBuffer appendSelect(String select) {
		
		this.select += " " + select;
		
		return this;
	}

	public EgSqlBuffer appendFrom(String from) {
		
		this.from += " " + from;
		return this;
	}

	public EgSqlBuffer appendWhere(String where) {
		
		if(this.where.trim().length()==0) {
			
			this.where = where;
		} else {
			
			this.where += " and " + where;
		}
		
		return this;
	}

	public EgSqlBuffer appendGroupBy(String groupBy) {
		
		if(this.groupBy.trim().length()==0) {
			
			this.groupBy = groupBy;
		} else {
			
			this.groupBy += "," + groupBy;
		}
		
		return this;
	}

	public EgSqlBuffer appendOrderBy(String orderBy) {
		
		if(this.orderBy.trim().length()==0) {
			
			this.orderBy = orderBy;
		} else {
			
			this.orderBy += "," + orderBy;
		}
		
		return this;
	}
	
	public EgSqlBuffer addParam(String key, Object value) {
		
		params.put(key, value);		
		return this;
	}
	
	public EgSqlBuffer addParamLikeAll(String key, String value) {
		
		params.put(key, EgDbUtil.getAllLike(value));		
		return this;
	}

	public String getQuerySql() {
				
		return getSelect() + getFrom() + getWhere() + getGroupBy() + getOrderBy();
	}
	
	public String getCountSql() {
		
		if(groupBy.trim().length()>0) {
			
			return "select count(distinct "+ groupBy +") " + getFrom() + " " + getWhere();
		} else {
			
			return "select count(*) " + getFrom() + " " + getWhere();
		}
	}
	
	public String getCountSql(String count) {
		
		return "select count("+ count +") " + getFrom() + " " + getWhere();
	}
	
	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getFrom() {
		
		return " " + from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWhere() {
		
		if(where.trim().length()==0) {
			
			return where;
		} else {
			
			return " where " + where;
		}
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getGroupBy() {
		
		if(groupBy.trim().length()>0) {
			
			return " group by " + groupBy;
		}
		
		return "";
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getOrderBy() {
		
		if(orderBy.trim().length()>0) {
			
			return " order by " + orderBy;
		}
		
		return "";
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
